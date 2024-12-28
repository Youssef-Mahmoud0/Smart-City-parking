async function getAdminReports(lotId) {
  const url = "http://localhost:8080/generate-report-admin";

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
export { getAdminReports };