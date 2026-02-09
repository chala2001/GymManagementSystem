import '../styles/About.css';

function About() {
  return (
    <div className="page about-page">
      <h1 className="page-title">The studio brain behind ForgeFit</h1>
      <p className="page-subtitle">
        Built to mirror the backend roles you already run: admins, trainers, and
        members. Every screen is designed to show exactly what each role needs.
      </p>

      <div className="section grid grid-3">
        <div className="card">
          <h3>Admin clarity</h3>
          <p>
            Manage trainers, monitor member growth, and keep your studio data
            clean in one dashboard.
          </p>
        </div>
        <div className="card">
          <h3>Trainer focus</h3>
          <p>
            Assign routines, review member stats, and deliver programs without
            getting lost in spreadsheets.
          </p>
        </div>
        <div className="card">
          <h3>Member momentum</h3>
          <p>
            Members track body metrics, routines, and progress updates in a
            single space.
          </p>
        </div>
      </div>

      <div className="section about-highlight">
        <div>
          <span className="pill">Backend aligned</span>
          <h2>Designed around your Spring Boot API</h2>
          <p>
            ForgeFit connects to the existing endpoints for registration,
            trainers, and member profiles. The UI simply makes it feel premium.
          </p>
        </div>
        <div className="card">
          <h4>What's included</h4>
          <ul>
            <li>Role-based dashboards</li>
            <li>Routine updates & profile edits</li>
            <li>Admin user management</li>
            <li>Trainer client roster</li>
          </ul>
        </div>
      </div>
    </div>
  );
}

export default About;
