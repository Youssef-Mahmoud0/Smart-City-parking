  export const handleDriverLogin = async (data) => {
    try {
      const response = await fetch('http://localhost:8080/auth/login/driver', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      }); 
      const responseData = await response.json();
      console.log(responseData);
      return responseData;
    }
    catch (error) {
      console.log(error);
    }
  };
  export const handleDriverSignup = async (data) => {
    try {
        const response = await fetch('http://localhost:8080/auth/signup/driver', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });
        const responseData = await response.json();
        if(responseData.ok){
          console.log("Response Data:", responseData);
          return { success: true, responseData };
        }
        else{
          console.error(responseData.message);
          return { success: false, message: data.message };
        }
    } catch (error) {
        console.log("Error occurred during signup:", error);
        return { success: false, message: "Signup failed. Please try again." }; // Explicitly return an error response
    }
};


export const handleManagerLogin = async (data) => {
  try {
    const response = await fetch('http://localhost:8080/auth/login/manager', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });
    const responseData = await response.json();
    console.log(responseData);
    return responseData;
  }
  catch (error) {
    console.log(error);
  }
}
export const handleAdminLogin = async (data) => {
  try {
    const response = await fetch('http://localhost:8080/auth/login/admin', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });
    const responseData = await response.json();
    console.log(responseData);
    return responseData;
  }
  catch (error) {
    console.log(error);
  }
}
