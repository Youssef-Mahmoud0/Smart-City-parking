import React, { useState, useEffect } from "react";
import Header from "../../components/header/Header";
import "./AdminHomePage.css";
import { getAdminReports } from "../../services/adminService";
function AdminHomePage() {
    const handleClick = async () => {
        try {
            const blob = await getAdminReports();
            const pdfUrl = URL.createObjectURL(blob);
             window.open(pdfUrl, "_blank");
        } catch (error) {
            console.error("Error fetching reports:", error);
        }
    }

    return (
        <div className="driver-home">
            <Header title={"Admin"} />
            <main className="driver-main">
                <div className="admin-content-container">
                    <button onClick={handleClick}>View Reports</button>
                </div>
            </main>
        </div>
    );
}
export default AdminHomePage;