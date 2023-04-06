import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { HeartIcon as HeartSolidIcon } from '@heroicons/react/24/solid';
import { HeartIcon as HeartOutlineIcon } from '@heroicons/react/24/outline';
import { getSongs } from '@/features/karaoke';

function Table() {
  const [page, setPage] = useState(1);

  const { isLoading, isError, error, data, isFetching, isPreviousData } = useQuery({
    queryKey: ['songs', page],
    queryFn: () => getSongs({ page }),
    keepPreviousData: true,
  });

  function clickHeart() {
    console.log('clickHeart');
  }

  // function prevPages() {
  //   console.log('prevPage');
  // }

  // function nextPages() {
  //   console.log('nextPage');
  // }

  // function clickPage(page) {
  //   console.log('clickPage' + page);
  // }

  return (
    <>
      <div>
        {isLoading ? (
          <div>Loading...</div>
        ) : isError ? (
          <div>Error: {error.message}</div>
        ) : (
          <div className="flex h-full w-full flex-col items-center justify-start">
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
            {data?.songs?.map((song) => (
              <div className="fade-in-custom-15s mb-2 flex w-full flex-col" key={song.tjNum}>
                <div className="mb-1 flex h-14 w-full flex-row justify-between rounded-full bg-white opacity-40">
                  <div className="mx-2 flex w-4/12 flex-row items-center ">
                    <p className="font-nanum text-sm text-white lg:text-lg">
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{song.title}
                    </p>
                  </div>
                  <div className="flex w-4/12 flex-row items-center justify-center ">
                    <p className="font-nanum text-sm text-white lg:text-lg">{song.singer}</p>
                  </div>
                  <div className="flex w-1/12 flex-row items-center justify-center ">
                    <p className="font-nanum text-sm text-white lg:text-lg">{song.tjNum}</p>
                  </div>
                  <div className="mx-2 flex w-1/12 flex-row items-center justify-center ">
                    <button onClick={clickHeart}>
                      {song.isLike ? (
                        <HeartSolidIcon className="h-4 w-4 text-white lg:h-5 lg:w-5" />
                      ) : (
                        <HeartOutlineIcon className="h-4 w-4 text-white lg:h-5 lg:w-5" />
                      )}
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
        <span>Current Page: {page}</span>
        <button
          onClick={() => {
            if (page !== data.totalPageNumber) {
              setPage((old) => old + 1);
            }
          }}
          // Disable the Next Page button until we know a next page is available
          // disabled={isPreviousData || !data?.hasMore}
        >
          Next Page
        </button>
        {isFetching ? <span> Loading...</span> : null}{' '}
      </div>
      {/* <div className="flex h-full w-full flex-col items-center justify-start"> */}
      {/* <div className="fade-in-custom-20s mb-2 flex h-10 w-2/5 flex-row items-center justify-between lg:w-1/5">
          <button onClick={prevPages}>
            <ChevronLeftIcon className="h-6 w-6 text-white" />
          </button>
          <button onClick={() => setPage((old) => Math.max(old - 1, 1))} disabled={page === }>
           <ChevronLeftIcon className="h-6 w-6 text-white" />
          </button>
          <button
            onClick={() => clickPage(basePage)}
            className="font-nanum text-sm text-white lg:text-lg"
          >
            {basePage}
          </button>
          <button
            onClick={() => clickPage(basePage + 1)}
            className="font-nanum text-sm text-white lg:text-lg"
          >
            {basePage + 1}
          </button>
          <button
            onClick={() => clickPage(basePage + 2)}
            className="font-nanum text-sm text-white lg:text-lg"
          >
            {basePage + 2}
          </button>
          <button
            onClick={() => clickPage(basePage + 3)}
            className="font-nanum text-sm text-white lg:text-lg"
          >
            {basePage + 3}
          </button>
          <button
            onClick={() => clickPage(basePage + 4)}
            className="font-nanum text-sm text-white lg:text-lg"
          >
            {basePage + 4}
          </button>
          <button onClick={nextPages}>
            <ChevronRightIcon className="h-6 w-6 text-white" />
          </button>
        </div> */}
      {/* </div> */}
    </>
  );
}

export default Table;
