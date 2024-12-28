import React, {useState} from "react";
import "./parkingLot.css";
import ParkingSpot from "../parkingSpot/parkingSpot";
import {getLotReport} from "../../services/managerService";
export default function ParkingLot({lot}) {
    const [spots, setSpots] = useState([
        {
            spotId: "1",
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
            spotId: "2",
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
            spotId: "3",
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
    // const [spots, setSpots] = lot.spots;

    const handleViewReport = async (lotId) => {
        try {
            const blob = await getLotReport(lotId);
            const pdfUrl = URL.createObjectURL(blob);
            window.open(pdfUrl, '_blank');
        } catch (error) {
            console.error("Error fetching report:", error);
        }
    }
    return (
        <div className="parking-lot-body">
            <div className="lot-header">
                <h3>Parking Lot</h3>
                <div className="lot-report-button">
                    <button onClick={() => handleViewReport(lot.id)}>
                        View Report
                    </button>
                </div>
            </div>
            <div className="lot-body">
                <div className="lot-info">
                    <p>Lot Name: {lot.name}</p>
                    <p>Capacity: {lot.capacity}</p>
                    <p>Base Price: {lot.basePrice}</p>
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