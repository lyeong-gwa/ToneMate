import { useEffect } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useRouter } from 'next/router';

import { getUser } from '../api/getUser';

export const useUser = ({ redirectTo = '', redirectIfFound = false } = {}) => {
  const router = useRouter();

  const { data: user } = useQuery({
    queryKey: ['user'],
    queryFn: () => getUser(),
  });

  useEffect(() => {
    if (!redirectTo || !user) return;

    if ((redirectTo && !redirectIfFound && !user.data) || (redirectIfFound && user.data)) {
      router.push(redirectTo);
    }
  }, [user, redirectIfFound, redirectTo, router]);

  return { user };
};