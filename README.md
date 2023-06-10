# musinsa_homwork
무신사 과제 문제

### 프로젝트 환경
> STS 툴 사용
> Java , jdk 11, Gradle(gradle-7.6.1-bin)

### 실행 방법
1. HomeworkApplication.class에서 java application 실행을 통한 콘솔 동작

### 프로젝트 구현 방향

#### 라이브러리
> Spring jpa
> QueryDSL
> h2 database

#### 메인 화면 프로세스

1. BufferedReader를 이용하여 입력값을 받음
2. 1차 while 반복문을 통해 주문(o/order) 또는 종료(q/quit)를 선택
3. BufferedReader에 입력된 값이 o/order 그리고 q/quit 아니게 되면 continue를 통한 재입력 맞다면 주문 화면 이동
4. 상품 목록 조회하여 상품 리스트 출력
5. 2차 while 문을 통해 상품 번호와 수량을 대량입력
6. 첫번째 if 조건 : 상품번호가 비어있고 선택한 상품이 있을 경우 결제 진행
7. 두번째 elseif 조건 : 상품번호가 비어있는 경우 break를 타고 초기화면 이동
8. 세번째 else 조건 : 상품번호가 있고 수량을 선택하였을 경우 해당 상품들을 itemList에 담음 단) 입력 상품번호가 없을 경우 다시 처음부터 상품입력
9. 6번에 이어서 상품구매가 완료되었을 경우 이동, orderService.insertOrderApp service 단으로 이동하여 데이터 처리
10. insertOrderApp 에서 다시 한번 상품존재여부 체크(상품이 없을 경우 NotExistProductException 발생), 재고량 체크(재고가 없을 경우 SoldOutException 예외처리 발생)
11. NotExistProductException 과 SoldOutException이 발생하지 않았다면 결제화면 출력 및 데이터베이스에 주문 정보 등록
12. 주문이 완료되고 나면 다시 break를 타고나와 1차 while 문에서 주문(o/order) 또는 종료(q/quit)를 선택
13. q/quit를 선택하면 프로그램 종료

#### 프로젝트 구조

![프로젝트구조](https://github.com/rhkdgur/musinsa_homwork/assets/67618667/de616d1f-7bc5-4799-b02c-a043c50fa261)


> 패키지 구조

![패키지구조](https://github.com/rhkdgur/musinsa_homwork/assets/67618667/3bf92c40-ec0c-484b-a677-9c0a35ee2930)

* service : 실질적인 데이터가 처리
* dto : view 단에서 전달되는 Object
* entity : 데이터베이스 테이블과 매칭되는 Object
* repository : CRUD 정의

> 구현 방향
* 단일 책임원칙을 준수하기 위해 모듈별 역할들을 package로 구분하였고 유지보수에 있어서 가독성에 어려움이 없도록하기 위한 방향으로 진행하였습니다.
* 공통적으로 사용되는 코드는 통합package를 생성하여 관리 할 수 있도록 처리하였습니다.
* DTO와 DefaultDTO를 구분지어 데이터 전송 객체(DTO) 와 데이터 검색(DefaultDTO) 객체로 구분 지어 사용하였습니다. 
* Controller와 service를 구분지어 Controller에서 데이터의 유동성에 집중하였고, service는 실질적 데이터 처리를 하는 구간으로 정의하였습니다.
