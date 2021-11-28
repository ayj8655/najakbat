# **나만의 작은 텃밭**

![https://img.shields.io/github/license/:user/:repo.svg](https://img.shields.io/github/license/:user/:repo.svg)

![https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)

</br>

가정용 텃밭 관리 도우미 (모바일 중심 서비스) - 나만의 작은 텃밭

![실시간 인기 작물 이미지](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/b851e416-008a-4f00-aac0-23bcf118ed02/Untitled-removebg-preview.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20211128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211128T092810Z&X-Amz-Expires=86400&X-Amz-Signature=91c9f7046b010f2259e387c0855c8a66b7c0d389353754f9a93a36625414dbe8&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled-removebg-preview.png%22&x-id=GetObject)


---
## **목차**

- 팀원소개
- 프로젝트 설명
- 기술스택
- 사용된 API
- License

</br>

---

## **팀원소개**

|  | 안영진 | 이용직 | 이상현 | 신기하 | 박준호 | 이수정 |
| --- | --- | --- | --- | --- | --- | --- |
| 역할 | 백엔드,안드로이드 | 백엔드 | 백엔드 | 프론트엔드, 안드로이드 | 프론트엔드 | 팀장, 프론트엔드 |
| 담당내용 | 회원관리 기능 개발, 안드로이드 앱 및 알림 개발, CI/CD | 커뮤니티 중심개발, 내 농작물 관리 기능 개발 | 농작물 도감 중심 개발, 업적 기능 개발  | 프로필/로그인/회원가입, 안드로이드 알림 기능 | 쪽지,1:1문의 화면, 알림 및 설정 기능 | 농작물 조회 및 관리 화면, 커뮤니티 기능 |

</br>

---
## **프로젝트 설명**

UCC : [https://youtu.be/V2gcpwDS10c](https://youtu.be/V2gcpwDS10c)


## 기획 배경

![기획배경](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/1d921a6c-6a18-4333-9ab7-c05dfb8cfdbf/image-removebg-preview.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20211128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211128T111842Z&X-Amz-Expires=86400&X-Amz-Signature=8b7b3091313a85279169bf465c803eb505e8a473ab4866c6aff4af77f796a680&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22image-removebg-preview.png%22&x-id=GetObject)

이로인한 식재료 구매 지출을 줄이기 위한 텃밭 문화의 생성


문제점
- 부족한 농작물 재배 지식
- 불편한 텃밭 관리
- 찾기 힘든 커뮤니티


</br>

주요기능

- 인기 작물 추천, 월별 작물 추천
- 농작물 상세 정보 및 레시피 제공
- 농작물 가격 및 상태 정보 통계 제공
- 농작물 물주기 알림, 작물 추천 알림, 커뮤니티 활동 알림, 쪽지 메시지 알림
- 다양한 커뮤니티 (자유, 정보, 질문, 나눔)
- 인기 게시글 추천, 사진 업로드

</br>

와이어프레임

![와이어프레임](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/2a90c0f5-14fc-47fc-9dcc-2347a5216869/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20211128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211128T100246Z&X-Amz-Expires=86400&X-Amz-Signature=f10d1da86daa1de87e54810173a7e373ec0ef3382127098ee8d827d90ded5c32&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
<br>

ERD

![ERD](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/3ee3b302-5d91-43c4-a881-a3c2a174e8e9/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20211128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211128T100503Z&X-Amz-Expires=86400&X-Amz-Signature=f3042c5b445953540f03242f9386463a586ed57e8f06479478bca1db7237d9e2&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
<br>


JIRA

![week4](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/b341d7cf-cf24-49d3-aeda-40b86ca583c6/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20211128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211128T100955Z&X-Amz-Expires=86400&X-Amz-Signature=fc2eafaac101022cfa8a5905e06a0e0e18d868db5540c8e2202244bdc7352349&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

![week5](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/6a37e6ef-514b-441d-a857-b59e94e92279/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20211128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211128T101102Z&X-Amz-Expires=86400&X-Amz-Signature=dfc5c3c8a9ce6a5278e91a5c6e7d0787466a30fbdb9e280856b58ef7f49750db&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

---
## **기술 스택**

```
FE : Vue.js, Andriod
BE : Springboot, mysql
INFRA : Nginx, Jenkins, Docker, amazon EC2, amazon S3
```

</br>

---
## **API**


[모바일 앱 알림](https://firebase.google.com/products/cloud-messaging?hl=ko)

[핸드폰 메시지 전송](https://developer.coolsms.co.kr/developer)

[기상청 단계예보 조회 서비스](https://www.data.go.kr/data/15084084/openapi.do)

[농작물 가격](https://www.kamis.or.kr/customer/reference/openapi_list.do?action=detail&boardno=2)

</br>

---


Note: The `license` badge image link at the top of this file should be updated with the correct `:user` and `:repo`.

</br>


## **License**

[MIT © Richard McRichface.](https://www.notion.so/LICENSE)
