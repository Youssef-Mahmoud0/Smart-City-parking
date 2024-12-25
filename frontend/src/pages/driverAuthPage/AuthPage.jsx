import React, { useState } from 'react';
import LoginForm from '../../components/auth/LoginForm';
import SignupForm from '../../components/auth/SignupForm';
import './AuthPage.css';

function AuthPage() {
    const [isLogin, setIsLogin] = useState(true);

    const handleLogin = (data) => {
        console.log('Login data:', data);
        // Implement login logic here
    };

    const handleSignup = (data) => {
        console.log('Signup data:', data);
        // Implement signup logic here
    };

    return (
        <div className="auth-page">
            <div className="auth-container">
                {isLogin ? (
                    <LoginForm onLogin={handleLogin} />
                ) : (
                    <SignupForm onSignup={handleSignup} />
                )}

                <div className="auth-switch">
                    <p>
                        {isLogin ? "Don't have an account? " : "Already have an account? "}
                        <button
                            className="switch-button"
                            onClick={() => setIsLogin(!isLogin)}
                        >
                            {isLogin ? 'Sign Up' : 'Log In'}
                        </button>
                    </p>
                </div>
            </div>
        </div>
    );
}

export default AuthPage;