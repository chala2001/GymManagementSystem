import { useState } from 'react';
import { registerUser } from '../services/api';
import '../styles/Register.css';

function Register() {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    name: '',
    email: '',
    dateOfBirth: '',
  });
  const [result, setResult] = useState('');
  const [error, setError] = useState('');

  const handleChange = (event) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError('');
    setResult('');

    try {
      const response = await registerUser(formData);
      setResult(`Registration complete. Your ID is ${response.data.id}.`);
      setFormData({
        username: '',
        password: '',
        name: '',
        email: '',
        dateOfBirth: '',
      });
    } catch (err) {
      setError('Registration failed. Please check your info.');
    }
  };

  return (
    <div className="page register-page">
      <div className="register-header">
        <h1 className="page-title">Join ForgeFit</h1>
        <p className="page-subtitle">
          Create a member profile in minutes. You will use your ID when logging
          in as a member.
        </p>
      </div>

      <form className="card register-form" onSubmit={handleSubmit}>
        <div className="form-grid">
          <input
            className="input"
            name="name"
            placeholder="Full name"
            value={formData.name}
            onChange={handleChange}
            required
          />
          <input
            className="input"
            name="username"
            placeholder="Username"
            value={formData.username}
            onChange={handleChange}
            required
          />
          <input
            className="input"
            type="email"
            name="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
            required
          />
          <input
            className="input"
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            required
          />
          <input
            className="input"
            type="date"
            name="dateOfBirth"
            value={formData.dateOfBirth}
            onChange={handleChange}
            required
          />
        </div>

        {error && <p className="error">{error}</p>}
        {result && <p className="result">{result}</p>}

        <button className="button" type="submit">Create profile</button>
      </form>
    </div>
  );
}

export default Register;
