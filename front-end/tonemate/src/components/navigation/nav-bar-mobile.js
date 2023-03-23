import { Bars3Icon } from "@heroicons/react/24/solid";
import { Alatsi } from "next/font/google";

const alatsi = Alatsi({
  weight: "400",
  subsets: ["latin"],
});

export default function NavBarMobile() {
  return (
    <>
      <div className="flex md:hidden justify-between items-center h-14 bg-black">
        <div className="flex justify-center items-center ml-4 text-lg text-white alatsi.claaName">
          TONEMATE
        </div>
        <div className="flex w-8 h-8 justify-center items-center mr-4">
          <Bars3Icon className="flex w-8 h-8 text-white border-white border-2 rounded-full " />
        </div>
      </div>
    </>
  );
}
