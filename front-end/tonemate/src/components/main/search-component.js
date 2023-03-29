import { useState } from "react";

function SearchComponent() {
  const [category, setCategory] = useState("가수");
  const [keyword, setKeyword] = useState("검색어를 입력해주세요.");

  function handleInput() {
    console.log();
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
      <div className="flex flex-row ">
        <div>
          <select
            className="select select-warning w-full max-w-xs"
            onChange={handleChangeSelect}
          >
            <option value="가수">가수</option>
            <option value="제목">제목</option>
          </select>
        </div>
        <div>
          <input placeholder={keyword} onChange={handleChangeInput}></input>
        </div>
        <div>
          <button>
            <p className="text-white" onClick={handleClickButton}>
              검색
            </p>
          </button>
        </div>
      </div>
    </>
  );
}

export default SearchComponent;
