drop PROCEDURE handle_reservations_exceeding_30min;
DELIMITER $$

CREATE PROCEDURE handle_reservations_exceeding_30min()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE spot_id INT;
    DECLARE lot_id INT;
    DECLARE driver_id INT;
    DECLARE duration INT;
    DECLARE base_price DECIMAL(6, 3);
    DECLARE reservation_id INT;

    -- Cursor for reservations exceeding 30 minutes
    DECLARE reservation_cursor CURSOR FOR 
        SELECT r.reservation_id, r.spot_id, r.lot_id, r.driver_id, TIMESTAMPDIFF(MINUTE, r.start_time, NOW()) AS duration
        FROM reservation r
        WHERE r.status = 'WAITING_FOR_ARRIVAL'
          AND TIMESTAMPDIFF(MINUTE, r.start_time, NOW()) >= 30;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    -- Create a temporary table to store the driver IDs
    CREATE TEMPORARY TABLE temp_driver_ids (driver_id INT);
    
    -- Open the cursor
    OPEN reservation_cursor;

    reservation_loop: LOOP
        FETCH reservation_cursor INTO reservation_id, spot_id, lot_id, driver_id, duration;
        IF done THEN
            LEAVE reservation_loop;
        END IF;

        -- Fetch base price for the parking lot
        SELECT pl.base_price INTO base_price FROM parking_lot AS pl WHERE pl.lot_id = lot_id LIMIT 1;

        IF base_price IS NOT NULL THEN
            UPDATE reservation AS r
            SET r.status = 'NO_SHOW', r.penalty = (base_price / 2)
            WHERE r.reservation_id = reservation_id;

            INSERT INTO notification (driver_id, message, type)
            VALUES (driver_id, 'Your reservation has been cancelled because of not Showing up in the time.', 'MESSAGE');

            CALL apply_penalty(driver_id, (base_price / 2));

            -- Store the driver_id in the temporary table
            INSERT INTO temp_driver_ids (driver_id) VALUES (driver_id);

        ELSE
            -- Handle case where base_price is not found
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Base price not found for the parking lot';
        END IF;

    END LOOP;

    -- Close the cursor
    CLOSE reservation_cursor;

    -- Return the driver_ids
    SELECT driver_id FROM temp_driver_ids;

    -- Drop the temporary table after use
    DROP TEMPORARY TABLE IF EXISTS temp_driver_ids;
END$$


-- Procedure to calculate dynamic spot price
CREATE PROCEDURE get_dynamic_spot_price(
    IN lot_id INT,
    IN reservation_start_time DATETIME,
    IN reservation_end_time DATETIME,
    OUT dynamic_price DECIMAL(6, 3)
)
BEGIN
    DECLARE base_price DECIMAL(6, 3);
    DECLARE demand_factor DECIMAL(6, 3);
    DECLARE time_factor DECIMAL(6, 3);

    -- Get base price of the lot
    SELECT pl.base_price INTO base_price
    FROM parking_lot as pl
    WHERE pl.lot_id = lot_id
    LIMIT 1;

    -- Calculate demand factor based on overlapping reservations
    SELECT 1 + (COUNT(*) / (SELECT COUNT(*) FROM parking_spot WHERE lot_id = lot_id)) INTO demand_factor
    FROM reservation as r
    WHERE r.lot_id = lot_id
    AND (
        (r.start_time <= reservation_end_time AND r.end_time >= reservation_start_time)  -- Overlapping condition
    );

    -- Determine time factor based on the time of the reservation
    IF HOUR(reservation_start_time) BETWEEN 7 AND 10 OR HOUR(reservation_start_time) BETWEEN 17 AND 20 THEN
        SET time_factor = 1.5;  -- Peak time multiplier
    ELSE
        SET time_factor = 1.0;  -- Off-peak time multiplier
    END IF;

    -- Calculate dynamic price
    SET dynamic_price = base_price * demand_factor * time_factor;
END$$


-- Procedure to apply penalties
CREATE PROCEDURE apply_penalty(IN driver_id INT, IN penalty_amount DECIMAL(6, 3))
BEGIN
    -- Ensure the penalty_amount is not NULL and handle default case
    IF penalty_amount IS NULL THEN
        SET penalty_amount = 0;
    END IF;

    -- Update penalty for the driver
    UPDATE driver AS d
    SET d.penalty = COALESCE(penalty, 0) + penalty_amount
    WHERE d.driver_id = driver_id;  -- Here, it should reference the parameter 'driver_id'

    -- Add a notification about the penalty
    INSERT INTO notification (driver_id, message, type, seen)
    VALUES (driver_id, CONCAT('A penalty of ', IFNULL(penalty_amount, 0), ' has been applied.'), 'PENALTY', 0);
END$$


DELIMITER $$

-- Procedure 1: notify_reservation_start
CREATE PROCEDURE notify_reservation_start()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE spot_id INT;
    DECLARE lot_id INT;
    DECLARE driver_id INT;

    

    -- Declare the cursor for reservations starting now
    DECLARE reservation_cursor CURSOR FOR 
        SELECT r.spot_id, r.lot_id, r.driver_id
        FROM reservation r
        WHERE r.status = 'WAITING_FOR_ARRIVAL'
          AND TIMESTAMPDIFF(MINUTE, r.start_time, NOW()) = 0;

    -- Declare continue handler for cursor NOT FOUND
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	-- Create a temporary table to store the driver IDs
    CREATE TEMPORARY TABLE temp_driver_ids_start (driver_id INT);
    -- Open the cursor
    OPEN reservation_cursor;

    reservation_loop: LOOP
        FETCH reservation_cursor INTO spot_id, lot_id, driver_id;
        IF done THEN
            LEAVE reservation_loop;
        END IF;

        -- Add a notification for the driver
        INSERT INTO notification (driver_id, message, type)
        VALUES (driver_id, 'Your reservation has started\nIt will be cancelled with penalty, if not arrived in 30 minutes.', 'MESSAGE');

        -- Store the driver_id in the temporary table
        INSERT INTO temp_driver_ids_start (driver_id) VALUES (driver_id);
    END LOOP;

    -- Close the cursor
    CLOSE reservation_cursor;

    -- Return the driver_ids
    SELECT driver_id FROM temp_driver_ids_start;

    -- Drop the temporary table after use
    DROP TEMPORARY TABLE IF EXISTS temp_driver_ids_start;
END$$

-- Procedure 2: notify_end_time_arrived
CREATE PROCEDURE notify_end_time_arrived()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE spot_id INT;
    DECLARE lot_id INT;
    DECLARE driver_id INT;
    DECLARE base_price INT;
    DECLARE price DECIMAL(6,3);
    DECLARE reservation_id INT;

    

    -- Declare the cursor for reservations ending now
    DECLARE reservation_cursor CURSOR FOR 
        SELECT r.spot_id, r.lot_id, r.driver_id, r.price, r.reservation_id
        FROM reservation r
        WHERE r.status = 'DRIVER_ARRIVED'
          AND r.end_time <= NOW();

    -- Declare continue handler for cursor NOT FOUND
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
	SELECT r.driver_id
        FROM reservation r
        WHERE r.status = 'DRIVER_ARRIVED'
          AND r.end_time <= NOW();
    -- Open the cursor
    OPEN reservation_cursor;

    reservation_loop: LOOP
        FETCH reservation_cursor INTO spot_id, lot_id, driver_id, price, reservation_id;
        IF done THEN
            LEAVE reservation_loop;
        END IF;

        SELECT pl.base_price INTO base_price FROM parking_lot AS pl WHERE pl.lot_id = lot_id LIMIT 1;

        IF base_price IS NOT NULL THEN
            -- Update reservation status to OVER_STAY
            UPDATE reservation AS r
            SET r.status = 'OVER_STAY', r.penalty = (base_price / 2)
            WHERE r.reservation_id = reservation_id;

            -- Add a notification for the driver
            INSERT INTO notification (driver_id, message, type)
            VALUES (driver_id, 'Your reservation has ended and your car will be removed.', 'MESSAGE');

            -- Apply penalty
            CALL apply_penalty(driver_id, (base_price / 2) + price);

            
        ELSE
            -- Handle case where base_price is not found
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Base price not found for the parking lot';
        END IF;
    END LOOP;

    -- Close the cursor
    CLOSE reservation_cursor;

END$$

-- Procedure 3: notify_reservation_end_before_5min
CREATE PROCEDURE notify_reservation_end_before_5min()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE spot_id INT;
    DECLARE lot_id INT;
    DECLARE driver_id INT;


    -- Declare the cursor for reservations ending within 5 minutes
    DECLARE reservation_cursor CURSOR FOR 
        SELECT r.spot_id, r.lot_id, r.driver_id
        FROM reservation r
        WHERE r.status = 'DRIVER_ARRIVED'
          AND TIMESTAMPDIFF(MINUTE, r.end_time, NOW()) <= 5;

    -- Declare continue handler for cursor NOT FOUND
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	-- Create a temporary table to store the driver IDs
    CREATE TEMPORARY TABLE temp_driver_ids_5min (driver_id INT);
    
    -- Open the cursor
    OPEN reservation_cursor;

    reservation_loop: LOOP
        FETCH reservation_cursor INTO spot_id, lot_id, driver_id;
        IF done THEN
            LEAVE reservation_loop;
        END IF;

        -- Add a notification for the driver
        INSERT INTO notification (driver_id, message, type)
        VALUES (driver_id, 'Your reservation will end in 5 minutes.\nA penalty will be applied if not checkout.', 'MESSAGE');

        -- Store the driver_id in the temporary table
        INSERT INTO temp_driver_ids_5min (driver_id) VALUES (driver_id);
    END LOOP;

    -- Close the cursor
    CLOSE reservation_cursor;

    -- Return the driver_ids
    SELECT driver_id FROM temp_driver_ids_5min;

    -- Drop the temporary table after use
    DROP TEMPORARY TABLE IF EXISTS temp_driver_ids_5min;
END$$

DELIMITER ;
