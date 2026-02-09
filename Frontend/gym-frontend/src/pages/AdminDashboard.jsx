import { useEffect, useState } from 'react';
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
    <div>
      <h1>Admin Dashboard</h1>

      <div className="dashboard-cards">
        <div className="dashboard-card">
          <h3>Total Trainers</h3>
          <p>{trainerCount}</p>
        </div>

        <div className="dashboard-card">
          <h3>Total Users</h3>
          <p>{userCount}</p>
        </div>
      </div>
    </div>
  );
}

export default AdminDashboard;
