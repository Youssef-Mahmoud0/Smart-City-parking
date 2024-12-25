import React, { useState } from 'react';
import './AuthForms.css';

function SignupForm({ onSignup }) {
    const [formData, setFormData] = useState({
        name: '',
        phoneNumber: '',
        licensePlate: '',
        paymentMethod: '',
        password: '',
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        onSignup(formData);
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    return (
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
                    required
                />
            </div>

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
                <label htmlFor="licensePlate">License Plate Number</label>
                <input
                    type="text"
                    id="licensePlate"
                    name="licensePlate"
                    value={formData.licensePlate}
                    onChange={handleChange}
                    required
                />
            </div>

            <div className="form-group">
                <label htmlFor="paymentMethod">Payment Method</label>
                <select
                    id="paymentMethod"
                    name="paymentMethod"
                    value={formData.paymentMethod}
                    onChange={handleChange}
                    required
                >
                    <option value="">Select payment method</option>
                    <option value="visa">Visa</option>
                    <option value="mastercard">Mastercard</option>
                    <option value="amex">American Express</option>
                </select>
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

            <button type="submit" className="auth-button">Sign Up</button>
        </form>
    );
}

export default SignupForm;