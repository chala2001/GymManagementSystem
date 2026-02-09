import { Routes, Route } from 'react-router-dom';

import Navbar from './components/Navbar';
import AdminLayout from './layouts/AdminLayout';

import Home from './pages/Home';
import About from './pages/About';
import Contact from './pages/Contact';
import Login from './pages/Login';
import Register from './pages/Register';

import AdminDashboard from './pages/AdminDashboard';
import AdminTrainers from './pages/AdminTrainers';

import TrainerLayout from "./layouts/TrainerLayout";
import TrainerDashboard from "./pages/TrainerDashboard";

import TrainerUsers from "./pages/TrainerUsers";



function App() {
  return (
    <>
      <Navbar />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />


        <Route path="/trainer/users" element={<TrainerUsers />} />


       

        <Route path="/admin" element={<AdminLayout />}>
        <Route index element={<AdminDashboard />} />
        <Route path="trainers" element={<AdminTrainers />} />
         </Route>



        <Route path="/trainer" element={<TrainerLayout />}>
        <Route index element={<TrainerDashboard />} />
      
        </Route>

      </Routes>
    </>
  );
}

export default App;
