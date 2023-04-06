import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import {
  HeartIcon as HeartSolidIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
} from '@heroicons/react/24/solid';
import { HeartIcon as HeartOutlineIcon } from '@heroicons/react/24/outline';
import { MagnifyingGlassIcon } from '@heroicons/react/24/solid';
import { getSongs, addLike, deleteLike, searchKaraoke } from '@/features/karaoke';

function Table() {
  const [page, setPage] = useState(1);
  const [dataShow, setDataShow] = useState(null);

  const { isLoading, isError, error, data, isFetching, isPreviousData } = useQuery({
    queryKey: ['songs', page],
    queryFn: () => getSongs({ page }),
    keepPreviousData: true,
  });

  setDataShow(data);

  function clickHeart(isLike, tjNum) {
    if (isLike) {
      deleteLike({ tjNum });
    } else {
      addLike({ tjNum });
    }
  }

  const [category, setCategory] = useState('가수');
  const [keyword, setKeyword] = useState('검색어를 입력해주세요.');

  const handleChangeInput = (e) => {
    setKeyword(e.target.value);
  };

  const handleChangeSelect = (e) => {
    setCategory(e.target.value);
  };

  const searchQuery = useQuery({
    queryKey: ['search', page],
    queryFn: () => searchKaraoke({ page }),
    keepPreviousData: true,
  });
  function clickSearchButton() {
    console.log('clickSearchButton');
  }

  return (
    <>
      <div className="h-18 fade-in-custom-10s mb-5 flex w-full flex-col items-center justify-between lg:h-12 lg:flex-row lg:items-start">
        <div className="flex h-10 w-full flex-row items-center justify-around rounded-full border-2 border-red-600 bg-white lg:w-5/12 ">
          <div className="flex h-1/2 items-center">
            <select className="flex shrink-0 " onChange={handleChangeSelect}>
              <option value="제목">제목</option>
              <option value="가수">가수</option>
            </select>
          </div>
          <div className="flex h-12 w-9/12 items-center">
            <input
              className="h-2/3 w-full"
              placeholder={keyword}
              onChange={handleChangeInput}
            ></input>
          </div>
          <button onClick={clickSearchButton} className="flex  h-12 items-center justify-center">
            <MagnifyingGlassIcon className="h-7 w-7 bg-transparent text-black" />
          </button>
        </div>
        {/* <div className="flex h-10 w-3/4 flex-row items-end justify-around lg:w-1/4 lg:justify-end">
          <button
            onClick={clickTJButton}
            className="mx-3 block rounded-full border border-white px-3"
          >
            <p className="mx-2 font-alatsi text-white">TJ TOP100</p>
          </button>
          <button onClick={clickKYButton} className="block rounded-full border border-white">
            <p className="mx-3 font-alatsi text-white">KY TOP100</p>
          </button>
        </div> */}
      </div>
      <div>
        {isLoading ? (
          <div>Loading...</div>
        ) : isError ? (
          <div>Error: {error.message}</div>
        ) : (
          <div className="flex h-full w-full flex-col items-center justify-start">
            {/* Table Head : 테이블 태그로 작성시에 사이즈 조절에 문제가 생겨서 div태그로 커스터마이징 */}
            <div className="fade-in-custom-15s mb-3 flex h-10 w-full flex-row justify-between border-y border-yellow-400">
              <div className="mx-2 flex w-4/12 flex-row items-center bg-black">
                <p className="font-nanum text-sm text-white lg:text-lg">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;제목
                </p>
              </div>
              <div className="flex w-4/12 flex-row items-center justify-center bg-black">
                <p className="font-nanum text-sm text-white lg:text-lg">가수</p>
              </div>
              <div className="flex w-1/12 flex-row items-center justify-center bg-black">
                <p className="font-nanum text-sm text-white lg:text-lg">TJ</p>
              </div>
              <div className="mx-2 flex w-1/12 flex-row items-center justify-center bg-black">
                <p className="font-nanum text-sm text-white lg:text-lg">담기</p>
              </div>
            </div>
            {/* Table Body : 테이블 태그로 작성시에 사이즈 조절에 문제가 생겨 div태그로 커스터마이징 */}
            <div className="fade-in-custom-15s mb-2 flex w-full flex-col">
              {dataShow?.songs?.map((song) => (
                <div
                  key={song?.tjNum}
                  className="mb-1 flex h-14 w-full flex-row justify-between rounded-full bg-white/50"
                >
                  <div className="mx-2 flex w-4/12 flex-row items-center ">
                    <p className="font-nanum text-sm text-white lg:text-lg">
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{song?.title}
                    </p>
                  </div>
                  <div className="flex w-4/12 flex-row items-center justify-center ">
                    <p className="font-nanum text-sm text-white lg:text-lg">{song?.singer}</p>
                  </div>
                  <div className="flex w-1/12 flex-row items-center justify-center ">
                    <p className="font-nanum text-sm text-white lg:text-lg">{song?.tjNum}</p>
                  </div>
                  <div className="mx-2 flex w-1/12 flex-row items-center justify-center ">
                    <button onClick={() => clickHeart(song?.isLike, song?.tjNum)}>
                      {song?.isLike ? (
                        <HeartSolidIcon className="h-4 w-4 text-white lg:h-5 lg:w-5" />
                      ) : (
                        <HeartOutlineIcon className="h-4 w-4 text-white lg:h-5 lg:w-5" />
                      )}
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>
      <div className="fade-in-custom-20s mb-2 flex h-10 w-2/5 flex-row items-center justify-between lg:w-1/5">
        <button onClick={() => setPage((old) => Math.max(old - 1, 1))} disabled={page === 1}>
          <ChevronLeftIcon className="h-6 w-6 text-white" />
        </button>
        <span className="font-nanum text-sm text-white lg:text-lg">{page}</span>
        <button
          onClick={() => {
            if (page !== dataShow?.totalPageNumber) {
              setPage((old) => old + 1);
            }
          }}
          disabled={page === dataShow?.totalPageNumber}
        >
          <ChevronRightIcon className="h-6 w-6 text-white" />
        </button>
      </div>
    </>
  );
}

export default Table;
