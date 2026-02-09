import { useEffect, useState } from "react";

import { getTrainerUsers, updateUserRoutine } from "../services/api";
import "../styles/TrainerUsers.css";

function TrainerUsers() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");
  const [routineEdits, setRoutineEdits] = useState({});

  const trainerId = localStorage.getItem("trainerId");

  useEffect(() => {
    if (trainerId) {
      fetchUsers();
    }
  }, [trainerId]);

  const fetchUsers = async () => {
    try {
      const res = await getTrainerUsers(trainerId);
      setUsers(res.data);
    } catch (err) {
      setError("Failed to load assigned users");
    }
  };

  const saveRoutine = async (userId) => {
    try {
      await updateUserRoutine(trainerId, userId, routineEdits[userId] ?? "");
      alert("Gym routine updated successfully");
    } catch (err) {
      alert("Failed to update gym routine");
    }
  };

  return (
    <div className="page trainer-users">
      <h1 className="page-title">My members</h1>
      <p className="page-subtitle">
        Update routines and keep every member progressing week to week.
      </p>

      {!trainerId && (
        <div className="card warning">
          <h3>No trainer ID saved</h3>
          <p>Please log in with your trainer ID to load your roster.</p>
        </div>
      )}

      {error && <p className="error">{error}</p>}

      <table className="table trainer-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Username</th>
            <th>Gym Routine</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {users.map((u) => (
            <tr key={u.id}>
              <td>{u.id}</td>
              <td>{u.name}</td>
              <td>{u.username}</td>

              <td>
                <textarea
                  className="routine-input"
                  rows="4"
                  value={routineEdits[u.id] ?? u.gymRoutine ?? ""}
                  onChange={(e) =>
                    setRoutineEdits({
                      ...routineEdits,
                      [u.id]: e.target.value,
                    })
                  }
                />
              </td>

              <td>
                <button className="button ghost" onClick={() => saveRoutine(u.id)}>
                  Save Routine
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TrainerUsers;
