import Image from 'next/image';

import { useUser } from '@/features/auth';

export default function UserCard() {
  const { user } = useUser({ redirectTo: '/', redirectIfFound: false });
  const userData = user.data;
  return (
    <>
      <div className="m-1 my-3 flex w-11/12 flex-row items-center justify-around border-y-2 border-gray-500">
        <div className="mx-2 my-5 flex w-2/5 ">
          <img
            className="h-16 w-16 rounded-full"
            src={userData.profileImg}
            alt={`Profile img of ${userData.nickname}`}
          />
        </div>
        <div className="mx-2 my-5 flex w-3/5 flex-col items-center justify-between">
          <div className="my-1 flex w-full flex-row items-center justify-center">
            <p className="text-sm text-white 2xl:text-sm">안녕하세요</p>
          </div>
          <div className="my-1 flex w-full flex-row items-center justify-center">
            <p className="text-2xl  text-white">{userData.nickname} 님</p>
          </div>
        </div>
      </div>
    </>
  );
}
