import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AuthPage from './pages/driverAuthPage/AuthPage';
import DriverHome from './pages/driverHomePage/DriverHome';
import ManagerHomePage from './pages/managerHomePage/managerHomePage';
import './App.css'
import { ToastContainer } from 'react-toastify';


function App() {

    return (
        <>
            <Router>
                <Routes>
                    <Route path="/" element={<AuthPage />} />
                    <Route path="/home" element={<DriverHome />} />
                    <Route path="/manager/home" element={<ManagerHomePage />} />
                </Routes>
                <ToastContainer className="toast-container"/>
            </Router>
        </>
    )
}

export default App
