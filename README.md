# musinsa_homwork
무신사 과제 문제

### 프로젝트 환경
> STS 툴 사용
> Java , jdk 17, Gradle(gradle-7.6.1-bin)

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

> DTO 구조
1.ProductDTO ( 상품 정보) , ProductDefaultDTO(상품 검색 용)
2.OrderAppDTO ( 주문자 정보) , OrderAppItemDTO(주문 상품 정보) , OrderAppDefaultDTO(주문 검색 용)
 
> Service 설명

Product Service method
+ selectProductList : 조건에 따른 상품 개수와 목록을 조회한다.
+ selectProduct : 상품 상세 정보를 조회한다.
+ updateProductCnt : 상품 재고량을 업데이트 한다.
+ validateProductCntCheck : 재고량이 0개일 경우 SoldOutExcepion 을 발생시킵니다.

Order Service method
+ selectOrderAppList : 조건에 따른 주문자 개수와 목록을 조회한다.
+ insertOrderApp : 주문 상품을 결제처리 한다.
  - selectOrderNumMax : 신규 주문정보를 가져온다.
  - selectProductNumListIn : 주문자가 선택한 상품의 정보를 새로 들고온다.
    * 상품번호와 주문 상품번호를 비교하여 존재하는 상품인지 체크한다.
    * 주문자가 가지고있는 상품번호를 토대로 주문상품의 구매수량과 재고수량을 비교한다.
  - OrderAppItem에 해당 itemDTO 정보를 담아 OrderApp 연관관계 리스트에 담는다.
  - Product.minusProductCnt : 구매수량을 재고량에서 마이너스 한다. 영속성 컨텍스트에 변경감지를 통해 update 처리 된다.
  - PayAppDisplayUtil.productPayDisPlay : 결제 목록 출력
  - entityManager.save : OrderApp 주문 정보 등록 처리
    * OrderAppItem은 OrderApp 연관관계이다. 그래서 OrderApp의 ItemList(OrderAppItem)은 cascade = {CascadeType.PERSIST,CascadeType.MERGE}로 설정 되어있으므로 자동등록이 이루어진다.