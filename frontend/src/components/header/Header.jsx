import React, {useEffect, useState} from 'react';
import './Header.css';

function Header({ title }) {
    const [notifications, setNotifications] = useState([]);
    const [showNotifications, setShowNotifications] = useState(false);
    useEffect(() => {
        if (title != 'Driver') return
        // Establish WebSocket connection for notifications
        const socket = new WebSocket(`ws://localhost:8080/notifications?driverID=${1}`); // Replace with your WebSocket endpoint

        socket.onopen = () => {
            console.log('WebSocket connection established');
        };

        socket.onmessage = (event) => {
            const notification = JSON.parse(event.data);
            setNotifications((prevNotifications) => [notification, ...prevNotifications]);
        };

        socket.onclose = () => {
            console.log('WebSocket connection closed');
        };

        socket.onerror = (error) => {
            console.error('WebSocket error:', error);
        };

    }, []);
    return (
        <header className="app-header">
            <h1>{title} Dashboard</h1>
            {title === 'Driver' && (
                <div className="notification-container">
                    <div className="notification-icon">
                        <i className="fa-solid fa-bell" onClick={() => setShowNotifications(!showNotifications)}></i>
                        {
                            notifications.length > 0 && (
                                <div className="notification-count">
                                    {notifications.length}
                                </div>
                            )
                        }
                    </div>
                    {showNotifications && (
                        <div className="notification-list">
                            {notifications.map((notification, index) => (
                                <div key={index} className="notification-item">
                                    {notification.message}
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            )}
        </header>
    );
}

export default Header;

