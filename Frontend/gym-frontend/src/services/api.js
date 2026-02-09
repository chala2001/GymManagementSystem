import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080',
});

export const checkAdmin = (username, password) => {
  const token = btoa(`${username}:${password}`);
  return API.get('/api/admin/trainers', {
    headers: {
      Authorization: `Basic ${token}`,
    },
  });
};

export const checkTrainer = (username, password) => {
  const token = btoa(`${username}:${password}`);
  return API.get('/api/trainer/1/users', {
    headers: {
      Authorization: `Basic ${token}`,
    },
  });
};

export const checkUser = (username, password) => {
  const token = btoa(`${username}:${password}`);
  return API.get('/api/users/1', {
    headers: {
      Authorization: `Basic ${token}`,
    },
  });
};


// Get auth header from localStorage
const getAuthHeader = () => {
  const auth = localStorage.getItem('auth');
  return {
    Authorization: `Basic ${auth}`,
  };
};

// ADMIN APIs
export const getAllTrainers = () => {
  return API.get('/api/admin/trainers', {
    headers: getAuthHeader(),
  });
};

export const getAllUsers = () => {
  return API.get('/api/admin/users', {
    headers: getAuthHeader(),
  });
};

// Delete trainer by admin
export const deleteTrainer = (trainerId) => {
  return API.delete(`/api/admin/trainers/${trainerId}`, {
    headers: getAuthHeader(),
  });
};

// Create trainer (admin)
export const createTrainer = (trainerData) => {
  return API.post('/api/admin/trainers', trainerData, {
    headers: getAuthHeader(),
  });
};

export const getTrainerUsers = (trainerId) => {
  return API.get(`/api/trainer/${trainerId}/users`, {
    headers: getAuthHeader(),
  });
};
export const updateUserRoutine = (trainerId, userId, routine) => {
  return API.put(
    `/api/trainer/${trainerId}/users/${userId}/routine`,
    { gymRoutine: routine },
    { headers: getAuthHeader() }
  );
};

