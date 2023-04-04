import { axios } from '@/lib/axios';

export const logout = () => {
  return axios.delete('/logout');
};
