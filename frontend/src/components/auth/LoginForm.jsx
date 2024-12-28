import React, { useState } from "react";
import "./AuthForms.css";

function LoginForm({ onLogin }) {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });
  const [successResponse, setSuccessResponse] = useState("");
  const [failureResponse, setFailureResponse] = useState("");
  const [errors, setErrors] = useState({});
  const handleSubmit = async (e) => {
    setFailureResponse("");
    setSuccessResponse("");
    e.preventDefault();
    validateData();
    if (Object.keys(errors).length > 0) return;

    try {
      const response = await onLogin(formData); // Call the parent function
      if (response.success) {
        console.log("Login successful:", response.type);
        setSuccessResponse("Login successful.");
        if (response.type === "driver") {
          window.location.href = "/home";
        } else if (response.type === "manager") {
          window.location.href = "/manager/home";
        } else if (response.type === "admin") {
          window.location.href = "/admin/home";
        }
      } else {
        setFailureResponse(response.message);
      }
    } catch (error) {
      console.error("Error during login:", error);
      setErrors({ general: "An unexpected error occurred." });
    }
  };

  const handleChange = (e) => {
    setFailureResponse("");
    setSuccessResponse("");
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };
  const validateData = () => {
    const newErrors = {};
    if (!formData.email) newErrors.email = "Email is required.";
    else if (!/^\S+@gmail\.com$/.test(formData.email)) {
      newErrors.email =
        "Email must be a valid Gmail address (e.g., example@gmail.com).";
    }
    if (!formData.password) newErrors.password = "Password is required.";
    setErrors(newErrors);
  };

  return (
    <>
      <form className="auth-form" onSubmit={handleSubmit}>
        <h2>Log In</h2>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="text"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="example@gmail.com"
          />
        </div>
        {errors.email && <p className="error">{errors.email}</p>}
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
        </div>
        {errors.password && <p className="error">{errors.password}</p>}
        <button type="submit" className="auth-button">
          Log In
        </button>
        {successResponse && (
          <p className="success-message">{successResponse}</p>
        )}
        {failureResponse && <p className="error-message">{failureResponse}</p>}
      </form>
    </>
  );
}

export default LoginForm;
