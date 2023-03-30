// 컴포넌트 임포트
import UserCard from "../card/user-card";
import NavMenu from "./nav-menu";
import Logo from "../common/logo-button";

export default function NavBarDesk() {
  return (
    <>
      <div className="hidden lg:flex flex-col w-80 h-screen bg-transparent border-r-2 border-gray-600">
        <Logo />
        <div className="flex flex-col justify-start mx-2">
          <div className="flex justify-center">
            <UserCard />
          </div>
          <NavMenu />
        </div>
      </div>
    </>
  );
}
