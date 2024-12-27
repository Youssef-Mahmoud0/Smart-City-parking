let url = import.meta.env.VITE_BACKEND_URL;

export async function fetchParkingLots() {
      
    const response = await fetch(`${url}/lots`,
    {
        method: 'GET',
        // credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        }
    });

    if (!response.ok) {
        throw new Error('An error occurred while fetching the lots');
    }

    const lots = await response.json();
    // console.log(lots);
    return lots;

}


export async function fetchParkingSpots(lotId) {
    const response = await fetch(`${url}/lot/${lotId}/spots`,
    {
        method: 'GET',
        // credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        }
    });

    if (!response.ok) {
        throw new Error('An error occurred while fetching the parking spots');
    }

    const spots = await response.json();
    console.log(spots);
    return spots;
}


export async function reserveSpot(spotId, startTime, endTime) {
    startTime = startTime.replace('T', ' ');
    endTime = endTime.replace('T', ' ');

    console.log(startTime);
    console.log(endTime);

    const response = await fetch(`${url}/1/spots/${spotId}/reserve`,
    {
        method: 'POST',
        // credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            startTime,
            endTime,
        }),
    });

    if (!response.ok) {
        throw new Error('An error occurred while reserving the spot');
    }

    const reservation = await response.text();
    console.log(reservation);
    return reservation;
}



export async function fetchSpotReservations(spotId) {
    const response = await fetch(`${url}/spots/${spotId}/reservations`,
    {
        method: 'GET',
        // credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        }
    });

    if (!response.ok) {
        throw new Error();
    }

    const reservations = await response.json();
    console.log(reservations);
    return reservations;
}