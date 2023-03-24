import { Bars3Icon } from "@heroicons/react/24/solid";

export default function NavBarMobile() {
  return (
    <>
      <div className="flex lg:hidden justify-between items-center h-14 md:h-16 bg-transparent">
        <div className="flex justify-center items-center ml-4 md:ml-6">
          <p className="text-xl md:text-2xl text-white">TONEMATE</p>
        </div>
        <div className="flex justify-center items-center mr-4 md:mr-6">
          <Bars3Icon className="w-7 h-7 md:w-8 md:h-8 text-white " />
        </div>
      </div>
    </>
  );
}
