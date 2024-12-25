import React, { useState } from 'react';
import './AuthForms.css';

function LoginForm({ onLogin }) {
    const [formData, setFormData] = useState({
        phoneNumber: '',
        password: '',
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        onLogin(formData);
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    return (
        <form className="auth-form" onSubmit={handleSubmit}>
            <h2>Driver Login</h2>

            <div className="form-group">
                <label htmlFor="phoneNumber">Phone Number</label>
                <input
                    type="tel"
                    id="phoneNumber"
                    name="phoneNumber"
                    value={formData.phoneNumber}
                    onChange={handleChange}
                    pattern="[0-9]{10}"
                    placeholder="1234567890"
                    required
                />
            </div>

            <div className="form-group">
                <label htmlFor="password">Password</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />
            </div>

            <button type="submit" className="auth-button">Log In</button>
        </form>
    );
}

export default LoginForm;