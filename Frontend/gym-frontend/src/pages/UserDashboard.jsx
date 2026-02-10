import { useEffect, useState } from 'react';
import { getUser, updateUser } from '../services/api';
import '../styles/UserDashboard.css';

function UserDashboard() {
  const [user, setUser] = useState(null);
  const [formData, setFormData] = useState({
    height: '',
    weight: '',
    fatPercentage: '',
    tdee: '',
    caloriesPerDay: '',
  });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const userId = localStorage.getItem('userId');

  useEffect(() => {
    if (userId) {
      fetchUser();
    }
  }, [userId]);

  const fetchUser = async () => {
    try {
      const response = await getUser(userId);
      setUser(response.data);
      setFormData({
        height: response.data.height ?? '',
        weight: response.data.weight ?? '',
        fatPercentage: response.data.fatPercentage ?? '',
        tdee: response.data.tdee ?? '',
        caloriesPerDay: response.data.caloriesPerDay ?? '',
      });
    } catch (err) {
      setError('Unable to load profile. Confirm your ID and login.');
    }
  };

  const handleChange = (event) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setMessage('');
    setError('');

    try {
      const toNumberOrNull = (value) => (value === '' ? null : Number(value));
      await updateUser(userId, {
        height: toNumberOrNull(formData.height),
        weight: toNumberOrNull(formData.weight),
        fatPercentage: toNumberOrNull(formData.fatPercentage),
        tdee: toNumberOrNull(formData.tdee),
        caloriesPerDay: toNumberOrNull(formData.caloriesPerDay),
      });
      setMessage('Profile updated successfully.');
      fetchUser();
    } catch (err) {
      setError('Update failed. Please try again.');
    }
  };

  return (
    <div className="page user-dashboard">
      <h1 className="page-title">Member dashboard</h1>
      <p className="page-subtitle">
        Review your stats, update body metrics, and stay aligned with your plan.
      </p>

      {!userId && (
        <div className="card warning">
          <h3>No profile ID saved</h3>
          <p>
            Log in with your member ID to load your profile details and update
            your stats.
          </p>
        </div>
      )}

      {user && (
        <div className="section grid grid-2">
          <div className="card">
            <h3>Profile snapshot</h3>
            <div className="snapshot">
              <div>
                <span>Name</span>
                <strong>{user.name}</strong>
              </div>
              <div>
                <span>Username</span>
                <strong>{user.username}</strong>
              </div>
              <div>
                <span>Email</span>
                <strong>{user.email}</strong>
              </div>
              <div>
                <span>Date of birth</span>
                <strong>{user.dateOfBirth}</strong>
              </div>
              <div>
                <span>Trainer</span>
                <strong>{user.trainer ? user.trainer.name : 'Not assigned'}</strong>
              </div>
            </div>
            <div className="routine">
              <span>Current routine</span>
              <p>{user.gymRoutine || 'No routine assigned yet.'}</p>
            </div>
          </div>

          <form className="card" onSubmit={handleSubmit}>
            <h3>Update body metrics</h3>
            <div className="form-grid">
              <input
                className="input"
                name="height"
                type="number"
                step="0.1"
                placeholder="Height (cm)"
                value={formData.height}
                onChange={handleChange}
              />
              <input
                className="input"
                name="weight"
                type="number"
                step="0.1"
                placeholder="Weight (kg)"
                value={formData.weight}
                onChange={handleChange}
              />
              <input
                className="input"
                name="fatPercentage"
                type="number"
                step="0.1"
                placeholder="Body fat %"
                value={formData.fatPercentage}
                onChange={handleChange}
              />
              <input
                className="input"
                name="tdee"
                type="number"
                step="0.1"
                placeholder="TDEE"
                value={formData.tdee}
                onChange={handleChange}
              />
              <input
                className="input"
                name="caloriesPerDay"
                type="number"
                step="0.1"
                placeholder="Calories per day"
                value={formData.caloriesPerDay}
                onChange={handleChange}
              />
            </div>
            {message && <p className="result">{message}</p>}
            {error && <p className="error">{error}</p>}
            <button className="button" type="submit">Save updates</button>
          </form>
        </div>
      )}
    </div>
  );
}

export default UserDashboard;
