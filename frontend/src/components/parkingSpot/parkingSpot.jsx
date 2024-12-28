import React, {useState, useEffect} from "react";
import "./parkingSpot.css";
import {getSpotReservations} from "../../services/managerService";
export default function ParkingSpot({spot}) {
    const [showDetails, setShowDetails] = useState(false);
    const [reservations, setReservations] = useState([{
        reservationId: "",
        startTime: "",
        endTime: "",
        driver: {
            driverId: "",
            name: "",
            plateNumber: ""
        },
        status: "",
    }]);
    const handleShowDetails = async () => {
        setShowDetails(true);
        try {
            const fetchedSpotReservations = await getSpotReservations(spot.spotId);
            setReservations(fetchedSpotReservations);
        } catch (error) {
            console.error("Error fetching lots:", error);
        } 
    }

    return (
        <div className="parking-spot">
            <h4>Parking Spot</h4>
            <div className="spot-body">
                <div className="spot-info">
                    <p>Spot ID: {spot.spotId}</p>
                    <p>Spot Type: {spot.spotType}</p>
                    <p>Spot Status: {spot.status}</p>
                </div>
                {
                    showDetails &&
                    <div className="spot-reservations">
                        <h4>Reservations</h4>
                        <ul>
                            {reservations.map((reservation) => {
                                return (
                                    <li key={reservation.reservationId} className="reservation">
                                        <div className="reservation-info">
                                            <p>Reservation ID: {reservation.reservationId}</p>
                                            <p>Start Time: {reservation.startTime}</p>
                                            <p>End Time: {reservation.endTime}</p>
                                            <p>Status: {reservation.status}</p>
                                            <p>Price: {reservation.price}</p>
                                            <p>Penality: {reservation.penality}</p>
                                        </div>
                                        <div className="driver-info">
                                            <h4>Driver Info</h4>
                                            <p>Driver Name: {reservation.driver.name}</p>
                                            <p>Plate Number: {reservation.driver.plateNumber}</p>
                                        </div>
                                    </li>
                                )
                            })}
                        </ul>
                        <button onClick={() => setShowDetails(false)}>Hide Reservations</button>
                    </div>
                }
                {
                    showDetails?
                    <button onClick={() => setShowDetails(false)}>Hide Reservations</button>:
                    <button onClick={() => handleShowDetails()}>Show Reservations</button>
                }
            </div>
        </div>
    )
}