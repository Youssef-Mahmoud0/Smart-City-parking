import React from "react";
import "./Confirmation.css";

const Confirmation = ({ isOpen, onClose, onConfirm, message }) => {
    if (!isOpen) return null;

    return (
        <div className="modal-overlay">
            <div className="modal-container">
                <p>{message}</p>
                <div className="modal-buttons">
                    <button className="modal-confirm-button" onClick={onConfirm}>
                        Confirm
                    </button>
                    <button className="modal-cancel-button" onClick={onClose}>
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Confirmation;
