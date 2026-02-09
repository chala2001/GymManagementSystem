import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getTrainerUsers } from '../services/api';
import '../styles/TrainerDashboard.css';

function TrainerDashboard() {
  const [count, setCount] = useState(0);
  const [error, setError] = useState('');
  const trainerId = localStorage.getItem('trainerId');

  useEffect(() => {
    if (trainerId) {
      fetchUsers();
    }
  }, [trainerId]);

  const fetchUsers = async () => {
    try {
      const res = await getTrainerUsers(trainerId);
      setCount(res.data.length);
    } catch (err) {
      setError('Unable to load your members.');
    }
  };

  return (
    <div className="page trainer-dashboard">
      <h1 className="page-title">Trainer dashboard</h1>
      <p className="page-subtitle">
        Stay focused on your roster and keep routines up to date.
      </p>

      {!trainerId && (
        <div className="card warning">
          <h3>No trainer ID saved</h3>
          <p>Please log in with your trainer ID to load your roster.</p>
        </div>
      )}

      {error && <p className="error">{error}</p>}

      <div className="section grid grid-2">
        <div className="card stat">
          <span>Assigned members</span>
          <strong className="stat-value">{count}</strong>
        </div>
        <div className="card action-card">
          <h3>Update routines</h3>
          <p>Jump into your member list and update their weekly plan.</p>
          <Link className="button secondary" to="/trainer/users">View members</Link>
        </div>
      </div>
    </div>
  );
}

export default TrainerDashboard;
