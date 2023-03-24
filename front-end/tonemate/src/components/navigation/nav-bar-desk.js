// 컴포넌트 임포트
import UserCard from "../card/user-card";
import NavMenu from "./nav-menu";

import { Alatsi } from "next/font/google";

const alatsi = Alatsi({
  weight: "400",
  subsets: ["latin"],
});

export default function NavBarDesk() {
  return (
    <>
      <div className="hidden lg:block w-88 h-screen bg-black border-r-2 border-gray-600">
        <div className="flex justify-center items-center h-12 text-white ">
          <p className="text-1xl lg:text-2xl text-white">TONEMATE</p>
        </div>
        <div className="flex flex-col justify-start items-center mx-2">
          <UserCard />
          <NavMenu />
        </div>
      </div>
    </>
  );
}
