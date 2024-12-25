import React from 'react';
import './DriverProfile.css';

function DriverProfile() {
    // Mock data - replace with actual data later
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
            <div className="profile-info">
                <div className="info-group">
                    <label>Name:</label>
                    <span>{driverData.name}</span>
                </div>
                <div className="info-group">
                    <label>Phone:</label>
                    <span>{driverData.phoneNumber}</span>
                </div>
                <div className="info-group">
                    <label>License Number:</label>
                    <span>{driverData.licenseNumber}</span>
                </div>
                <div className="info-group">
                    <label>License Plate:</label>
                    <span>{driverData.licensePlate}</span>
                </div>
                <div className="info-group">
                    <label>Payment Method:</label>
                    <span>{driverData.paymentMethod}</span>
                </div>
            </div>
        </div>
    );
}

export default DriverProfile;