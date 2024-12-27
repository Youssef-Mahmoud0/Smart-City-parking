import React, { useState } from "react";
import "./AuthForms.css";

function SignupForm({ onSignup }) {
    const [formData, setFormData] = useState({
        name: "",
        phoneNumber: "",
        licensePlateNumber: "",
        paymentMethod: "",
        password: "",
        email: "",
    });

    const [successResponse, setSuccessResponse] = useState("");
    const [failureResponse, setFailureResponse] = useState("");
    const [errors, setErrors] = useState({});

    const validateData = () => {
        const newErrors = {};
        if (!formData.name) newErrors.name = "Name is required.";
        else if (formData.name.length < 3)
            newErrors.name = "Name must be at least 3 characters long.";
        else if (formData.name.length > 50)
            newErrors.name = "Name must be at most 50 characters long.";
        else if (!/^[a-zA-Z\s]*$/.test(formData.name))
            newErrors.name = "Name must contain only letters and spaces.";
        if (!formData.phoneNumber)
            newErrors.phoneNumber = "Phone number is required.";
        else if (!/^0[0-9]{10}$/.test(formData.phoneNumber)) {
            newErrors.phoneNumber =
                "Phone number must be 11 digits long and start with 0.";
        }
        if (!formData.email) newErrors.email = "Email is required.";
        else if (!/^\S+@gmail\.com$/.test(formData.email)) {
            newErrors.email =
                "Email must be a valid Gmail address (e.g., example@gmail.com).";
        }
        const licensePlateRegex =
            /^([\u0600-\u06FF])\s([\u0600-\u06FF])\s([\u0600-\u06FF])\s?([\u0660-\u0669]{4})$/;

        console.log(formData.licensePlateNumber);
        if (!formData.licensePlateNumber) {
            newErrors.licensePlateNumber = "License plate is required.";
        } else if (!licensePlateRegex.test(formData.licensePlateNumber)) {
            newErrors.licensePlateNumber =
                "License plate must follow the format: Arabic characters separated by spaces and Arabic digits separated by spaces (e.g., أ ب ج ١٢٣٤).";
        }
        if (!formData.paymentMethod)
            newErrors.paymentMethod = "Payment method is required.";
        if (!formData.password) {
            newErrors.password = "Password is required.";
        } else if (formData.password.length < 8) {
            newErrors.password = "Password must be at least 8 characters long.";
        } else if (!/[A-Z]/.test(formData.password)) {
            newErrors.password =
                "Password must contain at least one uppercase letter.";
        } else if (!/[a-z]/.test(formData.password)) {
            newErrors.password =
                "Password must contain at least one lowercase letter.";
        } else if (!/[0-9]/.test(formData.password)) {
            newErrors.password = "Password must contain at least one number.";
        } else if (!/[!@#$%^&*(),.?":{}|<>]/.test(formData.password)) {
            newErrors.password =
                "Password must contain at least one special character.";
        } else if (formData.password.length > 50) {
            newErrors.password = "Password must be at most 50 characters";
        }
        setErrors(newErrors);
    };

    const handleSubmit = async (e) => {
        setFailureResponse("");
        setSuccessResponse("");
        e.preventDefault();
        validateData();
        if (Object.keys(errors).length > 0) return;
        const response = await onSignup(formData);
        if (response.success) {
            setSuccessResponse(response.message);
            setFailureResponse("");
        } else {
            setFailureResponse(response.message);
            setSuccessResponse("");
        }
    };

    const handleChange = (e) => {
        setFailureResponse("");
        setSuccessResponse("");
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
        errors[name] && setErrors({ ...errors, [name]: "" });
    };

    return (
        <>
            <form className="auth-form" onSubmit={handleSubmit}>
                <h2>Driver Sign Up</h2>

                <div className="form-group">
                    <label htmlFor="name">Full Name</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                    />
                </div>
                {errors.name && <p className="error">{errors.name}</p>}

                <div className="form-group">
                    <label htmlFor="phoneNumber">Phone Number</label>
                    <input
                        type="text"
                        id="phoneNumber"
                        name="phoneNumber"
                        value={formData.phoneNumber}
                        onChange={handleChange}
                        placeholder="01234567890"
                    />
                </div>
                {errors.phoneNumber && <p className="error">{errors.phoneNumber}</p>}

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
                    <label htmlFor="licensePlateNumber">License Plate Number</label>
                    <input
                        type="text"
                        id="licensePlateNumber"
                        name="licensePlateNumber"
                        value={formData.licensePlateNumber}
                        onChange={handleChange}
                        dir="rtl"
                        placeholder="أ ب ج ١٢٣٤"
                    />
                </div>
                {errors.licensePlateNumber && (
                    <p className="error">{errors.licensePlateNumber}</p>
                )}
                <div className="form-group">
                    <label htmlFor="paymentMethod">Payment Method</label>
                    <select
                        id="paymentMethod"
                        name="paymentMethod"
                        value={formData.paymentMethod}
                        onChange={handleChange}
                    >
                        <option value="">Select payment method</option>
                        <option value="VISA">Visa</option>
                        <option value="CASH">Cash</option>
                    </select>
                </div>
                {errors.paymentMethod && (
                    <p className="error">{errors.paymentMethod}</p>
                )}
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
                    Sign Up
                </button>
                {successResponse && (
                    <p className="success-message">{successResponse}</p>
                )}
                {failureResponse && <p className="error-message">{failureResponse}</p>}
            </form>
        </>
    );
}

export default SignupForm;
