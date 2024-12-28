import React, { useState } from "react";
import "./Payment.css";

const PaymentOptions = () => {
    const [paymentMethod, setPaymentMethod] = useState("");
    const [visaCardNumber, setVisaCardNumber] = useState("");
    const [isValidVisa, setIsValidVisa] = useState(true);
    const [submitted, setSubmitted] = useState(false);

    const handleVisaValidation = (number) => {
        const visaRegex = /^4[0-9]{12}(?:[0-9]{3})?$/; // Visa card validation regex
        return visaRegex.test(number);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (paymentMethod === "cash") {
            setSubmitted(true);
            alert("Payment accepted: Cash");
        } else if (paymentMethod === "visa") {
            if (handleVisaValidation(visaCardNumber)) {
                setIsValidVisa(true);
                setSubmitted(true);
                alert("Payment accepted: Visa");
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
                    <div className="payment-option">
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
                    </div>
                    <div className="payment-option">
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
                </div>

                {paymentMethod === "visa" && (
                    <div className="visa-input">
                        <label>
                            Visa Card Number:
                            <input
                                type="text"
                                placeholder="Enter your Visa card number"
                                value={visaCardNumber}
                                onChange={(e) => setVisaCardNumber(e.target.value)}
                                className={!isValidVisa ? "error" : ""}
                            />
                        </label>
                        {!isValidVisa && <p className="error-text">Invalid Visa card number</p>}
                    </div>
                )}

                <button type="submit" className="submit-button">
                    Submit
                </button>
            </form>

            {submitted && <p className="success-text">Payment Successful!</p>}
        </div>
    );
};

export default PaymentOptions;
