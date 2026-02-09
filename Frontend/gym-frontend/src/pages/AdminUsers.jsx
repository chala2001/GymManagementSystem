import { useEffect, useState } from 'react';
import { assignUserToTrainer, deleteUser, getAllTrainers, getAllUsers } from '../services/api';
import '../styles/AdminUsers.css';

function AdminUsers() {
  const [users, setUsers] = useState([]);
  const [trainers, setTrainers] = useState([]);
  const [assignments, setAssignments] = useState({});
  const [error, setError] = useState('');

  useEffect(() => {
    fetchUsers();
    fetchTrainers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await getAllUsers();
      setUsers(response.data);
    } catch (err) {
      setError('Failed to load members.');
    }
  };

  const fetchTrainers = async () => {
    try {
      const response = await getAllTrainers();
      setTrainers(response.data);
    } catch (err) {
      setError('Failed to load trainers.');
    }
  };

  const handleAssign = async (userId) => {
    const trainerId = assignments[userId];
    if (!trainerId) {
      setError('Select a trainer before assigning.');
      return;
    }

    try {
      await assignUserToTrainer(userId, trainerId);
      setError('');
      fetchUsers();
    } catch (err) {
      setError('Assignment failed.');
    }
  };

  const handleDelete = async (userId) => {
    if (!window.confirm('Delete this member?')) return;
    try {
      await deleteUser(userId);
      fetchUsers();
    } catch (err) {
      setError('Delete failed.');
    }
  };

  return (
    <div className="page admin-users">
      <h1 className="page-title">Member management</h1>
      <p className="page-subtitle">
        Review member profiles, assigned trainers, and manage access.
      </p>

      {error && <p className="error">{error}</p>}

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Username</th>
            <th>Email</th>
            <th>Trainer</th>
            <th>Assign Trainer</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.name}</td>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>{user.trainer ? user.trainer.name : 'Unassigned'}</td>
              <td className="assign-cell">
                <select
                  className="input"
                  value={assignments[user.id] ?? ''}
                  onChange={(event) =>
                    setAssignments({
                      ...assignments,
                      [user.id]: event.target.value,
                    })
                  }
                >
                  <option value="">Select trainer</option>
                  {trainers.map((trainer) => (
                    <option key={trainer.id} value={trainer.id}>
                      {trainer.name}
                    </option>
                  ))}
                </select>
                <button
                  className="button secondary"
                  type="button"
                  onClick={() => handleAssign(user.id)}
                >
                  Assign
                </button>
              </td>
              <td>
                <button
                  className="button ghost"
                  onClick={() => handleDelete(user.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AdminUsers;
