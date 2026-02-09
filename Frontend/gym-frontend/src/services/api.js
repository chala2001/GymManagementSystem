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

export const checkTrainer = (username, password, trainerId) => {
  const token = btoa(`${username}:${password}`);
  return API.get(`/api/trainer/${trainerId}/users`, {
    headers: {
      Authorization: `Basic ${token}`,
    },
  });
};

export const checkUser = (username, password, userId) => {
  const token = btoa(`${username}:${password}`);
  return API.get(`/api/users/${userId}`, {
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

export const assignUserToTrainer = (userId, trainerId) => {
  return API.put(`/api/admin/users/${userId}/assign/${trainerId}`, null, {
    headers: getAuthHeader(),
  });
};

export const deleteUser = (userId) => {
  return API.delete(`/api/admin/users/${userId}`, {
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
    routine,
    {
      headers: {
        ...getAuthHeader(),
        'Content-Type': 'text/plain',
      },
    }
  );
};

export const registerUser = (userData) => {
  return API.post('/api/users/register', userData);
};

export const getUser = (userId) => {
  return API.get(`/api/users/${userId}`, {
    headers: getAuthHeader(),
  });
};

export const updateUser = (userId, userData) => {
  return API.put(`/api/users/${userId}`, userData, {
    headers: getAuthHeader(),
  });
};

