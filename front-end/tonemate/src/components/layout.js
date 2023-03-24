import NavBarDesk from "./navigation/nav-bar-desk";
import NavBarMobile from "./navigation/nav-bar-mobile";
import Image from "next/image";

export default function Layout() {
  return (
    <>
      <div className="flex flex-col lg:flex-row w-full h-screen bg-black">
        <NavBarMobile />
        <NavBarDesk />
        <div className="flex flex-col w-full h-full lg:w-5/6 bg-black">
          <div className="flex flex-col w-full h-1/2 justify-center items-center">
            <div className="flex w-11/12 h-5/6">
              <div className="flex w-full lg:w-2/5 h-full bg-gray-400">
                <img src="https://t1.daumcdn.net/cfile/tistory/222C993554828EE222" />
              </div>
              <div className="hidden md:flex flex-col w-3/5 h-full bg-gray-200">
                <p>으아악</p>
                <p>너무 힘들다</p>
                <p>프론트 너무 힘들다</p>
                <p>으갸갸갹</p>
              </div>
            </div>
          </div>
          <div className="flex flex-col w-full h-1/2 justify-center items-center">
            <div className="flex w-11/12 h-5/6 bg-gray-100">안녕</div>
          </div>
        </div>
      </div>
    </>
  );
}
