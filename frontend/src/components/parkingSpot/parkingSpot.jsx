import React, {useState} from "react";
import "./parkingSpot.css";

export default function ParkingSpot({spot}) {
    const [showDetails, setShowDetails] = useState(false);
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
                            {spot.reservations.map((reservation) => {
                                return (
                                    <li key={reservation.reservationId} className="reservation">
                                        <div className="reservation-info">
                                            <p>Reservation ID: {reservation.reservationId}</p>
                                            <p>Start Time: {reservation.startTime}</p>
                                            <p>End Time: {reservation.endTime}</p>
                                            <p>Status: {reservation.status}</p>
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
                    <button onClick={() => setShowDetails(true)}>Show Reservations</button>
                }
            </div>
        </div>
    )
}