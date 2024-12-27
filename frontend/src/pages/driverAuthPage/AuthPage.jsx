import React, { useState } from "react";
import LoginForm from "../../components/auth/LoginForm";
import SignupForm from "../../components/auth/SignupForm";
import {
  handleDriverLogin,
  handleDriverSignup,
  handleAdminLogin,
  handleManagerLogin,
} from "../../services/authService";
import "./AuthPage.css";

function AuthPage() {
  const [isLogin, setIsLogin] = useState(true);
  const [userType, setUserType] = useState("driver"); // 'driver', 'manager', or 'admin'
  const handleUserTypeChange = (type) => {
    setUserType(type);
  };
  const handleLogin = async(credentials) => {
    let response;
    switch (userType) {
      case "driver":
        response = await handleDriverLogin(credentials);
        break; 
      case "manager":
        response =  await handleManagerLogin(credentials);
        break;
      case "admin":
        response =  await handleAdminLogin(credentials);
        break; 
      default:
        console.error("Invalid user type");
    }
    return response;
  };

  const handleSignup = async (userData) => {
    const response = await handleDriverSignup(userData);
    return response;
    };
  

  return (
    <div className="auth-page">
      <div className="auth-container">
        {isLogin ? (
          <div className="auth-user-type">
            <button
              className={`type-button ${userType === "driver" ? "active" : ""}`}
              onClick={() => handleUserTypeChange("driver")}
            >
              Driver
            </button>
            <button
              className={`type-button ${
                userType === "manager" ? "active" : ""
              }`}
              onClick={() => handleUserTypeChange("manager")}
            >
              Manager
            </button>
            <button
              className={`type-button ${userType === "admin" ? "active" : ""}`}
              onClick={() => handleUserTypeChange("admin")}
            >
              Admin
            </button>
          </div>
            
        ) : null}
        {isLogin ? (
          <LoginForm onLogin={(credentials) => handleLogin(credentials)} />
        ) : (
          <SignupForm onSignup={(userData) => handleSignup(userData)}/>
        )}
        <div className="auth-switch">
          <p>
            {isLogin ? "Don't have an account? " : "Already have an account? "}
            <button
              className="switch-button"
              onClick={() => setIsLogin(!isLogin)}
            >
              {isLogin ? "Sign Up" : "Log In"}
            </button>
          </p>
        </div>
      </div>
    </div>
  );
}

export default AuthPage;
