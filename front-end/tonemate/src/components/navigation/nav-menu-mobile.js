import NavMenu from './nav-menu';
import UserCard from '@/components/card/user-card';

export default function NavMenuMobile() {
  return (
    <>
      <div className="absolute bottom-0 z-50 flex w-full flex-col items-center justify-center bg-gray-500 lg:hidden">
        <UserCard />
        <NavMenu />
      </div>
    </>
  );
}
