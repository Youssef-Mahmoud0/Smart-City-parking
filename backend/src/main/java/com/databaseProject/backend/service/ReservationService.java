package com.databaseProject.backend.service;

import com.databaseProject.backend.dto.ParkingLotDto;
import com.databaseProject.backend.dto.ParkingSpotDto;
import com.databaseProject.backend.dto.ReservationDto;
import com.databaseProject.backend.dto.SpotReservationDto;
import com.databaseProject.backend.repository.ParkingLotRepository;
import com.databaseProject.backend.repository.ReservationRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public List<ParkingLotDto> fetchAllLots() {
        return parkingLotRepository.getAllLots();
    }

    public List<ParkingSpotDto> fetchAllSpotsForLot(int lotId) {
        List<ParkingSpotDto> spots = parkingLotRepository.getAllSpots(lotId);
        List<SpotReservationDto> reservations = parkingLotRepository.getSpotReservations(lotId)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<Integer, List<SpotReservationDto>> reservationsBySpot = reservations.stream().collect(Collectors.groupingBy(SpotReservationDto::getSpotId));

        return spots.stream().map(spot -> {
            List<SpotReservationDto> spotReservations = reservationsBySpot.getOrDefault(spot.getSpotId(), List.of());
            spot.setReservations(spotReservations); // Set an empty list if no reservations exist
            return spot;
        }).collect(Collectors.toList());

    }

    public List<ReservationDto> fetchAllReservations(int driverId) {
        return reservationRepository.getReservationsById(driverId);
    }

    @Transactional
    public void reserveSpot(int spotId, Timestamp startTime, Timestamp expectedEndTime, int driverId) {
        validateTimes(startTime, expectedEndTime);
        boolean isSpotAvailableWithLock = reservationRepository.isSpotAvailableWithLock(spotId);
        if (!isSpotAvailableWithLock) throw new IllegalArgumentException("Spot is not available.");

        boolean isOverlapping = reservationRepository.checkOverlapping(spotId, startTime, expectedEndTime);
        if (isOverlapping) throw new IllegalArgumentException("Reservation overlaps with an existing reservation.");

        if (driverId == -1) throw new RuntimeException("Driver not logged in.");
        reservationRepository.createReservation(spotId, startTime, expectedEndTime, driverId);
    }

    @Transactional
    public void cancelReservation(int spotId, int driverId) {
        reservationRepository.cancelReservation(spotId, driverId);
    }

    @Transactional
    public void completeReservation(int spotId, int driverId) {
        reservationRepository.completeReservation(spotId, driverId);
    }

    private void validateTimes(Timestamp startTime, Timestamp expectedEndTime) {
        if (!startTime.before(expectedEndTime)) {
            throw new IllegalArgumentException("Start time must be before the expected end time.");
        }
        // reservation duration is at least 1h
        long durationMillis = expectedEndTime.getTime() - startTime.getTime();
        long oneHourMillis = 60 * 60 * 1000;
        if (durationMillis < oneHourMillis) {
            throw new IllegalArgumentException("Minimum reservation duration is 1 hour.");
        }
    }

//    public void reserveSpot(int spotId, Timestamp startTime, Timestamp expectedEndTime, int driverId) {
//        validateTimes(startTime, expectedEndTime);
//        boolean isSpotAvailable = reservationRepository.isSpotAvailable(spotId);
//        if (!isSpotAvailable) throw new IllegalArgumentException("Spot is not available.");
//        if (driverId == -1) throw new RuntimeException("Driver not logged in.");
//        reservationRepository.createReservation(spotId, startTime, expectedEndTime, driverId);
//    }
//
//    private void validateTimes(Timestamp startTime, Timestamp expectedEndTime) {
//        if (!startTime.before(expectedEndTime)) {
//            throw new IllegalArgumentException("Start time must be before the expected end time.");
//        }
//        // reservation duration is at least 1h
//        long durationMillis = expectedEndTime.getTime() - startTime.getTime();
//        long oneHourMillis = 60 * 60 * 1000;
//        if (durationMillis < oneHourMillis) {
//            throw new IllegalArgumentException("Minimum reservation duration is 1 hour.");
//        }
//    }
//
//    public void cancelReservation(int spotId, int driverId) {
//        reservationRepository.cancelReservation(spotId, driverId);
//    }
//
//    public void completeReservation(int spotId, int driverId) {
//        reservationRepository.completeReservation(spotId, driverId);
//    }
//
//    public List<ReservationDto> fetchAllReservations(int driverId) {
//        return reservationRepository.getReservationsById(driverId);
//    }
}
