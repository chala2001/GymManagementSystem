import { Link, Outlet, useNavigate } from "react-router-dom";
import "../styles/AdminLayout.css";

function TrainerLayout() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <div className="admin-layout">
      <aside className="admin-sidebar">
        <div className="sidebar-header">
          <span className="pill">Trainer</span>
          <h2>Client Flow</h2>
        </div>

        <nav>
          <Link to="/trainer">Dashboard</Link>
          <Link to="/trainer/users">My Members</Link>
        </nav>

        <button className="logout" onClick={handleLogout}>Logout</button>
      </aside>

      <main className="admin-content">
        <Outlet />
      </main>
    </div>
  );
}

export default TrainerLayout;
