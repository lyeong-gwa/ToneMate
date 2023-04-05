import { axios } from '@/lib/axios';

export const getResults = () => {
  return axios.get('/music/result');
};
