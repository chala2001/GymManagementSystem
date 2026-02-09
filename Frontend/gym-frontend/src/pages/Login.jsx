import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Login.css';
import { checkAdmin, checkTrainer, checkUser } from '../services/api';

function Login() {
  const [role, setRole] = useState('ADMIN');
  const [accountId, setAccountId] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      if (role === 'ADMIN') {
        await checkAdmin(username, password);
        const token = btoa(`${username}:${password}`);
        localStorage.setItem('auth', token);
        localStorage.setItem('role', role);
        navigate('/admin');
        return;
      }

      if (!accountId) {
        setError('Please enter your account ID.');
        return;
      }

      if (role === 'TRAINER') {
        await checkTrainer(username, password, accountId);
        const token = btoa(`${username}:${password}`);
        localStorage.setItem('auth', token);
        localStorage.setItem('role', role);
        localStorage.setItem('trainerId', accountId);
        navigate('/trainer');
        return;
      }

      await checkUser(username, password, accountId);
      const token = btoa(`${username}:${password}`);
      localStorage.setItem('auth', token);
      localStorage.setItem('role', role);
      localStorage.setItem('userId', accountId);
      navigate('/user');
    } catch (err) {
      setError('Login failed. Check your credentials and role.');
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Access the studio</h2>

        {error && <p className="error">{error}</p>}

        <label>
          Role
          <select value={role} onChange={(e) => setRole(e.target.value)}>
            <option value="ADMIN">Admin</option>
            <option value="TRAINER">Trainer</option>
            <option value="USER">Member</option>
          </select>
        </label>

        {(role === 'TRAINER' || role === 'USER') && (
          <label>
            Account ID
            <input
              type="number"
              placeholder="Enter your profile ID"
              value={accountId}
              onChange={(e) => setAccountId(e.target.value)}
              required
            />
          </label>
        )}

        <label>
          Username
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </label>

        <label>
          Password
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </label>

        <button className="button" type="submit">Login</button>
      </form>
      <div className="login-aside">
        <div className="card">
          <span className="pill">Tip</span>
          <h3>Need your ID?</h3>
          <p className="muted">
            After registration, your profile ID is returned in the response. Use
            that number when logging in as a trainer or member.
          </p>
        </div>
      </div>
    </div>
  );
}

export default Login;
