import { Link } from 'react-router-dom';
import '../styles/Navbar.css';

function Navbar() {
  return (
    <nav className="navbar">
      <Link to="/" className="navbar-logo">
        ForgeFit
      </Link>

      <ul className="navbar-links">
        <li><Link to="/about">About</Link></li>
        <li><Link to="/contact">Contact</Link></li>
        <li><Link to="/login">Login</Link></li>
      </ul>

      <Link className="navbar-cta" to="/register">
        Start Membership
      </Link>
    </nav>
  );
}

export default Navbar;
