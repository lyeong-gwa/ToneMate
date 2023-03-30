import { useState } from "react";
import { MagnifyingGlassIcon } from "@heroicons/react/24/solid";

function SearchComponent() {
  const [category, setCategory] = useState("가수");
  const [keyword, setKeyword] = useState("검색어를 입력해주세요.");

  function handleInput() {
    console.log("tlqkf");
  }

  const handleChangeInput = (e) => {
    setKeyword(e.target.value);
  };

  const handleChangeSelect = (e) => {
    setCategory(e.target.value);
  };

  function handleClickButton() {
    console.log(category);
    console.log(keyword);
    // 내부 내용 바꾸세요
  }

  // css 먹여야함
  return (
    <>
      <div className="flex flex-col lg:flex-row w-full h-18 lg:h-12 justify-start items-center lg:items-start mb-3">
        <div className="flex flex-row w-full lg:w-9/12 h-10 justify-around items-center rounded-full bg-white border-2	border-width: 2px; border-red-300 ">
          <div className="flex h-1/2 items-center">
            <select className="flex shrink-0 " onChange={handleChangeSelect}>
              <option value="가수">제목</option>
              <option value="제목">가수</option>
            </select>
          </div>
          <div className="flex w-3/6 h-12 items-center">
            <input
              className="w-full h-2/3"
              placeholder={keyword}
              onChange={handleChangeInput}
            ></input>
          </div>
          <div className="flex w-1/6 h-12 justify-center items-center">
            <MagnifyingGlassIcon className="w-7 h-7 bg-transparent text-black" />
          </div>
        </div>
        <div className="flex flex-row w-3/4 lg:w-1/4 h-10 justify-around lg:justify-end items-end">
          <button className="block border rounded-full border-white mx-3 px-3">
            <p className="font-alatsi text-white mx-2">TJ TOP100</p>
          </button>
          <button className="block border rounded-full border-white">
            <p className="font-alatsi text-white mx-3">KY TOP100</p>
          </button>
        </div>
      </div>
    </>
  );
}

export default SearchComponent;
