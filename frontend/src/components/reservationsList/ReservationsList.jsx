import React, { useState } from "react";
import Payment from "../payment/Payment";
import "./ReservationsList.css";

const ReservationsList = ({ reservations, onCheckIn, onCancel, onCheckout }) => {
    const [showPayment, setShowPayment] = useState(false);

    const handleCheckout = (reservation) => {
        setShowPayment(true);
        onCheckout(reservation);
    };

    return (
        <div className="reservations-list">
            <h2>Your Reservations</h2>
            {reservations.length === 0 ? (
                <p>No reservations found.</p>
            ) : (
                reservations.map((reservation) => (
                    <div key={reservation.spotId} className="reservation-item">
                        <p><strong>Start Time:</strong> {reservation.startTime.replace("T", " ")}</p>
                        <p><strong>End Time:</strong> {reservation.endTime.replace("T", " ")}</p>
                        <div className="reservation-actions">
                            {reservation.status === "WAITING_FOR_ARRIVAL" && (
                                <>
                                    <button onClick={() => onCheckIn(reservation)} className="action-button">
                                        Check In
                                    </button>
                                    <button onClick={() => onCancel(reservation)} className="action-button cancel-button">
                                        Cancel
                                    </button>
                                </>
                            )}
                            {reservation.status === "DRIVER_ARRIVED" && (
                                <button onClick={() => handleCheckout(reservation)} className="action-button checkout-button">
                                    Check Out
                                </button>
                            )}
                        </div>
                    </div>
                ))
            )}

            {showPayment && (
                <div className="payment-modal">
                    <Payment 
                        onCancel={() => setShowPayment(false)}
                        onPaymentSuccess={() => setShowPayment(false)}
                    />
                </div>
            )}
        </div>
    );
};

export default ReservationsList;
