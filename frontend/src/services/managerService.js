const backedURL = import.meta.env.VITE_BACKEND_URL;

async function getManagerLots() {
  const url = "http://localhost:8080/manager";
  const response = await fetch(url, {
    method: "GET",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  });
  console.log(response);
  const data = await response.json();
  console.log(data);

  if (!response.ok) throw new Error("Failed to fetch jobs");

  return data;
}

async function getLotReport(lotId) {
  const url = "http://localhost:8080/auth/generate-report-manager";

  const response = await fetch(url, {
    method: "GET",
    credentials: "include",
    headers: {
      Accept: "application/pdf",
    },
  });
  if (!response.ok) {
    throw new Error("Failed to fetch report");
  }
  // Convert the response to a blob (binary data)
  const blob = await response.blob();
  return blob;
}

async function getSpotReservations(spotId) {
  const url = `http://localhost:8080/spots/${spotId}/reservations`;
  const response = await fetch(url, {
    method: "GET",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  });
  const data = await response.json();
  console.log(data);
  if (!response.ok) throw new Error("Failed to fetch jobs");

  return data;
}

export { getManagerLots, getLotReport, getSpotReservations };
