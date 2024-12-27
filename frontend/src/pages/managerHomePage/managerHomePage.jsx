import React, {useState} from "react";
import ParkingLot from "../../components/parkingLot/parkingLot";

function ManagerHomePage() {
    const managerID = 1;
    const [lots, setLots] = useState([{
        name: "",
        id: "",
        location: "",
        capacity: "",
        basePrice: "",
    }]);

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