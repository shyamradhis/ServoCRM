import './Dashboard.scss';
import React, { useEffect, useState } from 'react';
import { Row, Col, Nav, NavItem, NavLink } from 'reactstrap';
import { Line, Pie, Bar, Doughnut } from 'react-chartjs-2';
import 'chart.js/auto'; // Required for Chart.js
import { useUser } from './UserContext';

// Example data for analytics (Replace with real data)
const lineChartData = {
  labels: ['January', 'February', 'March', 'April', 'May', 'June'],
  datasets: [
    {
      label: 'Profit and Loss',
      data: [65, 59, 80, 81, 56, 55],
      fill: false,
      borderColor: 'rgb(75, 192, 192)',
      tension: 0.1,
    },
  ],
};

const pieChartData = activities => ({
  labels: ['Leads', 'Deals'],
  datasets: [
    {
      label: 'Activities',
      data: [activities.leads, activities.deals],
      backgroundColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)'],
      hoverOffset: 4,
    },
  ],
});

const tasksChartData = tasks => ({
  labels: ['Completed', 'Pending'],
  datasets: [
    {
      label: 'Tasks',
      data: [tasks.completed, tasks.pending],
      backgroundColor: ['rgb(75, 192, 192)', 'rgb(255, 205, 86)'],
      hoverOffset: 4,
    },
  ],
});

const meetingsChartData = meetings => ({
  labels: ['Scheduled', 'Completed'],
  datasets: [
    {
      label: 'Meetings',
      data: [meetings.scheduled, meetings.completed],
      backgroundColor: ['rgb(153, 102, 255)', 'rgb(255, 159, 64)'],
      hoverOffset: 4,
    },
  ],
});

const barChartData = {
  labels: ['T1', 'T2', 'T3', 'T4'],
  datasets: [
    {
      label: 'Revenue',
      data: [5000, 6000, 7000, 8000],
      backgroundColor: 'rgba(75, 192, 192, 0.2)',
      borderColor: 'rgb(75, 192, 192)',
      borderWidth: 1,
    },
  ],
};

// Sidebar component
const Sidebar = () => {
  return (
    <div className="sidebar">
      <h5></h5>
      <Nav vertical>
        <NavItem>
          <NavLink href="/Dashboard">Dashboard</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/lead">Leads</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/Contacts">Contacts</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/Accounts">Accounts</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/Deals">Deals</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/task">Task</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/Meeting">Meeting</NavLink>
        </NavItem>
      </Nav>
    </div>
  );
};

// Dashboard component
const Dashboard = () => {
  const { user } = useUser();

  // Example state for recent activities, tasks, and meetings (Replace with real data)
  const [recentActivities, setRecentActivities] = useState({
    leads: 5,
    deals: 3,
  });

  const [upcomingTasks, setUpcomingTasks] = useState({
    completed: 2,
    pending: 2,
  });

  const [recentMeetings, setRecentMeetings] = useState({
    scheduled: 3,
    completed: 2,
  });

  // Example effect to fetch recent activities, tasks, and meetings (Replace with real data fetching logic)
  useEffect(() => {
    // Fetch recent activities, tasks, and meetings here and update the state
    // Example:
    // setRecentActivities({ leads: 10, deals: 7 });
    // setUpcomingTasks({ completed: 4, pending: 4 });
    // setRecentMeetings({ scheduled: 5, completed: 4 });
  }, []);

  return (
    <div>
      <Row>
        <Col md="3">
          <Sidebar />
        </Col>
        <Col md="9">
          <Row>
            <Col md="12">
              <div className="">
                <br />
                <br />
                <h5>WELCOME BACK, {user?.name || 'SHYAM'}</h5>
                <br />
              </div>
            </Col>
          </Row>
          <Row>
            <Col md="4">
              <div className="dashboard-chart">
                <h5>Activities</h5>
                <Pie data={pieChartData(recentActivities)} />
              </div>
            </Col>
            <Col md="4">
              <div className="dashboard-chart">
                <h5>Upcoming Tasks</h5>
                <Doughnut data={tasksChartData(upcomingTasks)} />
              </div>
            </Col>
            <Col md="4">
              <div className="dashboard-chart">
                <h5>Meetings</h5>
                <Doughnut data={meetingsChartData(recentMeetings)} />
              </div>
            </Col>
          </Row>
          <br />
          <br />
          <br />
          <br />
          <Row className="dashboard-chart-container">
            <Col md="4">
              <div className="dashboard-chart">
                <h5>Profit Analytics</h5>
                <Line data={lineChartData} />
              </div>
            </Col>
            <Col md="4">
              <div className="dashboard-chart">
                <h5>Bar Chart Analytics</h5>
                <Bar data={barChartData} />
              </div>
            </Col>
          </Row>
        </Col>
      </Row>
    </div>
  );
};

export default Dashboard;
