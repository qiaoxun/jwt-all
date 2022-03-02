import axios from 'axios';

function addTimestamp(url) {
  if (url.indexOf('?') > -1) {
    return `${url}&timestamp=${+new Date()}`;
  } 
  return `${url}?timestamp=${+new Date()}`;
}

function getToken() {
  return window.localStorage.getItem('token');
}

function makeClient() {
  const instance = axios.create({
    timeout: 1000 * 60,
    timeoutErrorMessage: 'Timeout',
    xsrfCookieName: 'csrftoken',
    xsrfHeaderName: 'X-CSRFToken',
  });

  instance.interceptors.request.use((config) => {
    const newConfig = config;
    newConfig.url = addTimestamp(newConfig.url);
    newConfig.headers.Authorization = `Bearer ${getToken()}`;
    return newConfig;
  });

  instance.interceptors.response.use(
    (res) => res.data,
    (err) => {
      if (err.response.status === 401) {
        window.location.href = '/login';
        return;
      }

      // onError(err.response?.data ?? err);
      Promise.reject(err);
    },
  );

  return instance;
}

const client = makeClient();
export default client;
