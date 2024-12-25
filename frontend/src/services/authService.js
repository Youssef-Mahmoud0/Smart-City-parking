  export const handleLogin = async (data) => {
    try {
      const response = await fetch('http://localhost:8080/auth/login', {
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
  };
  export const handleDriverSignup = async (data) => {
    console.log(data);
    try {
      const response = await fetch('http://localhost:8080/auth/signup/driver', {
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