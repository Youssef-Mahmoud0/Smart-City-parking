import React, {useEffect, useState} from 'react';
import './Header.css';
import { getDriverNotifications, seeNotifications } from '../../services/notificationSevice';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function Header({ title }) {
    const [notifications, setNotifications] = useState([]);
    const [showNotifications, setShowNotifications] = useState(false);
    const driverID = document.cookie.split('id=')[1];
    console.log(driverID)
    useEffect(() => {
        if (title != 'Driver') return
        handleGetNotifications()
    }, [])
    useEffect(() => {
        console.log(showNotifications)
        seeNotifications(driverID)
        handleGetNotifications()
    }, [showNotifications])
    useEffect(() => {
        if (title != 'Driver') return
        // Establish WebSocket connection for notifications
        const socket = new WebSocket(`ws://localhost:8080/notifications?driverID=${driverID}`); // Replace with your WebSocket endpoint

        socket.onopen = () => {
            console.log('WebSocket connection established');
        };

        socket.onmessage = (event) => {
            const notification = JSON.parse(event.data);
            toast(notification.message, {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                type: notification.type === 'error' ? 'confirmation' : 'success'
            });
            handleGetNotifications()
        };

        socket.onclose = () => {
            console.log('WebSocket connection closed');
        };

        socket.onerror = (error) => {
            console.error('WebSocket error:', error);
        };

    }, []);

    const handleGetNotifications = async() => {
        const notifications = await getDriverNotifications(driverID)
        setNotifications(notifications)
    }
    return (
        <header className="app-header">
            <h1>{title} Dashboard</h1>
            {title === 'Driver' && (
                <div className="notification-container">
                    <div className="notification-icon">
                        <i className="fa-solid fa-bell" onClick={() => setShowNotifications(!showNotifications)}></i>
                        {
                            notifications.filter(notification => notification.seen === false).length > 0 && (
                                <div className="notification-count">
                                    {notifications.filter(notification => notification.seen === false).length}
                                </div>
                            )
                        }
                    </div>
                    {showNotifications && (
                        <div className="notification-list">
                            {notifications.map((notification, index) => (
                                <div key={index} className="notification-item">
                                    <div className="notification-type">
                                        {notification.type == 'penality' ?
                                          <i className="fa-solid fa-money-bill-trend-up penality"></i>  :  
                                          <i className="fa-solid fa-circle-check confirmation"></i>
                                        }
                                    </div>
                                    <div className="notification-body">
                                        <div className={`notification-message ${notification.seen ? 'seen' : 'unseen'}`}>
                                            {notification.message}
                                        </div>
                                        <div className="notification-date">
                                            {notification.createdAt}
                                        </div>
                                    </div>
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

