package com.databaseProject.backend.controller;

import com.databaseProject.backend.dto.ParkingLotDto;
import com.databaseProject.backend.dto.ParkingSpotDto;
import com.databaseProject.backend.dto.ReservationDto;
import com.databaseProject.backend.dto.ReservationRequest;
import com.databaseProject.backend.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseException(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + ex.getMessage());
    }

    @GetMapping("/lots")
    public ResponseEntity<?> fetchAllLots() {
        List<ParkingLotDto> result = reservationService.fetchAllLots();
        if(!result.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to fetch lots.");
        }
    }

    @GetMapping("/lot/{lotId}/spots")
    public ResponseEntity<?> fetchAllSpotsForLot(@PathVariable int lotId) {
        try {
            List<ParkingSpotDto> result = reservationService.fetchAllSpotsForLot(lotId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to fetch spots.");
        }
    }

    @PostMapping("{lotId}/spots/{spotId}/reserve")
    public ResponseEntity<?> reserveSpot(@PathVariable int spotId,
                                         @PathVariable int lotId,
                                         @RequestBody ReservationRequest reservationRequest,
                                         HttpServletRequest request) {
        try {
            int driverId = (int) request.getAttribute("id");
            System.out.println("Driver ID: " + driverId);

            Timestamp startTime = Timestamp.valueOf(reservationRequest.getStartTime());
            Timestamp endTime = Timestamp.valueOf(reservationRequest.getEndTime());

            reservationService.reserveSpot(lotId, spotId, startTime, endTime, driverId);
            return ResponseEntity.status(HttpStatus.OK).body("Spot reserved successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{reservationId}/spots/{spotId}/cancel")
    public ResponseEntity<?> cancelSpot(@PathVariable int reservationId, @PathVariable int spotId, HttpServletRequest request) {
        try {
            int driverId = (int) request.getAttribute("id");
            reservationService.cancelReservation(reservationId, spotId, driverId);
            return ResponseEntity.status(HttpStatus.OK).body("Reservation cancelled successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{reservationId}/spots/{spotId}/complete")
    public ResponseEntity<?> completeSpot(@PathVariable int reservationId, @PathVariable int spotId, HttpServletRequest request) {
        try {
            int driverId = (int) request.getAttribute("id");
            reservationService.completeReservation(reservationId, spotId, driverId);
            return ResponseEntity.status(HttpStatus.OK).body("Spot completed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/reservations")
    public ResponseEntity<?> fetchAllReservationsForDriver(HttpServletRequest request) {
        try {
            int driverId = (int) request.getAttribute("id");

            System.out.println("getting reservations for driver: " + driverId);

            List<ReservationDto> result = reservationService.fetchAllReservationsAfterNow(driverId);
            for (ReservationDto reservation : result) {
                reservation.setStrStartTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(reservation.getStartTime()));
                reservation.setStrEndTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(reservation.getEndTime()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/spots/{spotId}/reservations")
    public ResponseEntity<?> fetchAllReservationsForSpot(@PathVariable int spotId) {
        try {
            List<ReservationDto> result = reservationService.fetchAllReservationsForSpot(spotId);
            for (ReservationDto reservation : result) {
                reservation.setStrStartTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(reservation.getStartTime()));
                reservation.setStrEndTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(reservation.getEndTime()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // from waiting to arrived and from reserved to occupied
    @PostMapping("/{reservationId}/spots/{spotId}/checkin")
    public ResponseEntity<?> checkIn(@PathVariable int reservationId, @PathVariable int spotId, HttpServletRequest request) {
        try {
            int driverId = (int) request.getAttribute("id");
            reservationService.checkIn(reservationId, spotId, driverId);
            return ResponseEntity.status(HttpStatus.OK).body("Checked in successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
