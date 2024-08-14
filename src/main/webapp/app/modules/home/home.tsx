import './home.scss';
import React from 'react';
import { Link } from 'react-router-dom';
import { Row, Col, Container, Button } from 'reactstrap';
import { useAppSelector } from 'app/config/store';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import Dashboard from './Dashboard';

// Slide images URLs
const slideImages = [
  'https://img.freepik.com/free-vector/flat-working-day-scene-with-different-business-people_23-2148946792.jpg?t=st=1722402382~exp=1722405982~hmac=afac475cef64756adf4592f8d882f8cb94a23b93dbf403d59d0d74fa457ea924&w=996',
  'https://img.freepik.com/free-vector/hand-drawn-flat-design-crm-illustration_23-2149383345.jpg?t=st=1722402494~exp=1722406094~hmac=9cda69c15c910a3f42125d0663baff69a3b4ea3012f72cf1b17116ac9c9a49cb&w=996',
  'https://img.freepik.com/free-vector/protection-secure-data-access-flat-illustration-concept_126523-3067.jpg?t=st=1722402535~exp=1722406135~hmac=1f7d513030de9c81c385fa4c0b66e1a7cd0b2c79a66a12dafa5b0c95ad8d317f&w=996',
  'https://img.freepik.com/free-vector/data-inform-illustration-concept_114360-864.jpg?w=996&t=st=1722190310~exp=1722190910~hmac=321d4c4944c47de55bd051fcbea1a06d9d4c0b0ab0cfa29c4ac94205144cb4ac',
];

// Slider settings
const sliderSettings = {
  dots: true,
  infinite: true,
  speed: 500,
  slidesToShow: 1,
  slidesToScroll: 1,
  autoplay: true,
  autoplaySpeed: 1000, // 1 second
};

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div>
      {account && account.login ? (
        <Dashboard />
      ) : (
        <>
          <Row
            className="header"
            style={{
              backgroundImage:
                'url(https://img.freepik.com/free-photo/person-office-analyzing-checking-finance-graphs_23-2150377141.jpg?w=996&t=st=1723107256~exp=1723107856~hmac=3c65fd7e754d5413bb0590d093a269ad8e2f339f44a5bd81826c79cf11602b25)', // Update with the path to your image
              backgroundSize: 'cover',
              backgroundPosition: 'center',
              backgroundRepeat: 'no-repeat',
              height: '600px', // Adjust this value as needed
              color: 'white', // Optional: changes text color
            }}
          >
            <Col>
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <h1 className="display-4">ELEVATE YOUR SALES GAME</h1>
              <p>Discover how our CRM system can transform your sales approach, optimize workflows, and boost your business growth.</p>
            </Col>
            <br />
            <br />
            <br />
            <br />
            <div className="parent-container">
              <Button color="primary" size="lg" tag={Link} to="/get-started" className="center-button">
                Get Started
              </Button>
            </div>
          </Row>

          {/* 
          <Row className="features">
            <Col md="4">
              <div className="feature">
                <h4>Client management</h4>
                <div className="definition"></div>
              </div>
            </Col>
            <Col md="4">
              <div className="feature">
                <h4>Digital marketing</h4>
                <div className="definition"></div>
              </div>
            </Col>
            <Col md="4">
              <div className="feature">
                <h4>Workflow automation</h4>
                <div className="definition"></div>
              </div>
            </Col>
            <Col md="4">
              <div className="feature">
                <h4>Sales analytics</h4>
                <div className="definition"></div>
              </div>
            </Col>
            <Col md="4">
              <div className="feature">
                <h4>Inventory management</h4>
                <div className="definition"></div>
              </div>
            </Col>
            <Col md="4">
              <div className="feature">
                <h4>Customer service</h4>
                <div className="definition"></div>
              </div>
            </Col>
          </Row> */}

          <Row className="feature-highlight">
            <Col>
              <div className="highlight">
                <h2>OUR CRM FEATURES</h2>
                <ul>
                  <li>Automated Lead Scoring</li>
                  <li>Sales Pipeline Management</li>
                  <li>Integration with Email </li>
                  <li>Real-time Analytics and Reporting</li>
                </ul>
              </div>
            </Col>
          </Row>

          <Row className="slideshow">
            <Col>
              <Slider {...sliderSettings}>
                {slideImages.map((src, index) => (
                  <div key={index}>
                    <img src={src} alt={`Slide ${index + 1}`} style={{ width: '100%', borderRadius: '8px' }} />
                  </div>
                ))}
              </Slider>
            </Col>
          </Row>
          <br />
          <br />

          <Row className="best-crm-tools">
            <Col>
              <h2>Best CRM Tools</h2>
              <br />
              <br />
              <div className="crm-tools">
                <div className="crm-tool">
                  <p>Calls from CRM with call recording</p>
                </div>
                <div className="crm-tool">
                  <p>Launch SMS and email marketing campaigns</p>
                </div>
                <div className="crm-tool">
                  <p>AI scoring</p>
                </div>
                <div className="crm-tool">
                  <p>Facebook, Instagram, WhatsApp integration</p>
                </div>
                <div className="crm-tool">
                  <p>Forms and landing pages</p>
                </div>
                <div className="crm-tool">
                  <p>Set-and-forget' automation rules</p>
                </div>
                <div className="crm-tool">
                  <p>Automated sales funnels</p>
                </div>
                <div className="crm-tool">
                  <p>Invoicing and online payments</p>
                </div>
                <div className="crm-tool">
                  <p>Invoice payment status tracking</p>
                </div>
                <div className="crm-tool">
                  <p>Powerful analytics and reports</p>
                </div>
                <div className="crm-tool">
                  <p>Customer support chat</p>
                </div>
              </div>
            </Col>
          </Row>
          <br />
          <br />

          {/* New image row */}
          <Row className="industries-image">
            <Col className="text-center">
              <h2>Suitable Industies</h2>
              <br />
              <br />
              <img
                src="https://rednote.in/crm-software-india/img/Industries.png"
                alt="Industries"
                style={{ width: '100%', borderRadius: '8px' }}
              />
            </Col>
          </Row>
          <br />
          <br />
          <br />

          <div className="parent-container">
            <Button color="primary" size="lg" tag={Link} to="/get-started" className="center-button">
              Get Started
            </Button>
          </div>

          <Row className="follow-up">
            <Col className="text-center">
              <div className="follow-up-container">
                <h2>Never Miss Out on a Follow-Up</h2>
                <p>Ensure all your follow-ups are timely and effective with our automated reminders and task management features.</p>
                <img
                  src="https://odoocdn.com/openerp_website/static/src/img/apps/crm/follow-up-example.svg"
                  alt="Follow-up Example"
                  className="follow-up-image"
                />
              </div>
            </Col>
          </Row>
          <br />
          <br />
          <br />
          <Row className="industries-image">
            <Col className="text-center">
              <h2>Modules</h2>
              <img
                src="https://qrsolutions.in/wp-content/uploads/2021/02/Sec3_CRM-1-scaled.jpeg"
                alt="Industries"
                style={{ width: '40%', borderRadius: '8px' }}
              />
            </Col>
          </Row>

          <Row className="team-performance">
            <div className="text-section">
              <b>
                <h3>Team performance tracking</h3>
              </b>
              <h4>See how your team is performing on their tasks.</h4>
              <ul>
                <p>Check real-time reports with hour-by-hour performance data</p>
                <p>Access your entire team's performance from anywhere</p>
                <p>See how many calls each person is making and how many deals they are closing</p>
              </ul>
            </div>
            <div className="image-section">
              <img
                src="https://cdn.dribbble.com/users/2444986/screenshots/10046913/media/52490f46f85c1ccb754faee8507cba9f.png"
                alt="Team Performance"
                style={{ borderRadius: '8px' }}
              />
            </div>
          </Row>
          {/* <Row className="industries-image">
            <Col className="text-center">
            
              <img 
                src="https://qrsolutions.in/wp-content/uploads/2021/02/Sec3_CRM-1-scaled.jpeg" 
                
                alt="Industries" 
                style={{ width: '40%', borderRadius: '8px' }} 
              />
              
            </Col>
          </Row>
           */}

          <br />
          <br />
          <Row className="industries-image">
            <Col className="text-center">
              <h2>Integrates with</h2>
              <img
                src="https://odoocdn.com/openerp_website/static/src/img/icons/mail.svg"
                alt="Industries"
                style={{ width: '10%', borderRadius: '8px' }}
              />
              <img
                src="https://odoocdn.com/openerp_website/static/src/img/icons/message-2.svg"
                alt="Industries"
                style={{ width: '10%', borderRadius: '8px' }}
              />
              <img
                src="https://odoocdn.com/openerp_website/static/src/img/icons/vibrate.svg"
                alt="Industries"
                style={{ width: '10%', borderRadius: '8px' }}
              />
              <img
                src="https://odoocdn.com/openerp_website/static/src/img/icons/call.svg"
                alt="Industries"
                style={{ width: '10%', borderRadius: '8px' }}
              />
            </Col>
          </Row>
        </>
      )}
    </div>
  );
};

export default Home;
