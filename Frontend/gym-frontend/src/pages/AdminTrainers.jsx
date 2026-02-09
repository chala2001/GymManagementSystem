import { useEffect, useState } from 'react';
import { getAllTrainers, deleteTrainer, createTrainer } from '../services/api';
import '../styles/AdminTrainers.css';

function AdminTrainers() {
  const [trainers, setTrainers] = useState([]);
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    name: '',
    experienceYears: '',
    dateOfBirth: '',
  });

  useEffect(() => {
    fetchTrainers();
  }, []);

  const fetchTrainers = async () => {
    try {
      const response = await getAllTrainers();
      setTrainers(response.data);
    } catch (error) {
      console.error('Error fetching trainers', error);
    }
  };

  const handleDelete = async (trainerId) => {
    if (!window.confirm('Delete this trainer?')) return;
    try {
      await deleteTrainer(trainerId);
      fetchTrainers();
    } catch (error) {
      console.error('Error deleting trainer', error);
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleCreate = async (e) => {
    e.preventDefault();

    try {
      await createTrainer({
        ...formData,
        experienceYears: Number(formData.experienceYears),
      });

      setFormData({
        username: '',
        password: '',
        name: '',
        experienceYears: '',
        dateOfBirth: '',
      });

      fetchTrainers();
    } catch (error) {
      console.error('Error creating trainer', error);
    }
  };

  return (
    <div>
      <h1>Trainer Management</h1>

      {/* CREATE TRAINER FORM */}
      <form className="trainer-form" onSubmit={handleCreate}>
        <h3>Add Trainer</h3>

        <input
          name="username"
          placeholder="Username"
          value={formData.username}
          onChange={handleChange}
          required
        />

        <input
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
        />

        <input
          name="name"
          placeholder="Full Name"
          value={formData.name}
          onChange={handleChange}
          required
        />

        <input
          name="experienceYears"
          type="number"
          placeholder="Experience (Years)"
          value={formData.experienceYears}
          onChange={handleChange}
          required
        />

        <input
          name="dateOfBirth"
          type="date"
          value={formData.dateOfBirth}
          onChange={handleChange}
          required
        />

        <button type="submit">Add Trainer</button>
      </form>

      {/* TRAINER TABLE */}
      <table className="trainer-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Name</th>
            <th>Experience</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {trainers.map((trainer) => (
            <tr key={trainer.id}>
              <td>{trainer.id}</td>
              <td>{trainer.username}</td>
              <td>{trainer.name}</td>
              <td>{trainer.experienceYears}</td>
              <td>
                <button
                  className="delete-btn"
                  onClick={() => handleDelete(trainer.id)}
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

export default AdminTrainers;
