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
        System.out.println(spots);
        List<SpotReservationDto> reservations = parkingLotRepository.getSpotReservations(lotId)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        System.out.println(reservations);

        Map<Integer, List<SpotReservationDto>> reservationsBySpot = reservations.stream().collect(Collectors.groupingBy(SpotReservationDto::getSpotId));

        return spots.stream().map(spot -> {
            List<SpotReservationDto> spotReservations = reservationsBySpot.getOrDefault(spot.getSpotId(), List.of())
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            spot.setReservations(spotReservations);
            return spot;
        }).collect(Collectors.toList());
    }

    public List<ReservationDto> fetchAllReservationsAfterNow(int driverId) {
        return reservationRepository.getReservationsById(driverId);
    }

    @Transactional
    public void reserveSpot(int lotId, int spotId, Timestamp startTime, Timestamp endTime, int driverId) {
        validateTimes(startTime, endTime);

        boolean isOverlapping = reservationRepository.checkOverlapping(spotId, startTime, endTime);
        if (isOverlapping) throw new IllegalArgumentException("Reservation overlaps with an existing reservation.");

        if (driverId == -1) throw new RuntimeException("Driver not logged in.");
        reservationRepository.createReservation(lotId, spotId, startTime, endTime, driverId);
    }

    @Transactional
    public void cancelReservation(int reservationId, int spotId, int driverId) {
        reservationRepository.cancelReservation(reservationId, spotId, driverId);
    }

    @Transactional
    public void completeReservation(int reservationId, int spotId, int driverId) {
        reservationRepository.completeReservation(reservationId, spotId, driverId);
    }

    private void validateTimes(Timestamp startTime, Timestamp endTime) {
        if (!startTime.before(endTime)) {
            throw new IllegalArgumentException("Start time must be before the end time.");
        }
        // reservation duration is at least 1h and at most 1d
        long durationMillis = endTime.getTime() - startTime.getTime();
        long oneHourMillis = 60 * 60 * 1000;
        long oneDayMillis = 24 * 60 * 60 * 1000;
        if (durationMillis < oneHourMillis) {
            throw new IllegalArgumentException("Minimum reservation duration is 1 hour.");
        }
        if (durationMillis > oneDayMillis) {
            throw new IllegalArgumentException("Maximum reservation duration is 1 day.");
        }
    }

    public List<ReservationDto> fetchAllReservationsForSpot(int spotId) {
        return reservationRepository.getReservationsBySpotId(spotId);
    }

    public void checkIn(int reservationId, int spotId, int driverId) {
        reservationRepository.checkIn(reservationId, spotId, driverId);
    }
}
