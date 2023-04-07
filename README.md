# Readme

# SSAFY 8기 A603 특화 프로젝트

# 프로젝트 이름: ToneMate

## 프로젝트 개요

1. ToneMate는 웹 페이지를 통해 사용자의 음색과 음역대를 분석하고, 이에 맞는 최적의 노래를 추천하는 프로젝트입니다. 
2. 이 프로젝트의 목적은 사용자가 자신과 가장 어울리는 가수와 노래를 추천받을 수 있도록 하는 것입니다.

## 프로젝트 구성원

- FE : **권영진(팀장)**, 윤준하
- BE / Data : 이상현, 박수민
- BE / AI : 류정민, 박정희

## 사용된 기술

- 펼쳐보기
    
    ### Front End
    
    - React 18.2.0
    - Next.js 13.2.4
    - Tanstack Query 4.28.0
    - Axios 1.3.4
    - Chart.js 4.2.1
    
    ### Back-end
    
    - Java openjdk-11
    - Python 3.10.9
    - Spring boot 2.7.5
    - Spring Security 5.7.4
    - Spring Data JPA 2.7.5
    - Querydsl 5.0.0
    - JWT 0.11.5
    - Spring Data Redis 2.7.5
    - Flask 2.2.3
    
    ### AI
    
    - scikit-learn 1.2.2
    - Keras 2.11.0
    - librosa 0.10.0
    - tensorflow 2.11.0
    
    ### Data
    
    - Hadoop-MapReduce 3.3.1
    - MRjob 0.7.4
    
    ### CI / CD
    
    - GitLab, Jenkins

## ERD

![Untitled](Readme%205e9f86ace2ea4fa5ac929ebfde99ed22/Untitled.png)

## 모델 구조

- v1
    
    ![Untitled](Readme%205e9f86ace2ea4fa5ac929ebfde99ed22/Untitled%201.png)
    
    ![Untitled](Readme%205e9f86ace2ea4fa5ac929ebfde99ed22/Untitled%202.png)
    

- v2
    
    ![Untitled](Readme%205e9f86ace2ea4fa5ac929ebfde99ed22/Untitled%203.png)
    
    ![Untitled](Readme%205e9f86ace2ea4fa5ac929ebfde99ed22/Untitled%204.png)
    
- 모델 학습 방법:
    - 최적화 알고리즘: Adam
    - 학습률(learning rate): 0.001
    - 손실 함수(loss function): categorical_crossentropy
    - 정확도 측정 지표(metrics): accuracy
    - 배치 크기(batch size): 32
    - 에폭(epoch): 60(v1), 40(v2)
    

## 결과

모델 시각화

- v1

![Untitled](Readme%205e9f86ace2ea4fa5ac929ebfde99ed22/Untitled%205.png)

- v2

![Untitled](Readme%205e9f86ace2ea4fa5ac929ebfde99ed22/Untitled%206.png)

## 기능

- 펼쳐보기
    
    
    | 분류 | 주기능 | 상세기능 | 설명 |
    | --- | --- | --- | --- |
    | 로그인 | 로그인 | - 카카오를 통해 소셜 로그인 | - Spring Security를 수정해서 사용함
    - Authorization Code Grant Type으로 소셜로그인 구현
    - 별도의 회원가입창이 없이, 회원 정보가 없으면 자동 회원가입 |
    | 로그아웃 | 로그아웃 | - 로그아웃 | - Spring Security를 수정해서 사용함
    - JWT Refresh Token을 Redis에서 삭제하고, Cookie를 삭제함 |
    | 마이페이지 | 회원 정보 조회 | - 회원 정보 조회 | - 사용자의 정보를 불러옴 |
    | 회원 정보 수정 | 회원 정보 수정 | - 닉네임, 프로필 사진 수정- S3를 연동해서 프로필 사진을 저장함 |  |
    | 닉네임 중복 확인 | 닉네임 중복 확인 | - 중복된 닉네임이 있는지 검사함 |  |
    | 노래 분석 | 음색 분석 | - 사용자의 음색을 분석 | - 사용자가 녹음한 음성 파일을 분석하여 노래 특성 정보 보여줌 |
    | 음역대 분석 | 사용자의 음역대를 분석 | - 사용자의 음계 테스트 분석을 통해 최저음, 최고음, 안정적인 음역대를 분석 |  |
    | 음역대 장르 분류 | 음역대 분석 결과에서 장르별 필터 | - 음역대 장르 분류에서 나온 음악정보들 중에서 장르별로 필터를 거치고 제공 |  |
    | 검사 결과 목록 | 지난 음색, 음역대 검사 결과들을 보여줌 | - 사용자의 음색 검사 결과를 통해 비슷한 음색의 가수를 보여주고 노래를 추천함
    - 사용자의 음역대 검사 결과를 통해 부르기 어려운 노래, 적당한 노래,   쉬운 노래로 분류해서 알려줌 |  |
    | 음색 검사 결과 조회 | 특정 음색 검사 결과 상세 | - 특정 음색 검사 결과 기록을 조회하여 상세 정보를 알려줌 |  |
    | 음역대 검사 결과 조회 | 특정 음역대 검사 결과 상세 | - 특정 음역대 검사 결과 기록을 조회하여 상세 정보를 알려줌 |  |
    | 음색 검사 결과 삭제 | 특정 음색 검사 결과 삭제 | - 특정 음색 검사 결과를 삭제함 |  |
    | 음역대 검사 결과 삭제 | 특정 음역대 검사 결과 삭제 | - 특정 음역대 검사 결과를 삭제함 |  |
    | 노래방 | 노래방 TOP 100 출력 | - TJ TOP 100 목록 조회 | - TJ 노래방 TOP 100을 보여줌- 페이지네이션 처리 |
    | 노래방 번호 출력 | TJ 노래방 전체 목록 조회 | - TJ 노래방 전체 목록을 보여줌
    - 페이지네이션 처리 |  |
    | 노래 검색 | TJ 노래 목록을 가수, 제목, 번호로 검색 | - TJ 노래방에 있는 노래들을 가수, 제목, 번호로 검색함
    - 페이지네이션 처리
    - 가수, 제목, 번호를 인덱싱 처리 |  |
    | 애창곡 | 애창곡 추가 | 애창곡 추가 | - 노래방 페이지에서 애창곡을 추가함 |
    | 애창곡 삭제 | 애창곡 삭제 | - 노래방 페이지 혹은 애창곡 목록에서 애창곡을 삭제함 |  |
    | 애창곡 조회 | 애창곡 목록 조회 | - 애창곡 목록에서 유저의 애창곡 목록을 조회함 |  |

## REST API

- 펼쳐보기
    
    
    | 기능 구분 | HTTP Method | URI | Request Param | Request Body | Response | 설명 | Response Body |
    | --- | --- | --- | --- | --- | --- | --- | --- |
    |  |  |  |  |  |  |  |  |
    | 카카오 소셜 로그인 | GET | /oauth/login/kakao | code : String,
    state : String |  |  | - 카카오 로그인 |  |
    | 로그아웃 | POST | /logout |  |  |  | - 로그아웃 처리 |  |
    |  |  |  |  |  |  |  |  |
    | 닉네임 중복 확인 | GET | /duplicate | nickname : String |  | suceess : 중복된 닉네임이 없음
    fail : 중복된 닉네임이 있음 | - 중복된 닉네임이 있는지 검사 |  |
    |  |  |  |  |  |  |  |  |
    | 회원 정보 조회 | GET | /users |  |  | {    nickname : String,    profileImg: String} | - 사용자의 정보 불러오기 | 사용자 닉네임, 사용자 프로필 경로 |
    | 회원 정보 수정 | PUT | /users |  | multipartFile : MultipartFilenickname : String |  | - 닉네임 수정 (중복 확인 필요)- 프로필 사진 수정 |  |
    |  |  |  |  |  |  |  |  |
    | 음색 분석 | POST | /music/timbre |  | fileWav : MultipartFile | {    timbreId: long,    mfccMean: float,    stftMean: float,    zcrMean: float,    spcMean: float,    sprMean: float,    rmsMean: float,    mfccVar: float,    stftVar: float,    zcrVar: float,    spcVar: float,    sprVar: float,    rmsVar: float,    time: Datetime,    singerDetails: [{            name: String,            similarityPercent: float,            songs: [{                    mfccMean: float,                    stftMean: float,                    zcrMean: float,                    spcMean: float,                    sprMean: float,                    rmsMean: float,                    mfccVar: float,                    stftVar: float,                    zcrVar: float,                    spcVar: float,                    sprVar: float,                    rmsVar: float,                    title: String                }]        }]} | - 사용자가 녹음한 음성 파일을 분석하여   노래 특성 정보 보여줌
    - 음색이 비슷한 가수들(5명)의 유사도와   대표곡들(가수별로 5곡씩) 노래 특성 정보 보여줌 | 특성, 검사일시, 음색이 비슷한 가수들의 정보(id, 이름, 유사도, 대표곡) |
    | 음역대 분석 | POST | /music/pitch |  | lowOctave : MultipartFilehighOctave : MultipartFile | {    pitchId: long,    lowOctave: String,    highOctave: String,    time: Datetime,    possibleSong: [Song],    normalSong: [Song],    impossibleSong: [Song]} | - 사용자가 녹음한 음성 파일을 분석하여  음역대를 보여줌
    - 사용자의 최저음, 최고음 기반으로   음역대에 맞는 노래들을 보여줌 - possibleSong : 잘 부를 수 있는 노래 기준
    - normalSong : 적당히 부를 수 있는 노래
    - impossibleSong : 부르기 힘든 노래
    - 세종류 모두 3개까지 음악을 추천하고 다수의 음악이  있다면 음역대의 중앙값과 가장 유사한 음악기준 3개  를 표현함- Song형태는 다음과 같음:
    {    mfccMean: float,    stftMean: float,    zcrMean: float,    spcMean: float,    sprMean: float,    rmsMean: float,    mfccVar: float,    stftVar: float,    zcrVar: float,    spcVar: float,    sprVar: float,    rmsVar: float,    lowOctave: String,    highOctave: String,    title: String} | 사용자의 최고음/최저음, 음역대에 따른 노래 잘부름, 적당, 못부름 그룹으로 분류하고음역대 중간값과의 차이를 기준으로 해서 그룹별 3개 이하의노래 및 정보 |
    | 음역대 장르분류 | GET | /music/{pitchId}/{genre} |  |  | {    possibleSong: [Song],    normalSong: [Song],    impossibleSong: [Song]} | - pitchId, lowOctave, highOctave, time은  null로 고정
    -PossibleSong, normalSong, impossibleSong은  기록된 음역대 기준으로 필터링된 Song들 중에서   선택한 장르에 해당하는 노래를 리턴한다.  (발라드, 락)
    - genre는 BALLADE, ROCK 두개만 지원
    - Song형태는 다음과 같음:
    {    mfccMean: float,    stftMean: float,    zcrMean: float,    spcMean: float,    sprMean: float,    rmsMean: float,    mfccVar: float,    stftVar: float,    zcrVar: float,    spcVar: float,    sprVar: float,    rmsVar: float,    lowOctave: String,    highOctave: String,    title: String,    singer: String} | 장르로 필터한 노래들을 그룹화 하여 정보 제공 |
    | 검사 결과 목록 | GET | /music/result |  |  | {    pitch: [{        pitchId: long,        time: Datetime,        lowOctave: String,        highOctave: String        }],    timbre: [{        timbreId: long,        time: Datetime,        singer: [String]        }]} | - 사용자의 음색/음역대 검사 결과 목록을 보여줌- 최신 결과부터 보내줌- singer 목록은 5개만 보내줌 | 검사아이디, 검사유형(음색 or 음역대), 검사일시 |
    | 음색 검사 결과 조회 | GET | /music/result/timbre/{timbreId} |  |  | {    timbreId: long,    mfccMean: float,    stftMean: float,    zcrMean: float,    spcMean: float,    sprMean: float,    rmsMean: float,    mfccVar: float,    stftVar: float,    zcrVar: float,    spcVar: float,    sprVar: float,    rmsVar: float,    time: Datetime,    singerDetails: [{            name: String,            similarityPercent: float,            songs: [{                    mfccMean: float,                    stftMean: float,                    zcrMean: float,                    spcMean: float,                    sprMean: float,                    rmsMean: float,                    mfccVar: float,                    stftVar: float,                    zcrVar: float,                    spcVar: float,                    sprVar: float,                    rmsVar: float,                    title: String                }]        }]} | - 사용자의 음색 검사 결과 상세 정보를 보여줌 | 특성, 검사일시, 음색이 비슷한 가수들의 정보(id, 이름, 유사도, 대표곡) |
    |  | GET |  |  |  | {    pitchId: long,    lowOctave: String,    highOctave: String,    time: Datetime,    possibleSong: [{Song}],    normalSong: [{Song}],    impossibleSong: [{Song}]} | - 사용자의 음역대 검사 결과 상세 정보를 보여줌
    - Song은 다음과 같음{    mfccMean: float,    stftMean: float,    zcrMean: float,    spcMean: float,    sprMean: float,    rmsMean: float,    mfccVar: float,    stftVar: float,    zcrVar: float,    spcVar: float,    sprVar: float,    rmsVar: float,    lowOctave: String,    highOctave: String,    title: String,    singer: String} | - pitch검사 id
    - 낮은 음역대와 높은 음역대
    - 검사 일시
    - 부르기 쉬운, 적절한, 어려운 곡 리스트 |
    | 음역대 검사 결과 조회 | DELETE | /music/result/pitch/{pitchId} |  |  |  | - 사용자가 선택한 음색 검사 결과를 삭제함 |  |
    | 음역대 검사 결과 삭제 | DELETE | /music/result/pitch/{pitchId} |  |  |  | - 사용자가 선택한 음역대 검사 결과를 삭제함 |  |
    |  |  |  |  |  |  |  |  |
    | 노래방 TOP 100 출력 | GET | /karaoke/top | page: int |  | {    songs: [{            tjNum: int,            singer: String,            title: String,            isLike: boolean        }],    pageSize: int,    totalPageNumber: int,    totalCount: long} | - 노래방 top 100에 해당하는 tj의 노래들을 출력 | - top100 노래들
    - pageSize로 한 페이지에 몇개의 노래가 있는지 알려줌(10개 고정)
    - totalPageNumber로 몇 페이지까지 존재하는지 알려줌
    - totalCount로 총 몇곡이 있는지 알려줌(top 100이기 때문에 100 고정) |
    | 노래방 번호 출력 | GET | /karaoke/songs | page: int |  | {    songs: [{            tjNum: int,            singer: String,            title: String,            isLike: boolean        }],    pageSize: int,    totalPageNumber: int,    totalCount: long} | - 노래방 책에 등록되어있는 tj의 노래들을 출력 | - 모든 노래들, 우리 db의 id 오름차순으로 보냄- pageSize로 한 페이지에 몇개의 노래가 있는지 알려줌(10개 고정)
    - totalPageNumber로 몇 페이지까지 존재하는지 알려줌
    - totalCount로 총 몇곡이 있는지 알려줌 |
    | 노래 검색 | GET | /karaoke/search | singer: String,
    title: String,
    tjNum: int,
    page: int |  | {    songs: [{            tjNum: int,            singer: String,            title: String,            isLike: boolean        }],    pageSize: int,    totalPageNumber: int,    totalCount: long} | - 검색 결과에 따른 tj의 노래들을 출력 | - 검색 결과에 맞는 노래들
    - 띄어쓰기 유무 상관없이 검색함
    - pageSize로 한 페이지에 몇개의 노래가 있는지 알려줌(10개 고정)
    - totalPageNumber로 몇 페이지까지 존재하는지 알려줌
    - totalCount로 총 몇곡이 있는지 알려줌 |
    |  |  |  |  |  |  |  |  |
    | 애창곡 추가 | POST | /likes/{tjNum} |  |  |  | - tj 번호와 맞는 애창곡 삭제 |  |
    | 애창곡 삭제 | DELETE | /likes/{tjNum} |  |  |  | - tj 번호와 맞는 애창곡 추가 |  |
    | 애창곡 조회 | GET | /likes | page: int |  | {    songs: [{            tjNum: int,            singer: String,            title: String        }],    pageSize: int,    totalPageNumber: int,    totalCount: long} | - 유저의 애창곡 목록 조회 |  |

## 웹 페이지 설명

## 서비스 아키텍처

![Untitled](Readme%205e9f86ace2ea4fa5ac929ebfde99ed22/Untitled%207.png)

## 데이터셋

- AI hub - 보컬파일
- 한국 가수 100명의 노래 약 5000곡

## 사용 방법

## 참고 문헌

- 하둡 완벽 가이드 - 톰 화이트
- 자바 ORM 표준 JPA 프로그래밍 - 김영한
- React를 다루는 기술 - 김민준
- Spring Security, Hadoop MapReduce, Next.js, React, tailwindCSS - 공식 문서