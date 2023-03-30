import Router from "next/router";
import { useEffect, useState } from "react";

function NavButton(props) {
  const baseURL = "http://localhost:3000";
  let iconColor = "block w-6 h-6 text-gray-400";
  let textColor = "text-white";

  const [isNow, setIsNow] = useState(false);

  // useEffect(() => {
  //   let nowURL = window.location.href;
  //   nowURL = nowURL.substring(baseURL.length);

  //   if (props.value === nowURL) {
  //     setIsNow(true);
  //   }
  // });

  function clickNavButton() {
    Router.push(props.value);
  }

  return (
    <>
      <button
        onClick={clickNavButton}
        className="flex w-full h-10 flex-row items-center justify-center my-1 rounded-full ml-2"
      >
        <div className="flex w-1/5 mx-3">
          <props.icon className={iconColor} />
        </div>
        <div className="flex w-4/5 mx-3">
          <p className={textColor}>{props.name}</p>
        </div>
      </button>
    </>
  );
}

export default NavButton;
