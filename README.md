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

![ERD](pic/Untitled.png)

## 모델 구조

<details>
  <summary>v1</summary>

  ![v1_summary](pic/Untitled 1.png)
  ![v1](pic/Untitled 2.png)
</details>
    

<details>
  <summary>v2</summary>

  ![v2_summary](pic/Untitled 3.png)
  ![v2](pic/Untitled 4.png)
</details>

    
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

![v1_시각화](pic/Untitled 5.png)

- v2

![v2_시각화](pic/Untitled 6.png)

## 기능
![기능](pic/table1.png)
![기능](pic/table2.png)

## REST API
![기능](pic/table3.png)
![기능](pic/table4.png)
![기능](pic/table5.png)
![기능](pic/table6.png)
![기능](pic/table7.png)
![기능](pic/table8.png)

## 웹 페이지 설명

## 서비스 아키텍처

![아키텍쳐](pic/Untitled 7.png)

## 데이터셋

- AI hub - 보컬파일
- 한국 가수 100명의 노래 약 5000곡

## 사용 방법
![실행 방법] (exec/README.md))

## 참고 문헌

- 하둡 완벽 가이드 - 톰 화이트
- 자바 ORM 표준 JPA 프로그래밍 - 김영한
- React를 다루는 기술 - 김민준
- Spring Security, Hadoop MapReduce, Next.js, React, tailwindCSS - 공식 문서
