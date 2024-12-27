import React, { useEffect, useState } from 'react';
import './DriverProfile.css';

function DriverProfile({driver}) {

    const driverData = {
        name: 'John Doe',
        phoneNumber: '(555) 123-4567',
        licenseNumber: 'DL123456789',
        licensePlate: 'ABC 123',
        paymentMethod: 'Visa ending in 4242',
    };
    return (
        <div className="profile-container">
            <h2>Driver Profile</h2>
            {driverData ? (
                <div className="profile-info">
                    <div className="info-group">
                        <label>Name:</label>
                        <span>{driver.name}</span>
                    </div>
                    <div className="info-group">
                        <label>Email:</label>
                        <span>{driver.email}</span>
                    </div>
                    <div className="info-group">
                        <label>Phone:</label>
                        <span>{driver.phoneNumber}</span>
                    </div>
                    <div className="info-group">
                        <label>License Number:</label>
                        <span>{driver.licensePlateNumber}</span>
                    </div>
                    <div className="info-group">
                        <label>Payment Method:</label>
                        <span>{driver.paymentMethod}</span>
                    </div>
                    {
                        driver.penalty > 0 &&
                        <div className="info-group">
                            <label>Penalty:</label>
                            <span>You owe us {driver.penalty} pounds</span>
                        </div>
                    }
                    

                </div>
            ) : (
                <div>No driver data available</div>
            )}
        </div>
    );
}

export default DriverProfile;
