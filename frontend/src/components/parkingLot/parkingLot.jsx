import React, {useState} from "react";
import "./parkingLot.css";
import ParkingSpot from "../parkingSpot/parkingSpot";
export default function ParkingLot({lot}) {
    const [spots, setSpots] = useState([
        {
            spotId: "",
            spotType: "",
            status: "",
            reservations: [{
                reservationId: "",
                startTime: "",
                endTime: "",
                driver: {
                    driverId: "",
                    name: "",
                    plateNumber: ""
                },
                status: "",
            }],
        },
        {
            spotId: "",
            spotType: "",
            status: "",
            reservations: [{
                reservationId: "",
                startTime: "",
                endTime: "",
                driver: {
                    driverId: "",
                    name: "",
                    plateNumber: ""
                },
                status: "",
            }],
        },
        {
            spotId: "",
            spotType: "",
            status: "",
            reservations: [
                {
                    reservationId: "",
                    startTime: "",
                    endTime: "",
                    driver: {
                        driverId: "",
                        name: "",
                        plateNumber: ""
                    },
                    status: "",
                },
                {
                    reservationId: "",
                    startTime: "",
                    endTime: "",
                    driver: {
                        driverId: "",
                        name: "",
                        plateNumber: ""
                    },
                    status: "",
                },
                {
                    reservationId: "",
                    startTime: "",
                    endTime: "",
                    driver: {
                        driverId: "",
                        name: "",
                        plateNumber: ""
                    },
                    status: "",
                },
                {
                    reservationId: "",
                    startTime: "",
                    endTime: "",
                    driver: {
                        driverId: "",
                        name: "",
                        plateNumber: ""
                    },
                    status: "",
                },
            ],
        },
    ]);
    return (
        <div className="parking-lot-body">
            <div className="lot-header">
                <h3>Parking Lot</h3>
                <div className="lot-report-button">
                    View Report
                </div>
            </div>
            <div className="lot-body">
                <div className="lot-info">
                    <p>Lot Name: {lot.name}</p>
                    <p>Location: {lot.location}</p>
                    <p>Capacity: {lot.capacity}</p>
                </div>
                <div className="lot-spots">
                    {
                        spots.map((spot) => {
                            return <ParkingSpot spot={spot} key={spot.spotId} />
                        })
                    }
                </div>
            </div>
        </div>
    );
}