import React from "react";
import Header from "../../components/header/Header";
import DriverProfile from "../../components/driverProfile/DriverProfile";
import PenaltiesList from "../../components/penaltiesList/PenaltiesList";
import MapPlaceholder from "../../components/mapPlaceholder/MapPlaceholder";

import "./DriverHome.css";

function DriverHome() {

    return (
        <div className="driver-home">
            <Header title={"Driver"}/>
            <main className="driver-main">
                <div className="content-container">
                    <DriverProfile />
                    <PenaltiesList />
                    <MapPlaceholder />
                </div>
            </main>
        </div>


    );
}

export default DriverHome;