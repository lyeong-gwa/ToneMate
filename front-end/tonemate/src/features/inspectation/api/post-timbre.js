import { axios } from '@/lib/axios';

export const postTimbre = ({ formData }) => {
  return axios.post('/music/timbre', formData);
};
