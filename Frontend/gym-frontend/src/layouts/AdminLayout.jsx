import { Link, Outlet, useNavigate } from 'react-router-dom';
import '../styles/AdminLayout.css';

function AdminLayout() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
  };

  return (
    <div className="admin-layout">
      {/* Sidebar */}
      <aside className="admin-sidebar">
        <h2>Admin</h2>
        <ul>
          <li><Link to="/admin">Dashboard</Link></li>
          <li><Link to="/admin/trainers">Trainers</Link></li>
          <li><button onClick={handleLogout}>Logout</button></li>
        </ul>
      </aside>

      {/* Main content */}
      <main className="admin-content">
        <Outlet />
      </main>
    </div>
  );
}

export default AdminLayout;
