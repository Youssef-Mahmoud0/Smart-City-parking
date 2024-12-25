import React, { useEffect, useRef, useState } from 'react';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import 'leaflet-routing-machine';
import 'leaflet-routing-machine/dist/leaflet-routing-machine.css';
import './MapPlaceholder.css';

function MapPlaceholder() {
    const mapRef = useRef(null); // Ref for the map
    const userMarkerRef = useRef(null); // Ref for the user's marker
    const routingControlRef = useRef(null); // Ref for the routing control
    const userLocationRef = useRef(null); // Ref to store the user's location
    const locationOnceRef = useRef(false); // Ref to set the location once


    const locationIndexRef = useRef(0); // Ref to keep track of simulated location index

    // Simulated locations for testing
    const simulatedLocations = [
        [31.240000, 29.960000],
        [31.242000, 29.962000],
        [31.244000, 29.964000],
        [31.246000, 29.966000],
        [31.248000, 29.968000],
    ];



    const pinnedLocations = [
        { id: 1, name: 'Location A', coordinates: [31.238834, 29.959061] },
        { id: 2, name: 'Location B', coordinates: [31.247264, 29.972941] },
        { id: 3, name: 'Location C', coordinates: [31.238834, 29.969755] },
    ];


    useEffect(() => {
        // Initialize the map
        const map = L.map('map').setView([51.505, -0.09], 13);
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

        pinnedLocations.forEach((location) => {
            L.marker(location.coordinates, { icon: redIcon })
                .addTo(map)
                .on('click', () => handleLocationClick(location.coordinates));
        });

        // // Watch the user's location
        const watchId = navigator.geolocation.watchPosition(
            (position) => {
                console.log(locationOnceRef.current); // Debug log
                console.log('User location updated:'); // Debug log
                const { latitude, longitude } = position.coords;
                console.log('User location updated:', latitude, longitude); // Debug log
                const newUserLocation = [latitude, longitude];
                userLocationRef.current = newUserLocation;

                // Remove the previous user marker
                if (userMarkerRef.current) {
                    map.removeLayer(userMarkerRef.current);
                }

                // Add a new marker for the user's location
                userMarkerRef.current = L.marker(newUserLocation).addTo(map);

                // Center the map on the user's location (optional)
                if (!locationOnceRef.current) {
                    map.setView(newUserLocation, 13);
                    locationOnceRef.current = true;
                }
            },
            (error) => {
                console.error('Error watching location:', error.message);
            },
            { enableHighAccuracy: true, maximumAge: 0 }
        );

        return () => {
            // Clean up map and location watcher
            map.remove();
            navigator.geolocation.clearWatch(watchId);
        };
    }, []);

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
    };

    return (
        <div className="map-container">
            <h2>Interactive Map with Pinned Locations</h2>
            <div id="map" style={{ height: '500px', borderRadius: '4px' }}></div>
        </div>
    );
}

export default MapPlaceholder;
