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
      <div className="hidden md:block w-1/5 h-screen bg-black border-r-2 border-gray-600">
        <div className="flex justify-center items-center h-14 sm:text-2xl text-white alatsi.claaName">
          TONEMATE
        </div>
        <UserCard />
        <NavMenu />
      </div>
    </>
  );
}
