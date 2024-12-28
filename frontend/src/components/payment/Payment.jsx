import React, { useState } from "react";
import "./Payment.css";

const PaymentOptions = ({ onPaymentSuccess, onCancel }) => {
    const [paymentMethod, setPaymentMethod] = useState("");
    const [visaCardNumber, setVisaCardNumber] = useState("");
    const [isValidVisa, setIsValidVisa] = useState(true);

    const handleVisaValidation = (number) => {
        const visaRegex = /^4[0-9]{12}(?:[0-9]{3})?$/;
        return visaRegex.test(number);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (paymentMethod === "cash") {
            onPaymentSuccess();
        } else if (paymentMethod === "visa") {
            if (handleVisaValidation(visaCardNumber)) {
                setIsValidVisa(true);
                onPaymentSuccess();
            } else {
                setIsValidVisa(false);
            }
        }
    };

    return (
        <div className="payment-container">
            <h2>Choose Payment Method</h2>
            <form onSubmit={handleSubmit}>
                <div className="payment-options">
                    <label>
                        <input
                            type="radio"
                            name="payment"
                            value="cash"
                            checked={paymentMethod === "cash"}
                            onChange={(e) => setPaymentMethod(e.target.value)}
                        />
                        Cash
                    </label>
                    <label>
                        <input
                            type="radio"
                            name="payment"
                            value="visa"
                            checked={paymentMethod === "visa"}
                            onChange={(e) => setPaymentMethod(e.target.value)}
                        />
                        Visa
                    </label>
                </div>

                {paymentMethod === "visa" && (
                    <div className="visa-input">
                        <input
                            type="text"
                            placeholder="Enter Visa card number"
                            value={visaCardNumber}
                            onChange={(e) => setVisaCardNumber(e.target.value)}
                            className={!isValidVisa ? "error" : ""}
                        />
                        {!isValidVisa && <p className="error-text">Invalid Visa card number</p>}
                    </div>
                )}

                <div className="payment-actions">
                    <button type="button" onClick={onCancel}>
                        Cancel
                    </button>
                    <button type="submit">Submit</button>
                </div>
            </form>
        </div>
    );
};

export default PaymentOptions;
