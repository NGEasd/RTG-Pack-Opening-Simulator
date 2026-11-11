import { useContext, useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import { validatePassword, validateUsername } from "../../utils/validation";
import { loginUser, registerUser } from "../../services/userapi";
import { AuthContext } from "../../global context/AuthContext";

function AuthenticationContainer() {
  // user global state: context
  const userContext = useContext(AuthContext);
  const navigate = useNavigate();

  // actual state (false=reg, true=login)
  const [isLoginMode, setIsLoginMode] = useState<boolean>(false);

  // states for input manipulation
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [confPassword, setConfPassword] = useState<string>('');

  // error & succes
  const [message, setMessage] = useState<{ type: 'success' | 'error', text: string } | null>(null);

  // reset input states
  function resetInputs() {
    setUsername('');
    setPassword('');
    setConfPassword('');
  }

  // handle submit and validation
  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();

    const usernameValidation = validateUsername(username);
    if (!usernameValidation.value) {
      setMessage({ type: 'error', text: usernameValidation.message });
      resetInputs();
      return;
    }

    const passwordValidation = validatePassword(password);
    if (!passwordValidation.value) {
      setMessage({ type: 'error', text: passwordValidation.message });
      resetInputs();
      return;
    }

    // registration
    if (!isLoginMode) { 
      if (confPassword !== password) {
        setMessage({type:'error', text:'Passwords does`t match! Try again!'},)
        resetInputs();
        return;
      }
      const verificator = await registerUser({ username, password });
      if (verificator.value) {
        setMessage({ type: 'success', text: verificator.message });
        setIsLoginMode(true);
      } else {
        setMessage({ type: 'error', text: verificator.message });
      }
      resetInputs();
      return;
    }

    // login
    const verificator = await loginUser({ username, password });
    if (verificator.value) {
      const loggedUser = verificator.user;
      console.log(loggedUser);
      if (loggedUser) {

        // set token
        localStorage.setItem('JWT', loggedUser.jwtToken)

        // set user
        if (userContext !== null ) {
          const user = loggedUser.userRespDTO;
          userContext.login(user);
          console.log(user);
          setMessage({ type: 'success', text: verificator.message });
          resetInputs();
          setTimeout(() => {
            navigate('/home');
          }, 1500);
        }
        
      } else {
        setMessage({ type: 'error', text: 'Something went wrong! Try again!' });
      }
    } else {
      setMessage({ type: 'error', text: verificator.message });
      resetInputs();
    }
  }

  useEffect(() => {
    if (message) {
      const timer = setTimeout(() => {
        setMessage(null);
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [message]);


  return (
    <div className='auth-container'>
      <div className='auth-nav'>
        <button className={!isLoginMode ? 'active' : ''} onClick={() => { setIsLoginMode(false); resetInputs(); setMessage(null); }}>Registration</button>
        <button className={isLoginMode ? 'active' : ''} onClick={() => { setIsLoginMode(true); resetInputs(); setMessage(null); }}>Login</button>
      </div>
      <div className='auth-form'>
        {!isLoginMode &&
          <>
            <h2>Register an account!</h2>
            <form id='register-form' onSubmit={handleSubmit}>
              <label htmlFor='reg-username'>Username:</label>
              <input type="text" id='reg-username' value={username} onChange={(e) => setUsername(e.target.value)} required />
              <label htmlFor='reg-password'>Password:</label>
              <input type="password" id='reg-password' value={password} onChange={(e) => setPassword(e.target.value)} required />
              <label htmlFor='reg-conf-password'>Confirm password:</label>
              <input type="password" id='reg-conf-password' value={confPassword} onChange={(e) => setConfPassword(e.target.value)} required />
              <button type="submit" className="submit-button">Register</button>
            </form>
          </>
        }

        {isLoginMode &&
          <>
            <h2>Login into account!</h2>
            <form id='login-form' onSubmit={handleSubmit}>
              <label htmlFor='login-username'>Username:</label>
              <input type="text" id='login-username' value={username} onChange={(e) => setUsername(e.target.value)} required />
              <label htmlFor='login-password'>Password:</label>
              <input type="password" id='login-password' value={password} onChange={(e) => setPassword(e.target.value)} required />
              <button type="submit" className="submit-button">Login</button>
            </form>
          </>
        }

        {message &&
          <div className={`message-box ${message.type}`}> 
            <p>{message.text}</p>
          </div>
        }

      </div>
    </div>
  );
}

export default AuthenticationContainer;