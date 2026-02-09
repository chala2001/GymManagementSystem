import '../styles/Home.css';

function Home() {
  return (
    <div className="home">
      {/* Hero Section */}
      <div className="hero">
        <div className="hero-overlay">
          <h1>Transform Your Body</h1>
          <p>Professional Gym Management System</p>
        </div>
      </div>

      {/* Features Section */}
      <div className="features">
        <div className="card">
          <h3>Admin Panel</h3>
          <p>Manage trainers and oversee the entire gym system.</p>
        </div>

        <div className="card">
          <h3>Trainer Dashboard</h3>
          <p>Assign workout plans and manage personal training clients.</p>
        </div>

        <div className="card">
          <h3>User Dashboard</h3>
          <p>Track progress, workouts, and personal fitness data.</p>
        </div>
      </div>
    </div>
  );
}

export default Home;
