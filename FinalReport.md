/*
2024년 Cloud Native 교육 4차 - KTDS 재무DX개발팀 이재경
*/

# 예제 - 임직원 휴양소 예약 서비스

## 1. 클라우드 네이티브 아키텍처
* 클라우드 아키텍처 설계




## 2. 클라우드 네이티브 모델링
* 시나리오
  1) 직원이 휴양소를 선택하여 예약한다.
  2) 직원이 예약하면 포인트가 차감된다.
  3) 예약이 되고 포인트 차감이 성공하면 예약 내역이 호텔 관리인에게 전달된다.
  4) 호텔에 객실이 있으면 예약이 확정된다.(카톡 알림이 발송)
  5) 직원이 예약을 취소할 수 있다.
  6) 예약이 취소되면 예약 확정이 취소된다.
  7) 직원은 예약 내역을 조회할 수 있다.

   
* 이벤트 스토밍


![image](https://github.com/user-attachments/assets/6cbede3f-50ff-4d0b-b6db-66122b3d5934)




## 3. 클라우드 네이티브 개발
1) Saga

kafka설치 후 호출 및 모니터링 할 수 있다.
![image](https://github.com/user-attachments/assets/a93908c8-0cfe-4802-ada6-ea71a8e6523f)

![image](https://github.com/user-attachments/assets/6a1e3d6c-222c-4c53-87d7-5d446ebe387e)

![image](https://github.com/user-attachments/assets/e811b682-0073-49b1-9d2c-3ff72bf73202)

![image](https://github.com/user-attachments/assets/a53b22c1-6fa5-4f2b-ae69-322aa6de127b)



booking(예약요청) -> point(포인트차감) 은 동기식,
포인트 차감 후 hotel(예약확정) 요청 및 booking(상태변경)은 비동기식이다.

따라서, hotel 서비스가 중지되어있어도 booking은 정상 작동한다.

![image](https://github.com/user-attachments/assets/86b23a91-41fe-4e16-9363-7fd500e979a4)

![image](https://github.com/user-attachments/assets/c10f2264-f3d9-43e8-be7d-af6289497431)


그리고 후에 hotel이 실행되면 kafka에 저장된 이벤트를 수행하고 결과를 전달한다.



3) Compensation


4) Gateway

같은 IP, Port로 각 서비스를 접근한다.

![image](https://github.com/user-attachments/assets/6619c5a8-d4cf-433a-a894-19a3dcd6f074)

![image](https://github.com/user-attachments/assets/39f55a7f-a6f6-4d09-8800-b0ead3025cfb)



6) CQRS

예약을 요청하면 booking 테이블과 동시에 mypage에도 입력된다.(비동기)

![image](https://github.com/user-attachments/assets/4d09801d-9be0-4633-8877-9df8ff3fb3c1)

![image](https://github.com/user-attachments/assets/cd4e9e50-f3a8-4c24-acdf-33288adbd224)


booking 서비스가 종료되어도 mypage는 조회 가능하다.

![image](https://github.com/user-attachments/assets/9f6a4caa-41c0-4143-8b7f-555a123ef4be)




## 4. 클라우드 네이티브 운영
1) CI/CD - Azure
