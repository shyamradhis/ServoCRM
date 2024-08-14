import './footer.scss';
import React from 'react';
import { Col, Row, Container } from 'reactstrap';
import { Link } from 'react-router-dom';

const Footer = () => (
  <div className="footer">
    <Container>
      <Row>
        <Col md="4">
          <h5>ABOUT US</h5>
          <ul>
            <p>
              We are a leading company in CRM solutions, dedicated to helping you manage your customer<p></p>relationships effectively and
              efficiently.
            </p>
          </ul>
        </Col>
        <Col md="4">
          <h5>CONTACT US</h5>

          <p>servocrm@gmail.com</p>
          <p>9360365790</p>
        </Col>
        <Col md="4">
          <h5>Follow Us</h5>
          <div className="social-icons">
            <a href="https://www.facebook.com" target="_blank" rel="noopener noreferrer">
              <img
                src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/Facebook_f_logo_%282019%29.svg/512px-Facebook_f_logo_%282019%29.svg.png"
                alt="Facebook"
              />
            </a>
            <a href="https://www.linkedin.com" target="_blank" rel="noopener noreferrer">
              <img
                src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/LinkedIn_Logo.svg/512px-LinkedIn_Logo.svg.png"
                alt="LinkedIn"
              />
            </a>
            <a href="https://www.instagram.com" target="_blank" rel="noopener noreferrer">
              <img
                src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Instagram_icon.png/600px-Instagram_icon.png"
                alt="Instagram"
              />
            </a>
          </div>
        </Col>
      </Row>
      <Row>
        <Col md="12" className="text-center mt-3">
          <p>&copy; {new Date().getFullYear()} Your Company. All Rights Reserved.</p>
        </Col>
      </Row>
    </Container>
  </div>
);

export default Footer;
