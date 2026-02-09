import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getAllTrainers, getAllUsers } from '../services/api';
import '../styles/AdminDashboard.css';

function AdminDashboard() {
  const [trainerCount, setTrainerCount] = useState(0);
  const [userCount, setUserCount] = useState(0);

  useEffect(() => {
    fetchStats();
  }, []);

  const fetchStats = async () => {
    try {
      const trainersRes = await getAllTrainers();
      setTrainerCount(trainersRes.data.length);

      const usersRes = await getAllUsers();
      setUserCount(usersRes.data.length);
    } catch (error) {
      console.error('Error fetching admin stats', error);
    }
  };

  return (
    <div className="page admin-dashboard">
      <h1 className="page-title">Admin dashboard</h1>
      <p className="page-subtitle">
        See trainer coverage, member volume, and quick actions at a glance.
      </p>

      <div className="section grid grid-2">
        <div className="card stat">
          <span>Total trainers</span>
          <strong className="stat-value">{trainerCount}</strong>
        </div>
        <div className="card stat">
          <span>Total members</span>
          <strong className="stat-value">{userCount}</strong>
        </div>
      </div>

      <div className="section admin-actions">
        <div className="card">
          <h3>Quick checklist</h3>
          <ul>
            <li>Onboard new trainers and confirm access</li>
            <li>Review member counts for staffing</li>
            <li>Audit routines in the trainer dashboard</li>
          </ul>
        </div>
        <div className="card accent">
          <h3>Next best action</h3>
          <p>
            Add new trainers before peak hours to keep member sessions flowing.
          </p>
          <Link className="button secondary" to="/admin/trainers">
            Manage trainers
          </Link>
        </div>
      </div>
    </div>
  );
}

export default AdminDashboard;
