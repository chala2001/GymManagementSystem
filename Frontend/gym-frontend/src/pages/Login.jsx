import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// import { loginRequest } from '../services/api';
import '../styles/Login.css';
import { checkAdmin, checkTrainer, checkUser } from '../services/api';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const navigate = useNavigate();


const handleSubmit = async (e) => {
  e.preventDefault();
  setError('');

  try {
    await checkAdmin(username, password);
    const token = btoa(`${username}:${password}`);
    localStorage.setItem('auth', token);

    localStorage.setItem('role', 'ADMIN');
    navigate('/admin');
    return;
  } catch {}

  try {
    await checkTrainer(username, password);
    const token = btoa(`${username}:${password}`);
    localStorage.setItem('auth', token);

    localStorage.setItem('role', 'TRAINER');
    navigate('/trainer');
    return;
  } catch {}

  try {
    await checkUser(username, password);
    const token = btoa(`${username}:${password}`);
    localStorage.setItem('auth', token);

    localStorage.setItem('role', 'USER');
    navigate('/user');
    return;
  } catch {}

  setError('Invalid username or password');
};


  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Login</h2>

        {error && <p style={{ color: 'red' }}>{error}</p>}

        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <button type="submit">Login</button>
      </form>
    </div>
  );
}

export default Login;
