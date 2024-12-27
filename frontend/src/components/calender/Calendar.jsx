import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {
    addMinutes,
    isBefore,
    isAfter,
    format,
    startOfDay,
    parse,
    isEqual,
    addDays,
    subDays,
} from "date-fns";

const Calendar = ({ 
    spotData,
    handleDeselectSpot, 
    handleReservationFromParking
}) => {
    const [selectedSpot] = useState(spotData); // Default to the first spot.
    const [startTime, setStartTime] = useState(null);
    const [endTime, setEndTime] = useState(null);
    const [currentDate, setCurrentDate] = useState(new Date());

    const generateTimeSlots = (date) => {
        return Array.from({ length: 24 * 4 }, (_, i) =>
            format(addMinutes(startOfDay(date), i * 15), "HH:mm")
        );
    };

    const timeSlots = generateTimeSlots(currentDate);

    const checkAvailability = (start, end, reservations) => {
        const startDate = new Date(start);
        const endDate = new Date(end);

        return !reservations.some(
            (reservation) =>
                isBefore(startDate, new Date(reservation.endTime)) &&
                isAfter(endDate, new Date(reservation.startTime))
        );
    };

    const isReserved = (slot) => {
        let slotTime = parse(slot, "HH:mm", currentDate);
        slotTime = format(slotTime, "yyyy-MM-dd'T'HH:mm:ss");

        return selectedSpot.reservations.some((reservation) => {
            let reservationStart = new Date(reservation.startTime);
            let reservationEnd = new Date(reservation.endTime);
            reservationStart = format(reservationStart, "yyyy-MM-dd'T'HH:mm:ss");
            reservationEnd = format(reservationEnd, "yyyy-MM-dd'T'HH:mm:ss");
            return (
                isBefore(slotTime, reservationEnd) &&
                (isAfter(slotTime, reservationStart) ||
                    isEqual(slotTime, reservationStart))
            );
        });
    };

    const handleReserve = async () => {
        if (!startTime || !endTime) {
            alert("Please select both start and end times.");
            return;
        }

        const now = new Date();
        if (isBefore(startTime, now)) {
            alert("Start time cannot be in the past.");
            return;
        }

        const duration = (endTime - startTime) / 60000; // Convert ms to minutes
        if (duration < 60) {
            alert("Reservation must be at least 1 hour.");
            return;
        }
        if (duration > 1440) {
            alert("Reservation cannot exceed 1 day.");
            return;
        }

        const formattedStart = format(startTime, "yyyy-MM-dd'T'HH:mm:ss");
        const formattedEnd = format(endTime, "yyyy-MM-dd'T'HH:mm:ss");

        if (
            checkAvailability(formattedStart, formattedEnd, selectedSpot.reservations)
        ) {


            await handleReservationFromParking(selectedSpot.spotId, formattedStart, formattedEnd);

            // alert(
            //     `Reservation confirmed from ${format(startTime, "HH:mm")} to ${format(
            //         endTime,
            //         "HH:mm"
            //     )}.`
            // );
        } else {
            alert("The selected time range is unavailable.");
        }
    };

    const handleNextDay = () => {
        setCurrentDate((prev) => addDays(prev, 1));
    };

    const handlePrevDay = () => {
        setCurrentDate((prev) => subDays(prev, 1));
    };

    return (
        <div style={{ padding: "20px", maxWidth: "600px", margin: "auto" }}>
            <button style={{
                position: "absolute",
                top: "10px",
                right: "10px",
                cursor: "pointer",
                padding: "5px 10px",
                border: "none",
                borderRadius: "50%",

            }} onClick={() => handleDeselectSpot()}>
                X
            </button>


            <h2>
                Spot ID: {selectedSpot.spotId} - {selectedSpot.type}
            </h2>

            <div>
                <label>Select Start Time:</label>
                <DatePicker
                    selected={startTime}
                    onChange={(date) => setStartTime(date)}
                    showTimeSelect
                    timeIntervals={15}
                    dateFormat="yyyy-MM-dd HH:mm"
                    minDate={new Date()} // Minimum date is now
                    maxDate={addDays(new Date(), 7)} // Maximum date is a week from now
                    placeholderText="Select start time"
                />
            </div>

            <div style={{ marginTop: "10px" }}>
                <label>Select End Time:</label>
                <DatePicker
                    selected={endTime}
                    onChange={(date) => setEndTime(date)}
                    showTimeSelect
                    timeIntervals={15}
                    dateFormat="yyyy-MM-dd HH:mm"
                    minDate={startTime || new Date()} // Minimum date is start time
                    maxDate={addDays(new Date(), 7)}
                    placeholderText="Select end time"
                />
            </div>

            <button
                onClick={handleReserve}
                style={{ marginTop: "20px", padding: "10px 20px", cursor: "pointer" }}
            >
                Reserve
            </button>

            <h3>Time Slots for {format(currentDate, "yyyy-MM-dd")}</h3>

            <div
                style={{
                    display: "flex",
                    justifyContent: "space-between",
                    marginBottom: "10px",
                }}
            >
                <button
                    onClick={handlePrevDay}
                    disabled={isBefore(currentDate, new Date())}
                >
                    Previous Day
                </button>
                <button
                    onClick={handleNextDay}
                    disabled={isAfter(currentDate, addDays(new Date(), 6))}
                >
                    Next Day
                </button>
            </div>

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns: "repeat(4, 1fr)",
                    gap: "5px",
                    marginTop: "10px",
                }}
            >
                {timeSlots.map((slot) => (
                    <div    
                        key={slot}
                        style={{
                            padding: "10px",
                            border: "1px solid gray",
                            backgroundColor: isReserved(slot) ? "lightcoral" : "lightgreen",
                            textAlign: "center",
                            fontSize: "12px",
                        }}
                    >
                        {slot}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Calendar;
