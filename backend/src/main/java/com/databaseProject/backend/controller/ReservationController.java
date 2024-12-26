package com.databaseProject.backend.controller;

import com.databaseProject.backend.dto.ParkingLotDto;
import com.databaseProject.backend.dto.ParkingSpotDto;
import com.databaseProject.backend.dto.ReservationDto;
import com.databaseProject.backend.dto.ReservationRequest;
import com.databaseProject.backend.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@CrossOrigin
@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;



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
        List<ParkingSpotDto> result = reservationService.fetchAllSpotsForLot(lotId);
        if(!result.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to fetch spots.");
        }
    }

    @PostMapping("/spots/{spotId}/reserve")
    public ResponseEntity<?> reserveSpot(@PathVariable int spotId,
                                         @RequestParam Timestamp startTime,
                                         @RequestParam Timestamp expectedEndTime,
                                         HttpServletRequest request) {
        try {
            int driverId = (int) request.getAttribute("id");
            reservationService.reserveSpot(spotId, startTime, expectedEndTime, driverId);
            return ResponseEntity.status(HttpStatus.OK).body("Spot reserved successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/spots/{spotId}/cancel")
    public ResponseEntity<?> cancelSpot(@PathVariable int spotId, HttpServletRequest request) {
        try {
            int driverId = (int) request.getAttribute("id");
            reservationService.cancelReservation(spotId, driverId);
            return ResponseEntity.status(HttpStatus.OK).body("Reservation cancelled successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/spots/{spotId}/complete")
    public ResponseEntity<?> completeSpot(@PathVariable int spotId, HttpServletRequest request) {
        try {
            int driverId = (int) request.getAttribute("id");
            reservationService.completeReservation(spotId, driverId);
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
            List<ReservationDto> result = reservationService.fetchAllReservations(driverId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//    @PostMapping("/{driverId}/spots/{spotId}/reserve")
//    public ResponseEntity<?> reserveSpot(@PathVariable int spotId,
//                                         @RequestBody ReservationRequest reservationRequest,
//                                         @PathVariable int driverId) {
//        try {
//            Timestamp startTime = Timestamp.valueOf(reservationRequest.getStartTime());
//            Timestamp expectedEndTime = Timestamp.valueOf(reservationRequest.getExpectedEndTime());
//            reservationService.reserveSpot(spotId, startTime, expectedEndTime, driverId);
//            return ResponseEntity.status(HttpStatus.OK).body("Spot reserved successfully.");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/{driverId}/spots/{spotId}/cancel")
//    public ResponseEntity<?> cancelSpot(@PathVariable int spotId, @PathVariable int driverId) {
//        try {
//            reservationService.cancelReservation(spotId, driverId);
//            return ResponseEntity.status(HttpStatus.OK).body("Reservation cancelled successfully.");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//
//    @PostMapping("/{driverId}/spots/{spotId}/complete")
//    public ResponseEntity<?> completeSpot(@PathVariable int spotId, @PathVariable int driverId) {
//        try {
//            reservationService.completeReservation(spotId, driverId);
//            return ResponseEntity.status(HttpStatus.OK).body("Spot completed successfully.");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/{driverId}/reservations")
//    public ResponseEntity<?> fetchAllReservationsForDriver(@PathVariable int driverId) {
//        try {
//            List<ReservationDto> result = reservationService.fetchAllReservations(driverId);
//            return ResponseEntity.status(HttpStatus.OK).body(result);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

}
