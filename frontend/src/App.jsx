import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AuthPage from './pages/driverAuthPage/AuthPage';
import DriverHome from './pages/driverHomePage/DriverHome';
import './App.css'
import { ToastContainer } from 'react-toastify';


function App() {

    return (
        <>
            <Router>
                <Routes>
                    <Route path="/" element={<AuthPage />} />
                    <Route path="/home" element={<DriverHome />} />
                </Routes>
                <ToastContainer className="toast-container"/>
            </Router>
        </>
    )
}

export default App
