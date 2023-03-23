import { BeakerIcon } from "@heroicons/react/24/solid";

export default function NavMenu() {
  //
  function ClickNavItems(link) {
    console.log(link);
  }

  return (
    <>
      <div className="block ">
        {/* 음색 검사 Navigation Botton */}
        <div>
          <div
            className="flex content-around justify-around my-4"
            onClick={ClickNavItems}
          >
            <div className="inline-block w=1/10">
              <BeakerIcon className="block w-6 h-6 text-cyan-500" />
            </div>
            <div className="block w-4/5 text-white">음색 검사</div>
          </div>
        </div>
        {/* 음역대 검사 Navigation Botton */}
        <div>
          <div
            className="flex content-around justify-around my-4"
            onClick={ClickNavItems}
          >
            <div>
              <BeakerIcon className="block w-6 h-6 text-cyan-500" />
            </div>
            <div className="block w-4/5 text-white">음역대 검사</div>
          </div>
        </div>
        {/* 검사 결과 Navigation Botton */}
        <div>
          <div
            className="flex content-around justify-around my-4"
            onClick={ClickNavItems("/inspectation/vocal-rangev")}
          >
            <div>
              <BeakerIcon className="block w-6 h-6 text-cyan-500" />
            </div>
            <div className="block w-4/5 text-white">검사 결과</div>
          </div>
        </div>
        {/* 랭킹 Navigation Botton */}
        <div>
          <div
            className="flex content-around justify-around my-4"
            onClick={ClickNavItems("/ranking")}
          >
            <div>
              <BeakerIcon className="block w-6 h-6 text-cyan-500" />
            </div>
            <div className="block w-4/5 text-white">랭킹</div>
          </div>
        </div>
        {/* 노래 검색 Navigation Botton */}
        <div>
          <div
            className="flex content-around justify-around my-4"
            onClick={ClickNavItems("/search")}
          >
            <div>
              <BeakerIcon className="block w-6 h-6 text-cyan-500" />
            </div>
            <div className="block w-4/5 text-white">노래검색</div>
          </div>
        </div>
        {/* 서비스 소개 Navigation Botton */}
        <div>
          <div
            className="flex content-around justify-around my-4"
            onClick={ClickNavItems("/about")}
          >
            <div>
              <BeakerIcon className="block w-6 h-6 text-cyan-500" />
            </div>
            <div className="block w-4/5 text-white">서비스 소개</div>
          </div>
        </div>
      </div>
    </>
  );
}

{
  /* <ul>
  <li>
    <Link href={`/inspectation/vocal-color`}>음색 검사</Link>
  </li>
  <li>
    <Link href={`/inspectation/vocal-range`}>음역대 검사</Link>
  </li>
  <li>
    <Link href={`/inspectation/vocal-range`}>검사 결과</Link>
  </li>
  <li>
    <Link href={`/ranking`}>랭킹</Link>
  </li>
  <li>
    <Link href={`/search`}>노래 검색</Link>
  </li>
  <li>
    <Link href={`/about`}>서비스 소개</Link>
  </li>
</ul>; */
}
