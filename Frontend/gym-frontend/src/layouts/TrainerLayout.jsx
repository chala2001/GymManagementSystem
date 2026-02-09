import { Link, Outlet } from "react-router-dom";
import "../styles/TrainerDashboard.css"; // reuse admin style

function TrainerLayout() {
  return (
    <div className="admin-layout">
      <aside className="admin-sidebar">
        <h2>Trainer</h2>

        <nav>
          <Link to="/trainer">Dashboard</Link>
          
          <Link to="/trainer/users">My Members</Link>

          <Link to="/logout">Logout</Link>
        </nav>
      </aside>

      <main className="admin-content">
        <Outlet />
      </main>
    </div>
  );
}

export default TrainerLayout;
