# **나만의 작은 텃밭**

</br>

가정용 텃밭 관리 도우미 (모바일 중심 서비스) - 나만의 작은 텃밭

![실시간 인기 작물 이미지](https://github.com/ayj8655/Readme_Images/blob/main/najacbat/%EB%A9%94%EC%9D%B8%EC%9D%B4%EB%AF%B8%EC%A7%80.png?raw=true)


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

![기획배경](https://github.com/ayj8655/Readme_Images/blob/main/najacbat/%EA%B8%B0%ED%9A%8D%EB%B0%B0%EA%B2%BD.png?raw=true)

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

![와이어프레임](https://github.com/ayj8655/Readme_Images/blob/main/najacbat/wireframe.png?raw=true)
<br>

DB Table

![ERD](https://github.com/ayj8655/Readme_Images/blob/main/najacbat/db.png?raw=true)
<br>


JIRA

![week4](https://github.com/ayj8655/Readme_Images/blob/main/najacbat/%EC%A7%80%EB%9D%BC4%EC%A3%BC.png?raw=true)

![week5](https://github.com/ayj8655/Readme_Images/blob/main/najacbat/%EC%A7%80%EB%9D%BC5%EC%A3%BC.png?raw=true)

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
