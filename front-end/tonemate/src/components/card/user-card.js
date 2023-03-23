import Image from "next/image";
import Link from "next/link";

export default function UserCard() {
  let user = {
    nickName: "이상현",
    profile:
      "https://mblogthumb-phinf.pstatic.net/MjAyMDExMDFfMyAg/MDAxNjA0MjI5NDA4NDMy.5zGHwAo_UtaQFX8Hd7zrDi1WiV5KrDsPHcRzu3e6b8Eg.IlkR3QN__c3o7Qe9z5_xYyCyr2vcx7L_W1arNFgwAJwg.JPEG.gambasg/%EC%9C%A0%ED%8A%9C%EB%B8%8C_%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84_%ED%8C%8C%EC%8A%A4%ED%85%94.jpg?type=w800",
  };
  return (
    <>
      <div className="flex border-white border-2 rounded-lg m-3">
        <div className="w-2/5 m-2">
          <img
            src={user.profile}
            alt={user.nickName}
            className="rounded-full"
          />
        </div>
        <div className="flex justify-center flex-col w-3/5">
          <div className="text-white">안녕하세요</div>
          <div className="text-white">{user.nickName}님</div>
          <div className="text-white">{user.nickName} 바보 멍청이</div>
        </div>
      </div>
    </>
  );
}
