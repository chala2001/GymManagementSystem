import { useEffect, useState } from "react";

import { getTrainerUsers, updateUserRoutine } from "../services/api";


function TrainerUsers() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");
  const [routineEdits, setRoutineEdits] = useState({});


  // TEMP: trainerId (later we’ll get this dynamically)
  const trainerId = 7;

  useEffect(() => {
    fetchUsers();
  }, []);

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
    await updateUserRoutine(
      trainerId,
      userId,
      routineEdits[userId]
    );
    alert("Gym routine updated successfully");
  } catch (err) {
    alert("Failed to update gym routine");
  }
};


  return (
    <div>
      <h1>My Members</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <table className="trainer-table">
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
          rows="4"
          cols="30"
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
        <button onClick={() => saveRoutine(u.id)}>
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
