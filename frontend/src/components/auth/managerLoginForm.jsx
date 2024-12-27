import React, { useState } from 'react';
import './AuthForms.css';

function LoginForm({ onLogin }) {
    const [formData, setFormData] = useState({
        email: '',
        password: '',
    });

        const [errors, setErrors] = useState({});
        
        const handleSubmit =async (e) => {
        e.preventDefault();
        validateData();
        if (Object.keys(errors).length > 0) return;
        const res =  onLogin(formData);
        console.log("Response from onLogin:", res);
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };
    const validateData = () => {
        const newErrors = {};
        if (!formData.email) newErrors.email = 'Email is required.';
        else if (!/^\S+@gmail\.com$/.test(formData.email)) {
            newErrors.email =
                "Email must be a valid Gmail address (e.g., example@gmail.com).";
        }
        if (!formData.password) 
            newErrors.password = "Password is required.";
        setErrors(newErrors);
    };

    return (
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
            <button type="submit" className="auth-button">Log In</button>
        </form>
    );
}

export default LoginForm;