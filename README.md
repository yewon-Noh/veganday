# 비건데이

담당 Skills & 사용 라이브러리: JPA, MySQL, Naver Search API, Postman, SpringBoot <br/>
진행기간: 2022년 8월 12일 → 2022년 8월 26일 <br/>
팀 구성: 백엔드 2명, 프로트엔드 2명 <br/>
한 줄 소개: 비건정보 공유, 비건제품 쇼핑몰, 비건카페·식당 지도서비스, 식품성분 분석서비스 등을 포함하는 비건 소통(커뮤니티) 플랫폼 <br/>


### Link
**구현 영상**

[소개딩_InSecure_비건데이](https://www.youtube.com/shorts/vZUcYQkEP-g)

**발표 영상**

[SW개발보안 경진대회 수상작 발표](https://www.youtube.com/watch?v=t24MmM7b1v8)

**Front Source**

[RN_소스](https://github.com/SeongHo-C/Vegan-Day)

### 상세내용
![비건데이_2](https://user-images.githubusercontent.com/80824750/194245509-a2485cf7-27b1-4f7b-b995-fe4597d97abf.png)

🌿 **제9회 SW개발보안 경진대회(소개딩 시즌4)** 출전을 위해 진행한 프로젝트입니다. 
비건데이는 비건을 위한 **비건 소통 플랫폼**입니다. 
성장하는 비거니즘 시장을 겨냥하여 질 높은 비거니즘 서비스를 제공하기 위해 시작하였습니다. 
비건정보 공유를 위한 커뮤니티, 비건제품 구매를 위한 비건 쇼핑몰, 비건카페 · 식당 지도서비스 및 OCR을 이용하여 쉽고 빠른 식품성분 분석 기능을 제공합니다.


### 사용 기술 및 라이브러리

- SpringBoot, Java, JPA
- MySQL
- Naver Search API, Gmail SMTP Server
- Ubuntu 18.0, NCP
- IntelliJ, DBeaver, MobaXterm, Postman

### 담당한 기능 (BackEnd Server)

- **DB** 설계
- 프로젝트 초기 세팅
- **이미지 File Upload** 및  Resource Handler 구현
- **메인화면** API
- **커뮤니티** API
    - 글 작성, 글 목록, 글 상세, 댓글 작성
- **Naver Search API**를 활용하여 쇼핑몰 API
- Gmail SMTP Server를 이용하여 **메일 전송** API

### 깨달은 점

- 클라이언트로 **REST API 응답**을 보내기 위해 직접 `상태코드, 메시지, 데이터`를 담은 클래스를 생성해 사용하였으며, 이를 통해 클라이언트와 원활한 소통을 할 수 있었다.
- **File** 처리를 위해 `MultipartHttpServletRequest` 와 `MultipartFile` 를 사용하였다.
- **공통 기능**을 Service로 구현하여 코드의 중복을 피하고 재사용성을 높일 수 있도록 하였다.
- **Naver Search API** 를 이용해 쇼핑몰 검색 정보를 제공하였다.
- `@Data` 사용 시 **보안의 취약점**이 있어, 이를 예방하기 위해 `@Getter` 및 `@Builder 패턴`을 사용하였다. **Builder 패턴**을 이용함으로써 필요한 데이터만 설정하고, 가독성을 높일 수 있었다.
- 독립적 실행이 가능한 Jar 파일을 빌드하여 ubuntu 환경에서 **서버를 배포**하였다.
