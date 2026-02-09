import '../styles/Contact.css';

function Contact() {
  return (
    <div className="page contact-page">
      <h1 className="page-title">Let's build your gym workflow</h1>
      <p className="page-subtitle">
        Have questions about onboarding, trainer access, or member setup? Drop
        us a note and we will help you map the perfect flow.
      </p>

      <div className="section contact-grid">
        <div className="card">
          <h3>Studio Support</h3>
          <p className="muted">
            Available Mon-Fri, 9am - 6pm to help with setup and training.
          </p>
          <div className="contact-details">
            <span>Email</span>
            <strong>support@forgefit.io</strong>
          </div>
          <div className="contact-details">
            <span>Phone</span>
            <strong>+1 (555) 018-2400</strong>
          </div>
        </div>

        <form className="card contact-form">
          <h3>Send a message</h3>
          <div className="form-grid">
            <input className="input" placeholder="Your name" />
            <input className="input" type="email" placeholder="Email address" />
            <input className="input" placeholder="Gym name" />
            <textarea className="input" rows="4" placeholder="Tell us about your needs" />
          </div>
          <button className="button" type="button">Send message</button>
        </form>
      </div>
    </div>
  );
}

export default Contact;
