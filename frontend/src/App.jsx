import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AuthPage from './pages/driverAuthPage/AuthPage';
import DriverHome from './pages/driverHomePage/DriverHome';
import './App.css'


function App() {

    return (
        <>
            <Router>
                <Routes>
                    <Route path="/" element={<AuthPage />} />
                    <Route path="/home" element={<DriverHome />} />
                </Routes>
            </Router>



        </>
    )
}

export default App
