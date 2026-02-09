import { Link, Outlet, useNavigate } from "react-router-dom";
import "../styles/AdminLayout.css";

function UserLayout() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <div className="admin-layout">
      <aside className="admin-sidebar">
        <div className="sidebar-header">
          <span className="pill">Member</span>
          <h2>Your Plan</h2>
        </div>

        <nav>
          <Link to="/user">Dashboard</Link>
        </nav>

        <button className="logout" onClick={handleLogout}>Logout</button>
      </aside>

      <main className="admin-content">
        <Outlet />
      </main>
    </div>
  );
}

export default UserLayout;
