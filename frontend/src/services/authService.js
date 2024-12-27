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
        if(response.ok){
          console.log("Response Data:", responseData);
          return { success: true, message: responseData.message };
        }
        else{
          console.error(responseData.message);
          return { success: false, message: responseData.message };
        }
    } catch (error) {
        console.log("Error occurred during signup:", error);
        return { success: false, message: "Signup failed. Please try again." }; // Explicitly return an error response
    }
};

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

    const responseData = await response.json(); // Parse the response

    if (response.ok) {
      console.log("Response Data:", responseData.id);
      document.cookie = `id=${responseData.id};`;
      return { success: true, type: "driver"};
    } else {
      console.error(responseData.message);
      return { success: false, message: responseData.message || "Login failed." };
    }
  } catch (error) {
    console.error("Error occurred during login:", error);
    return { success: false, message: "An error occurred during login. Please try again." };
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
    if(response.ok){
      console.log("Response Data:", responseData);
      return { success: true, type: "manager" };
    }
    else{
      console.error(responseData.message);
      return { success: false, message: responseData.message };
    }
  }
  catch (error) {
    console.log(error);
    return { success: false, message: "Login failed. Please try again." }; // Explicitly return an error response
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
    if(response.ok){
      console.log("Response Data:", responseData);
      return { success: true, type: "admin", message: responseData.message };
    }
    else{
      console.error(responseData.message);
      return { success: false, message: responseData.message };
    }
  }
  catch (error) {
    console.log(error);
    return { success: false, message: "Login failed. Please try again." }; // Explicitly return an error response
  }
}
