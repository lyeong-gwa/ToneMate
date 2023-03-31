import Link from 'next/link';
import { HiBars3 } from 'react-icons/hi2';

export const Navbar = ({ user }) => {
  console.log(user);
  return (
    <div className="navbar fixed z-50 bg-base-100">
      <div className="flex-1">
        <Link className="btn-ghost btn text-xl normal-case" href="/home">
          TONEMATE
        </Link>
      </div>
      <div className="flex-none">
        {user.data ? (
          <button className="btn-ghost btn">
            <HiBars3 />
          </button>
        ) : (
          <button className="btn-ghost btn">시작하기</button>
        )}
      </div>
    </div>
  );
};
