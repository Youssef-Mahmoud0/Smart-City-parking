import React, { useState } from 'react';
import { BatteryCharging, Accessibility } from 'lucide-react';
import './ParkingSpotsGrid.css';
import CarIcon from './CarIcon';
import Calender from '../calender/Calendar';

import { SPOT_TYPES, SPOT_STATUS } from '../../utils/spotUtils';

import {reserveSpot, fetchSpotReservations} from '../../services/driverHomeService';

const ParkingLotGrid = ({ parkingSpots, lot, onSpotSelect }) => {

    const [selectedSpotId, setSelectedSpotId] = useState(null);
    const [selectedSpotOrder, setSelectedSpotOrder] = useState(null);
    const [selectedSpot, setSelectedSpot] = useState(null);


    const getSpotColorClass = (status, isSelected) => {
        if (isSelected && status == SPOT_STATUS.AVAILABLE) return 'green-car';

        switch (status) {
            // case SPOT_STATUS.AVAILABLE:
            // return 'black-car';
            case SPOT_STATUS.OCCUPIED:
                return 'red-car';
            case SPOT_STATUS.RESERVED:
                return 'yellow-car';
            default:
                return 'spot-default';
        }
    };

    const SpotIcon = ({ type }) => {
        switch (type) {
            case SPOT_TYPES.EV:
                return <BatteryCharging className="icon" />;
            case SPOT_TYPES.DISABLED:
                return <Accessibility className="icon" />;
        }
    };

    const handleSpotClick = async (spot) => {
        // if (spot.status !== SPOT_STATUS.AVAILABLE) return;

        setSelectedSpotId(spot.spotId);
        setSelectedSpotOrder(spot.order);

        const spotReservations = await fetchSpotReservations(spot.spotId);       
        console.log(spotReservations);
        spot.reservations = spotReservations;

        setSelectedSpot(spot);


    };

    const handleReservationFromParking = async (spotId, startTime, endTime) => {

        try {
            const response = await reserveSpot(spotId, startTime, endTime);
            console.log(response);
            setSelectedSpot(null);
            setSelectedSpotId(null);
            setSelectedSpotOrder(null);


        } catch (error) {
            console.error('Error reserving spot:', error);
        }

    };



    const handleDeselectSpot = () => {
        setSelectedSpot(null);
        setSelectedSpotId(null);
        setSelectedSpotOrder(null);
    }

    return (
        <div className="parking-lot-container">
            <div className="header">
                <h2 className="title">Parking Layout</h2>
                <p className="subtitle">
                    {selectedSpotId
                        ? `Spot ${selectedSpotOrder} selected`
                        : 'Select an available spot'}
                </p>
            </div>

            <div className="grid-layout">
                {parkingSpots.map((spot, index) => (
                    <div
                        key={spot.spotId}
                        className={`spot ${getSpotColorClass(spot.status, spot.spotId == selectedSpotId)}-container`}
                        onClick={() => handleSpotClick(spot)}
                    >
                        <CarIcon carClass={getSpotColorClass(spot.status, spot.spotId == selectedSpotId)} />
                        <SpotIcon type={spot.type} />

                        {spot.status == SPOT_STATUS.AVAILABLE && spot.spotId !== selectedSpotId &&
                            <span className="spot-id">
                                {spot.order}
                            </span>
                        }

                    </div>
                ))}
            </div>

            <div className="info-section">
                <div className="info-box">
                    <h3 className="info-title">Status Types</h3>
                    <div className="info-items">
                        <div className="info-item">
                            <div className="status-occupied" />
                            <span className="status-label">Occupied</span>
                        </div>
                        <div className="info-item">
                            <div className="status-reserved" />
                            <span className="status-label">Reserved</span>
                        </div>
                    </div>
                </div>

                <div className="info-box">
                    <h3 className="info-title">Special Spot Types</h3>
                    <div className="info-items">
                        <div className="info-item">
                            <BatteryCharging className="icon-ev" />
                            <span className="spot-label">EV Charging</span>
                        </div>
                        <div className="info-item">
                            <Accessibility className="icon-disabled" />
                            <span className="spot-label">Accessible</span>
                        </div>

                    </div>
                </div>
            </div>

            <button
                onClick={handleReservationFromParking}
                disabled={!selectedSpotId}
                className={`reservation-button ${selectedSpotId ? 'enabled' : 'disabled'}`}
            >
                {selectedSpotId ? 'Reserve Selected Spot' : 'Select a Spot First'}
            </button>


            {
                selectedSpot &&
                <>
                    <div className="overlay"></div>

                    <div className="calender-container">
                        <Calender 
                            spotData={selectedSpot} 
                            handleDeselectSpot={handleDeselectSpot}
                            handleReservationFromParking={handleReservationFromParking}
                        />
                    </div>
                </>
            }



        </div>
    );
};

export default ParkingLotGrid;
