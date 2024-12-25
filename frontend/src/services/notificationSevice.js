const backedURL = import.meta.env.VITE_BACKEND_URL;
async function getDriverNotifications(driverID) {

    const url = `${backedURL}/driver/notifications?driverID=${driverID}`;
    const response = await fetch(url, {
        method: 'GET',
        // credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    const data = await response.json();
    console.log(data);

    if (!response.ok)
        throw new Error('Failed to fetch jobs');
    
    return data;
}

async function seeNotifications(driverID) {
    const url = `${backedURL}/driver/notifications/seen?driverID=${driverID}`;
    const response = await fetch(url, {
        method: 'PATCH',
        // credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    if (!response.ok)
        throw new Error('Failed to fetch jobs');
    
}

export { 
    getDriverNotifications ,
    seeNotifications
};