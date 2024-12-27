import React from 'react';

function CarIcon({carClass}) {
    return (
        <img 
            src={`./${carClass}.png`} 
            className={carClass}
            alt=""
            width="40"
        />
    );
}

export default CarIcon;