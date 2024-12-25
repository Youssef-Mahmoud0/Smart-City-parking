import React from 'react';
import './PenaltiesList.css';

function PenaltiesList() {
    // Mock data - replace with actual data later
    const penalties = [
        { id: 1, date: '2024-03-15', description: 'Speeding', amount: 150 },
        { id: 2, date: '2024-03-10', description: 'Parking violation', amount: 75 },
    ];

    return (
        <div className="penalties-container">
            <h2>Penalties</h2>
            {penalties.length === 0 ? (
                <p>No penalties found</p>
            ) : (
                <div className="penalties-list">
                    {penalties.map(penalty => (
                        <div key={penalty.id} className="penalty-item">
                            <div className="penalty-date">{penalty.date}</div>
                            <div className="penalty-description">{penalty.description}</div>
                            <div className="penalty-amount">${penalty.amount}</div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default PenaltiesList;