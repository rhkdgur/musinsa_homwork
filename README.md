# musinsa_homwork
무신사 과제 문제

### 프로젝트 환경
> STS 툴 사용
> Java , jdk 17, Gradle(gradle-7.6.1-bin)

### 라이브러리
> Spring jpa
> QueryDSL
> h2 database

### 프로젝트 구현 방향

#### 실행 방법
1. HomeworkApplication.class에서 java application 실행을 통한 콘솔 동작

#### 프로젝트 구조

> 패키지 구조
>> service, dto, entity,repository
1. service : 실질적인 데이터가 처리되는 패키지
2. dto : view 단에서 전달되는 Object 패키지
3. entity : 데이터베이스 테이블과 매칭되는 Object 패키지
4. repository : CRUD 정의하는 패키지

> 엔티티 구조
1. Product : 상품 엔티티
2. OrderApp : 주문자 엔티티
3. OrderAppItem : 주문 상품 엔티티
 
> 메인 화면 로직
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