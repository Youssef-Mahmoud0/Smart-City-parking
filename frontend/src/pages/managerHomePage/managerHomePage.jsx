import React, {useState, useEffect} from "react";
import ParkingLot from "../../components/parkingLot/parkingLot";
import {getManagerLots} from "../../services/managerService";
function ManagerHomePage() {
    const [lots, setLots] = useState([]);
        useEffect(() => {
            const getLots = async () => {
                try {
                    const fetchedLots = await getManagerLots();
                    setLots(fetchedLots);
                } catch (error) {
                    console.error("Error fetching lots:", error);
                } 
            }
    
            getLots();
        }, []);

    return (
        <div className="driver-home">
            <header className="app-header">
                <h1>Lot Manager Dashboard</h1>
            </header>
            <main className="driver-main">
                <div className="content-container">
                    <div className="profile-container">
                        {lots.map((lot) => {
                            return <ParkingLot lot={lot} key={lot.id} />
                        })}
                    </div>
                </div>
            </main>
        </div>
    );
}

export default ManagerHomePage;