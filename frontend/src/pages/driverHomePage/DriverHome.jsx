import React, { useState, useEffect } from "react";
import Header from "../../components/header/Header";
import DriverProfile from "../../components/driverProfile/DriverProfile";
import PenaltiesList from "../../components/penaltiesList/PenaltiesList";
import Map from "../../components/map/Map";
import ParkingSpotsGrid from "../../components/parkingSpotsGrid/ParkingSpotsGrid";

import { fetchParkingLots, fetchParkingSpots } from "../../services/driverHomeService";

import "./DriverHome.css";


function DriverHome() {

    const [lots, setLots] = useState([]);
    const [chosenLot, setChosenLot] = useState(null);
    const [parkingSpots, setParkingSpots] = useState([]);

    const [isLoading, setIsLoading] = useState(true); // Add loading state

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

    return (
        <div className="driver-home">
            <Header title={"Driver"} />
            <main className="driver-main">
                <div className="content-container">
                    <DriverProfile />
                    <PenaltiesList />
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