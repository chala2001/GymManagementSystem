import { Link } from 'react-router-dom';
import '../styles/Home.css';

function Home() {
  return (
    <div className="home">
      <section className="hero">
        <div className="hero-content">
          <span className="pill">New: unified gym command center</span>
          <h1>Build strength. Run the gym like a pro.</h1>
          <p>
            ForgeFit keeps members, trainers, and admin workflows in one sleek
            hub. Track progress, assign routines, and manage your studio without
            the clutter.
          </p>
          <div className="hero-actions">
            <Link className="button" to="/register">Create a member profile</Link>
            <Link className="button ghost" to="/about">See how it works</Link>
          </div>
        </div>
        <div className="hero-card">
          <div className="hero-card-header">
            <span>Live Studio Snapshot</span>
            <strong>Week 12</strong>
          </div>
          <div className="hero-card-body">
            <div>
              <p className="label">Active Members</p>
              <p className="value">184</p>
            </div>
            <div>
              <p className="label">Trainer Sessions</p>
              <p className="value">46</p>
            </div>
            <div>
              <p className="label">Programs Updated</p>
              <p className="value">29</p>
            </div>
          </div>
          <div className="hero-card-footer">
            <span>Momentum score</span>
            <strong>92%</strong>
          </div>
        </div>
      </section>

      <section className="home-section">
        <div className="section-header">
          <h2>Everything you need to run the floor</h2>
          <p>
            Role-based views keep the right tools in the right hands. Admins
            oversee the studio, trainers program with clarity, and members stay
            on track.
          </p>
        </div>
        <div className="grid grid-3">
          <div className="card feature-card">
            <h3>Admin Command</h3>
            <p>Recruit and manage trainers, audit member counts, and keep data clean.</p>
            <ul>
              <li>Trainer onboarding</li>
              <li>Member oversight</li>
              <li>Secure access roles</li>
            </ul>
          </div>
          <div className="card feature-card">
            <h3>Trainer Flow</h3>
            <p>Assign routines, update sessions, and track your client roster.</p>
            <ul>
              <li>Client management</li>
              <li>Routine updates</li>
              <li>Performance notes</li>
            </ul>
          </div>
          <div className="card feature-card">
            <h3>Member Growth</h3>
            <p>Keep personal stats in one place and build a steady fitness habit.</p>
            <ul>
              <li>Body metrics</li>
              <li>Workout plans</li>
              <li>Progress insights</li>
            </ul>
          </div>
        </div>
      </section>

      <section className="home-section split">
        <div>
          <h2>Designed for real gyms, not spreadsheets.</h2>
          <p>
            ForgeFit matches your backend capabilities with a clean, modern UI.
            Basic auth keeps roles separate while the dashboards surface the
            data that matters most.
          </p>
        </div>
        <div className="card highlight-card">
          <p className="label">Weekly focus</p>
          <h3>Retention & coaching clarity</h3>
          <p className="muted">
            Make it easy for trainers to update plans and for members to see
            their progress. Admins keep the whole studio aligned.
          </p>
          <Link className="button secondary" to="/login">Enter the studio</Link>
        </div>
      </section>
    </div>
  );
}

export default Home;
