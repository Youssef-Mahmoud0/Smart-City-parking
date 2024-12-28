import React, { useState, useEffect } from "react";
import Header from "../../components/header/Header";
import DriverProfile from "../../components/driverProfile/DriverProfile";
import PenaltiesList from "../../components/penaltiesList/PenaltiesList";
import ReservationsList from "../../components/reservationsList/ReservationsList";
import Map from "../../components/map/Map";
import ParkingSpotsGrid from "../../components/parkingSpotsGrid/ParkingSpotsGrid";
import Payment from "../../components/payment/Payment";

import {
    fetchParkingLots,
    fetchParkingSpots,
    fetchDriver,
    fetchReservations,
    cancelReservation,
    checkIn,
    checkOut,
} from "../../services/driverHomeService";

import "./DriverHome.css";

function DriverHome() {
    const [driver, setDriver] = useState(null);
    const [loadingDriver, setLoadingDriver] = useState(true);

    const [lots, setLots] = useState([]);
    const [chosenLot, setChosenLot] = useState(null);
    const [parkingSpots, setParkingSpots] = useState([]);

    const [isLoading, setIsLoading] = useState(true);

    const [reservations, setReservations] = useState([]);
    const [loadingReservations, setLoadingReservations] = useState(true);

    const [showPayment, setShowPayment] = useState(false);
    const [reservationToCheckout, setReservationToCheckout] = useState(null);

    useEffect(() => {
        const getLots = async () => {
            try {
                setIsLoading(true);
                const fetchedLots = await fetchParkingLots();
                processLocation(fetchedLots);
                setLots(fetchedLots);
            } catch (error) {
                console.error("Error fetching lots:", error);
            } finally {
                setIsLoading(false);
            }
        };

        getLots();
    }, []);

    useEffect(() => {
        setLoadingDriver(true);
        const getDriver = async () => {
            try {
                const fetchedDriver = await fetchDriver();
                setDriver(fetchedDriver);
            } catch (error) {
                console.error("Error fetching driver:", error);
            } finally {
                setLoadingDriver(false);
            }
        };

        getDriver();
    }, []);

    useEffect(() => {
        setLoadingReservations(true);
        const getReservations = async () => {
            try {
                const fetchedReservations = await fetchReservations();
                setReservations(fetchedReservations);
            } catch (error) {
                console.error("Error fetching reservations:", error);
            } finally {
                setLoadingReservations(false);
            }
        };

        getReservations();
    }, []);

    const processLocation = (lots) => {
        lots.forEach((lot) => {
            lot.coordinates = [lot.latitude, lot.longitude];
        });
    };

    const chooseLot = async (lot) => {
        setChosenLot(lot);
        const fetchedParkingSpots = await fetchParkingSpots(lot.lotId);
        setParkingSpots(fetchedParkingSpots);
    };

    const onCancelReservation = async (reservation) => {
        try {
            await cancelReservation(reservation);
            const fetchedReservations = await fetchReservations();
            setReservations(fetchedReservations);
        } catch (error) {
            console.error("Error canceling reservation:", error);
        }
    };

    const onCheckIn = async (reservation) => {
        try {
            await checkIn(reservation);
            const fetchedReservations = await fetchReservations();
            setReservations(fetchedReservations);
        } catch (error) {
            console.error("Error checking in:", error);
        }
    };

    const onCheckout = (reservation) => {
        setReservationToCheckout(reservation);
        setShowPayment(true);
    };

    const handlePaymentSuccess = async () => {
        try {
            if (reservationToCheckout) {
                await checkOut(reservationToCheckout);
                const fetchedReservations = await fetchReservations();
                setReservations(fetchedReservations);
            }
        } catch (error) {
            console.error("Error checking out:", error);
        } finally {
            setShowPayment(false);
            setReservationToCheckout(null);
        }
    };

    return (
        <div className="driver-home">
            <Header title={"Driver"} />
            <main className="driver-main">
                <div className="content-container">
                    {loadingDriver ? (
                        <div>Loading driver...</div>
                    ) : (
                        <DriverProfile driver={driver} />
                    )}

                    <ReservationsList
                        reservations={reservations}
                        onCheckIn={(reservation) => onCheckIn(reservation)}
                        onCancel={(reservation) => onCancelReservation(reservation)}
                        onCheckout={(reservation) => onCheckout(reservation)}
                    />

                    {isLoading ? (
                        <div>Loading parking lots...</div>
                    ) : (
                        <Map lots={lots} chooseLot={chooseLot} />
                    )}
                    {chosenLot && (
                        <ParkingSpotsGrid parkingSpots={parkingSpots} lot={chosenLot} />
                    )}
                </div>

                {showPayment && (
                    <div className="payment-modal">
                        <PaymentOptions
                            onPaymentSuccess={handlePaymentSuccess}
                            onCancel={() => setShowPayment(false)}
                        />
                    </div>
                )}
            </main>
        </div>
    );
}

export default DriverHome;
