import { useEffect, useState } from 'react';
import { getAllTrainers, deleteTrainer, createTrainer } from '../services/api';
import '../styles/AdminTrainers.css';

function AdminTrainers() {
  const [trainers, setTrainers] = useState([]);
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    name: '',
    email: '',
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
        email: '',
        experienceYears: '',
        dateOfBirth: '',
      });

      fetchTrainers();
    } catch (error) {
      console.error('Error creating trainer', error);
    }
  };

  return (
    <div className="page admin-trainers">
      <h1 className="page-title">Trainer management</h1>
      <p className="page-subtitle">
        Add new coaches, manage experience levels, and keep staffing aligned.
      </p>

      <form className="card trainer-form" onSubmit={handleCreate}>
        <h3>Add trainer</h3>
        <div className="form-grid">
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
            name="password"
            type="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            required
          />

          <input
            className="input"
            name="name"
            placeholder="Full Name"
            value={formData.name}
            onChange={handleChange}
            required
          />

          <input
            className="input"
            name="email"
            type="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
            required
          />

          <input
            className="input"
            name="experienceYears"
            type="number"
            placeholder="Experience (Years)"
            value={formData.experienceYears}
            onChange={handleChange}
            required
          />

          <input
            className="input"
            name="dateOfBirth"
            type="date"
            value={formData.dateOfBirth}
            onChange={handleChange}
            required
          />
        </div>

        <button className="button" type="submit">Add trainer</button>
      </form>

      <table className="table trainer-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Name</th>
            <th>Email</th>
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
              <td>{trainer.email}</td>
              <td>{trainer.experienceYears}</td>
              <td>
                <button
                  className="button ghost"
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
