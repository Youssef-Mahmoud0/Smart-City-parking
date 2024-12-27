const backedURL = import.meta.env.VITE_BACKEND_URL;

async function getManagerLots() {
    const url = `${backedURL}/manager/lots/1`;
    const response = await fetch(url, {
        method: 'GET',
        credentials: 'include',
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

async function getLotReport(lotId) {
    const url = `${backedURL}/manager/report/${lotId}`;
    const response = await fetch(url, {
        method: 'GET',
        credentials: 'include',
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