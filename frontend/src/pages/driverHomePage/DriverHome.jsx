import React, { useState, useEffect } from "react";
import Header from "../../components/header/Header";
import DriverProfile from "../../components/driverProfile/DriverProfile";
import PenaltiesList from "../../components/penaltiesList/PenaltiesList";
import ReservationsList from "../../components/reservationsList/ReservationsList";
import Map from "../../components/map/Map";
import ParkingSpotsGrid from "../../components/parkingSpotsGrid/ParkingSpotsGrid";

import { 
    fetchParkingLots,
    fetchParkingSpots, 
    fetchDriver, 
    fetchReservations,
    cancelReservation

 } from "../../services/driverHomeService";

import "./DriverHome.css";

function DriverHome() {

    const [driver, setDriver] = useState(null);
    const [loadingDriver, setLoadingDriver] = useState(true);

    const [lots, setLots] = useState([]);
    const [chosenLot, setChosenLot] = useState(null);
    const [parkingSpots, setParkingSpots] = useState([]);

    const [isLoading, setIsLoading] = useState(true); // Add loading state

    const [reservations, setReservations] = useState([]);
    const [loadingReservations, setLoadingReservations] = useState(true);



    


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
        }

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
        }

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
        }

        getReservations();
    }, []);


    const processLocation = (lots) => {
        lots.forEach((lot) => {
            lot.coordinates = [lot.latitude, lot.longitude];
        });
    }

    const chooseLot = async (lot) => {
        setChosenLot(lot);
        console.log(lot.lotId);
        const fetchedParkingSpots = await fetchParkingSpots(lot.lotId);

        console.log(fetchedParkingSpots);
        setParkingSpots(fetchedParkingSpots);


    }



    const onCancelReservation = async (reservation) => {


        try {
            const response = await cancelReservation(reservation);
            console.log(response);
            const fetchedReservations = await fetchReservations();
            setReservations(fetchedReservations);
        } catch (error) {
            console.error("Error canceling reservation:", error);
        }


    }


    return (
        <div className="driver-home">
            <Header title={"Driver"} />
            <main className="driver-main">
                <div className="content-container">

                    {
                        loadingDriver ? (
                            <div>Loading driver...</div>
                        ) : (
                            <DriverProfile driver={driver} />
                        )
                    }
                    
                    {/* <PenaltiesList /> */}

                    <ReservationsList
                        reservations={reservations}
                        onCheckIn={(reservation) => console.log("Check In:", reservation)}
                        onCancel={(reservation) => onCancelReservation(reservation)}
                        onCheckout={(reservation) => console.log("Check Out:", reservation)}
                    />


                    {isLoading ? (
                        <div>Loading parking lots...</div>
                    ) : (
                        <Map
                            lots={lots}
                            chooseLot={chooseLot}
                        />
                    )}
                    {
                        chosenLot &&
                        <ParkingSpotsGrid
                            parkingSpots={parkingSpots}
                            lot={chosenLot}
                        />
                    }

                </div>
            </main>
        </div>


    );
}

export default DriverHome;