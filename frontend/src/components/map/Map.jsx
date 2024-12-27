import React, { useEffect, useRef } from 'react';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import 'leaflet-routing-machine';
import 'leaflet-routing-machine/dist/leaflet-routing-machine.css';
import './Map.css';

function Map({lots, chooseLot}) {
    const mapRef = useRef(null); // Ref for the map
    const userMarkerRef = useRef(null); // Ref for the user's marker
    const routingControlRef = useRef(null); // Ref for the routing control
    const userLocationRef = useRef(null); // Ref to store the user's location
    const locationOnceRef = useRef(false); // Ref to set the location once

    useEffect(() => {
        console.log(lots);

        // Initialize the map
        const map = L.map('map').setView([31.240000, 29.960000], 13);
        mapRef.current = map;

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors',
        }).addTo(map);

        // Add pinned locations with red markers
        const redIcon = L.icon({
            iconUrl: 'https://cdn-icons-png.flaticon.com/512/684/684908.png',
            iconSize: [30, 30],
            iconAnchor: [15, 30],
        });

        lots.forEach((lot) => {
            
            L.marker(lot.coordinates, { icon: redIcon })
                .addTo(map)
                .on('click', () => handleLocationClick(lot.coordinates));
        });

        // Watch the user's location
        const watchId = navigator.geolocation.watchPosition(
            (position) => {
                updateUserLocation(position.coords.latitude, position.coords.longitude, map);
            },
            (error) => {
                console.error('Error watching location:', error.message);
            },
            { enableHighAccuracy: true, maximumAge: 0 }
        );

        // Fallback: Periodically fetch the user's location every 5 seconds
        const intervalId = setInterval(() => {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    console.log('Fallback: User location:');
                    updateUserLocation(position.coords.latitude, position.coords.longitude, map);
                },
                (error) => {
                    console.error('Error getting location:', error.message);
                },
                { enableHighAccuracy: true }
            );
        }, 2000);

        return () => {
            // Clean up map, location watcher, and interval
            map.remove();
            navigator.geolocation.clearWatch(watchId);
            clearInterval(intervalId);
        };
    }, []);

    const updateUserLocation = (latitude, longitude, map) => {
        const newUserLocation = [latitude, longitude];
        userLocationRef.current = newUserLocation;

        console.log('User location:', newUserLocation);

        // Remove the previous user marker
        if (userMarkerRef.current) {
            map.removeLayer(userMarkerRef.current);
        }

        // Add a new marker for the user's location
        userMarkerRef.current = L.marker(newUserLocation).addTo(map);

        // Center the map on the user's location (once only)
        if (!locationOnceRef.current) {
            map.setView(newUserLocation, 13);
            locationOnceRef.current = true;
        }
    };

    const handleLocationClick = (targetCoordinates) => {
        
        const map = mapRef.current;

        if (userLocationRef.current) {
            // Remove the existing route if any
            if (routingControlRef.current) {
                map.removeControl(routingControlRef.current);
                routingControlRef.current = null;
            }

            // Add a new route
            routingControlRef.current = L.Routing.control({
                waypoints: [
                    L.latLng(...userLocationRef.current),
                    L.latLng(...targetCoordinates),
                ],
                routeWhileDragging: true,
            }).addTo(map);
        } else {
            alert('User location not found yet!');
        }

        // filter the lots to get the chosen lot
        const chosenLot = lots.filter((lot) => lot.coordinates === targetCoordinates);
        console.log(chosenLot);
        chooseLot(chosenLot[0]);

    };

    return (
        <div className="map-container">
            <h2>Interactive Map with Pinned Locations</h2>
            <div id="map" style={{ height: '500px', borderRadius: '4px' }}></div>
        </div>
    );
}

export default Map;
