/*
2024년 Cloud Native 교육 4차 - KTDS 재무DX개발팀 이재경
*/

# <예제 - 임직원 휴양소 예약 서비스>

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



2) Compensation

   


3) Gateway

  같은 IP, Port로 각 서비스를 접근한다.

  ![image](https://github.com/user-attachments/assets/6619c5a8-d4cf-433a-a894-19a3dcd6f074)

  ![image](https://github.com/user-attachments/assets/39f55a7f-a6f6-4d09-8800-b0ead3025cfb)



4) CQRS

  예약을 요청하면 booking 테이블과 동시에 mypage에도 입력된다.(비동기)

  ![image](https://github.com/user-attachments/assets/4d09801d-9be0-4633-8877-9df8ff3fb3c1)

  ![image](https://github.com/user-attachments/assets/cd4e9e50-f3a8-4c24-acdf-33288adbd224)


  booking 서비스가 종료되어도 mypage는 조회 가능하다.

  ![image](https://github.com/user-attachments/assets/9f6a4caa-41c0-4143-8b7f-555a123ef4be)


  이벤트 발생시 생성/갱신되는 소스 구현은 아래와 같다.

  ![image](https://github.com/user-attachments/assets/5506ce5e-3517-4650-95d9-ac1c2c3efa95)

  ![image](https://github.com/user-attachments/assets/d6d82e57-c38e-4348-93fc-24cd10636d01)



## 4. 클라우드 네이티브 운영
1) CI/CD - Azure DevOps
   
  AKS를 생성한다.
  
  ![image](https://github.com/user-attachments/assets/5ca208d3-697c-4a21-a07b-3eda51043081)

  

* CI 세팅

  ![image](https://github.com/user-attachments/assets/864a84c1-3d05-4954-a36e-9010fca8fae0)

  ![image](https://github.com/user-attachments/assets/343f781d-b969-4a22-995c-fe874409e8d7)

  CI가 정상 실행되면 ACR에서 확인할 수 있다.

  ![image](https://github.com/user-attachments/assets/ae36e1eb-97c2-425d-86e7-2c5f5a11bbb0)


* CD 세팅

  ![image](https://github.com/user-attachments/assets/9eb2b129-f7d4-4b71-9853-6b6ecc86a196)
  
  ![image](https://github.com/user-attachments/assets/b95aadd4-1dd6-499b-9a1e-75e8ea8c9008)

  CD가 정상 실행됐는지 POD 상태로 확인한다.

  ![image](https://github.com/user-attachments/assets/c793fdce-3f6c-4c36-baf2-13b4bf475a3c)



   
2) HPA

booking 서비스에 hpa 설정한다.

![image](https://github.com/user-attachments/assets/f9923afe-a8d8-4e63-93a9-783daf8b482b)

-> CPU 사용률 50% 기준으로 최소 1개에서 최대 3개까지 자동으로 스케일링



3) Secret

mysql 접속 비밀번호를 직접 지정이 아닌 secret에 담아 사용한다.

![image](https://github.com/user-attachments/assets/07f0ca35-4a6b-4e04-93b4-b65aeb6fdd67)


코드 적용

![image](https://github.com/user-attachments/assets/fd352794-e86c-4dd0-88e1-3794a25db3bc)

![image](https://github.com/user-attachments/assets/5e70e057-eeff-4b53-aa56-d0707514ecfc)



4) PVC


공간을 할당해서 DB 데이터를 저장하면 종료된 이후에도 데이터가 보존된다.

![image](https://github.com/user-attachments/assets/0d5245a3-48bd-40a4-976f-5457643ba905)


현재 user21의 aks에 설치된 mysql은 pv와 pvc가 이미 할당되어있다.

그래서 pod를 계속 재시작해도 데이터가 그대로 남아있다.

![image](https://github.com/user-attachments/assets/7f8e718f-666a-489d-ab4f-ac47649bb7a7)

![image](https://github.com/user-attachments/assets/94bdb305-6ae4-46a3-ac57-742857261793)




5) Liveness

현재 dashboard 서비스는 kafka 접속 실패로 pod가 정상 실행되지 않는다.

![image](https://github.com/user-attachments/assets/7ebaac6b-95b8-4c8d-a422-fe76cfea8b04)


셀프힐링(liveness)을 통해 계속 재시작하는 것을 확인할 수 있다.

![image](https://github.com/user-attachments/assets/4fe2913f-4a84-4dff-b678-da313d577b5d)


