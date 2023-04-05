import { axios } from '@/lib/axios';

export const logout = () => {
  axios.delete('/logout');
};
