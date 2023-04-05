import NavButton from '../common/nav-button';

import {
  BeakerIcon,
  HomeIcon,
  ChartBarIcon,
  MusicalNoteIcon,
  SwatchIcon,
  HeartIcon,
} from '@heroicons/react/24/solid';

export default function NavMenu() {
  return (
    <div className="flex flex-col items-start justify-start sm:w-11/12">
      <NavButton icon={HomeIcon} name="홈 화면" value="/home" />
      <NavButton icon={SwatchIcon} name="음색 검사" value="/inspectation/vocal-color" />
      <NavButton icon={MusicalNoteIcon} name="음역대 검사" value="/inspectation/vocal-range" />
      <NavButton icon={ChartBarIcon} name="검사 결과" value="/inspectation/result-list" />
      <NavButton icon={MusicalNoteIcon} name="노래 검색" value="/search" />
      <NavButton icon={HeartIcon} name="애창곡 리스트" value="/user/music-list" />
      <NavButton icon={BeakerIcon} name="서비스 소개" value="/about" />
    </div>
  );
}
