// 컴포넌트 임포트
import UserCard from "../card/user-card";
import NavMenu from "./nav-menu";
import Logo from "../common/logo-button";

export default function NavBarDesk() {
  return (
    <>
      <div className="hidden lg:flex flex-col w-80 h-screen bg-black border-r-2 border-gray-600">
        <Logo />
        <div className="flex flex-col justify-start items-center mx-2">
          <UserCard />
          <NavMenu />
        </div>
      </div>
    </>
  );
}
