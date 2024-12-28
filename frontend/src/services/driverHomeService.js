let url = import.meta.env.VITE_BACKEND_URL;

export async function fetchDriver() {
    const response = await fetch(`${url}/driver`,
        {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            }
        });

    if (!response.ok) {
        throw new Error('An error occurred while fetching the driver');
    }

    const driver = await response.json();
    console.log(driver);
    return driver;
}




export async function fetchParkingLots() {

    const response = await fetch(`${url}/lots`,
        {
            method: 'GET',
            credentials: 'include',
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
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            }
        });

    if (!response.ok) {
        throw new Error('An error occurred while fetching the parking spots');
    }

    const spots = await response.json();
    // console.log(spots);
    return spots;
}


export async function reserveSpot(lotId, spotId, startTime, endTime) {
    startTime = startTime.replace('T', ' ');
    endTime = endTime.replace('T', ' ');


    console.log("inside reserve",startTime, endTime);

    const response = await fetch(`${url}/${lotId}/spots/${spotId}/reserve`,
        {
            method: 'POST',
            credentials: 'include',
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
    // console.log(reservation);
    return reservation;
}



export async function fetchSpotReservations(spotId) {
    const response = await fetch(`${url}/spots/${spotId}/reservations`,
        {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            }
        });

    if (!response.ok) {
        throw new Error();
    }

    const reservations = await response.json();
    reservations.forEach(reservation => {
        reservation.startTime = reservation.strStartTime;
        reservation.endTime = reservation.strEndTime;
    })

    console.log("This is the fetch in the frontend ",reservations);
    // console.log(reservations);
    return reservations;
}



export async function fetchReservationPrice(lotId, startTime, endTime) {
    console.log("inside fetch",startTime, endTime);
    const response = await fetch(`${url}/lot/${lotId}/price?startTime=${startTime}&endTime=${endTime}`,
        {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            }
        });

    if (!response.ok) {
        // throw new Error('An error occurred while fetching the reservation price');
    }

    // const price = await response.json();

    // console.log(price);
    // return price? price : 100;

    return 100;
}


export async function fetchReservations() {
    const response = await fetch(`${url}/reservations`,
        {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            }
        });

    if (!response.ok) {
        throw new Error('An error occurred while fetching the reservations');
    }

    const reservations = await response.json();
    
    reservations.forEach(reservation => {
        reservation.startTime = reservation.strStartTime;
        reservation.endTime = reservation.strEndTime;
    })
    
    console.log("this is the reservations object",reservations);
    return reservations;
}


export async function cancelReservation(reservation) {
    const response = await fetch(`${url}/spots/${reservation.spotId}/cancel`,
        {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            }
        });
        

    if (!response.ok) {
        throw new Error('An error occurred while canceling the reservation');
    }

    const result = await response.text();
    return result;
}