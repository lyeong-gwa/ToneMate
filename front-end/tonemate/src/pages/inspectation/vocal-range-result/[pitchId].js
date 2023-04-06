import Head from 'next/head';
import { useRouter } from 'next/router';
import { useQuery } from '@tanstack/react-query';

import { axios } from '@/lib/axios';
import { useUser } from '@/features/auth';
import { LoadingFallback } from '@/components/Fallbacks';
import Layout from '@/components/layout';
import TitleContainer from '@/components/content/title-container';
import MainContainer from '@/components/content/main-container';

export default function VoiceRangeResult() {
  const router = useRouter();
  const { pitchId } = router.query;

  const getResultPitch = ({ pitchId }) => {
    return axios.get(`/music/result/pitch/${pitchId}`);
  };

  const useResultPitch = ({ pitchId, config }) => {
    return useQuery({
      ...config,
      queryKey: ['pitch', pitchId],
      queryFn: () => getResultPitch({ pitchId }),
    });
  };
  const resultPitchQuery = useResultPitch({ pitchId });

  const result = resultPitchQuery.data;
  console.log(result);

  const { user, isUserLoading } = useUser({ redirectTo: '/', redirectIfFound: false });
  if (isUserLoading || !user || resultPitchQuery.isLoading) {
    return <LoadingFallback />;
  }

  return (
    <>
      <Head>
        <title>음역대검사결과</title>
        <meta name="description" content="Generated by create next app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <main>
        <Layout>
          <TitleContainer>
            <p className="text-xl text-white lg:text-4xl">음역대 검사 결과</p>
          </TitleContainer>
          <MainContainer>
            <div className="flex h-full w-full flex-col justify-between lg:flex-row ">
              <div className="flex h-2/5 w-full flex-col justify-between lg:h-full lg:w-5/12">
                {/*  */}
                <div className="flex w-full flex-col">
                  <p className="text-md mx-2 text-center font-nanum text-white lg:text-left lg:text-2xl">
                    {user.nickname}님의 {result.time}일
                  </p>
                  <p className="text-md mx-2 text-center font-nanum text-white lg:text-left lg:text-2xl">
                    음역대 검사 결과입니다.
                  </p>
                </div>
                {/* 유저 검사 결과 : 최저, 최고음 */}
                <div className="flex h-2/5 w-full flex-col rounded-xl border border-white">
                  <div>
                    <p className="text-md mx-2 text-center font-nanum text-white lg:text-left lg:text-2xl">
                      해당 음역대는 한국식 표기법을 따릅니다.
                    </p>
                  </div>
                  <div>
                    <p className="text-md mx-2 text-center font-nanum text-white lg:text-left lg:text-2xl">
                      최고음 : {result?.highOctave}
                    </p>
                  </div>
                  <div>
                    <p className="text-md mx-2 text-center font-nanum text-white lg:text-left lg:text-2xl">
                      최저음 : {result?.lowOctave}
                    </p>
                  </div>
                </div>
                {/* 유저 검사 결과 : 차트 */}
                <div className="flex h-2/5 w-full flex-row rounded-xl border border-white"></div>
              </div>

              {/* 추천곡 */}
              <div className="flex h-3/6 w-full flex-col justify-between lg:h-full lg:w-6/12">
                {/* 잘 부를 수 있는 노래 */}
                <div className="flex h-3/10 w-full flex-col rounded-xl border border-white">
                  {/* 텍스트 */}
                  <div className="mx-2 mt-2 flex flex-row">
                    <p className="mx-2 text-center font-nanum text-2xl text-white lg:text-start lg:text-2xl">
                      # 잘 부를 수 있는 노래
                    </p>
                  </div>
                  {/* 노래 3곡 */}
                  <div className="flex grow flex-col items-center justify-around lg:flex-row">
                    <div className="flex h-5/6 w-full flex-col bg-white lg:w-1/4"></div>
                    <div className="flex h-5/6 w-full flex-col bg-white lg:w-1/4"></div>
                    <div className="flex h-5/6 w-full flex-col bg-white lg:w-1/4"></div>
                  </div>
                </div>

                {/* 적당히 부를 수 있는 노래 */}
                <div className="flex h-3/10 w-full flex-col rounded-xl border border-white">
                  {/* 텍스트 */}
                  <div className="mx-2 mt-2 flex flex-row">
                    <p className="mx-2 text-center font-nanum text-2xl text-white lg:text-start lg:text-2xl">
                      # 적당한 노래
                    </p>
                  </div>
                  {/* 노래 3곡 */}
                  <div className="flex grow flex-col items-center justify-around lg:flex-row">
                    <div className="flex h-5/6 w-full flex-col bg-white lg:w-1/4"></div>
                    <div className="flex h-5/6 w-full flex-col bg-white lg:w-1/4"></div>
                    <div className="flex h-5/6 w-full flex-col bg-white lg:w-1/4"></div>
                  </div>
                </div>

                {/* 부르면 안되는 노래 */}
                <div className="flex h-3/10 w-full flex-col rounded-xl border border-white">
                  {/* 텍스트 */}
                  <div className="mx-2 mt-2 flex flex-row">
                    <p className="mx-2 text-center font-nanum text-2xl text-white lg:text-start lg:text-2xl">
                      # 부르면 안되는 노래
                    </p>
                  </div>
                  {/* 노래 3곡 */}
                  <div className="flex grow flex-col items-center justify-around lg:flex-row">
                    <div className="flex h-5/6 w-full flex-col bg-white lg:w-1/4"></div>
                    <div className="flex h-5/6 w-full flex-col bg-white lg:w-1/4"></div>
                    <div className="flex h-5/6 w-full flex-col bg-white lg:w-1/4"></div>
                  </div>
                </div>
              </div>
            </div>
          </MainContainer>
        </Layout>
      </main>
    </>
  );
}
