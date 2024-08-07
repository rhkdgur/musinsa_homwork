# musinsa_homwork
무신사 과제 문제

### 프로젝트 환경
> STS 툴 사용,
> Java , jdk 11, Gradle(gradle-7.6.1-bin)

### 실행 방법
* HomeworkApplication.class에서 java application 실행을 통한 콘솔 동작

### 라이브러리
> Spring jpa,
> QueryDSL,
> h2 database

### 메인 화면 프로세스

1. BufferedReader를 이용하여 입력값을 받음
2. 1차 while 반복문을 통해 주문(o/order) 또는 종료(q/quit)를 선택
3. BufferedReader에 입력된 값이 o/order 그리고 q/quit 아니게 되면 continue를 통한 재입력, 맞다면 주문 화면 이동
4. 상품 목록 조회하여 상품 리스트 출력
5. 2차 while 문을 통해 상품 번호와 수량을 대량입력
6. 첫번째 if 조건 : 상품번호가 비어있고 선택한 상품이 있을 경우 결제 진행
7. 두번째 elseif 조건 : 상품번호가 비어있는 경우 break를 타고 초기화면 이동
8. 세번째 else 조건 : 상품번호가 있고 수량을 선택하였을 경우 해당 상품들을 itemList에 담음 - 단) 입력 상품번호가 없을 경우 다시 처음부터 상품입력
9. 6번에 이어서 상품구매가 완료되었을 경우 이동, orderService.insertOrderApp service 단으로 이동하여 데이터 처리
10. orderService.insertOrderApp 에서 다시 한번 상품존재여부 체크(상품이 없을 경우 NotExistException 발생), 재고량 체크(재고가 없을 경우 SoldOutException 예외처리 발생) ,중복상품 체크(중복상품을 선택하였을 경우 OverlapException 예외처리 발생)
11. NotExistException,SoldOutException,OverlapException 이 발생하지 않았다면 결제화면 출력 및 데이터베이스에 주문 정보 등록
12. 주문이 완료되고 나면 다시 break를 타고나와 1차 while 문에서 주문(o/order) 또는 종료(q/quit)를 선택
13. q/quit를 선택하면 프로그램 종료

### 프로젝트 구조

![프로젝트구조20230612](https://github.com/rhkdgur/musinsa_homwork/assets/67618667/cf367342-9ce3-4cb1-b6a3-f0f0d97051fe)

### 패키지 구조

![패키지구조20230612](https://github.com/rhkdgur/musinsa_homwork/assets/67618667/3a049479-a550-48bd-8c37-38dbbd55a179)

* service : 실질적인 데이터가 처리
* dto : view 단에서 전달되는 Object
* entity : 데이터베이스 테이블과 매칭되는 Object
* repository : 데이터베이스에 CRUD 커넥션 처리

> 클래스
+ order
  - OrderApp.class : 주문정보 entity, OrderAppItem.class : 주문상품정보 entity
  - OrderAppDTO.class : 주문정보 DTO , OrderAppItemDTO.class : 주문상품정보 DTO , OrderAppDefaultDTO : 주문검색 DTO
  - OrderAppService.class : 주문 service
  - OrderAppRepostiroy.class : 주문 repostiroy, OrderAppItemRepository.class : 주문상품 repostiroy
+ product
  - Product.class : 상품정보 entity
  - ProductDTO.class : 상품 DTO, ProductDefaultDTO : 상품 검색 DTO
  - ProductService.class : 상품 service
  - ProductRepository.class : 상품 repository
+ common
  - BaseVO.class : 검색 공통 VO
  - BaseService.class : 공통 service
+ util
  - PayAppDisplayUtil.class : 화면 출력 처리 util
+ exception
  - NotExistProductException.class : 상품 미존재 예외처리
  - SoldOutException.class : 재고 매진 예외처리
  - OverlapException.class : 중복 상품 예외처리
+ AppConfig : QueryDSL 등 application에 사용되는 라이브러리 bean 생성 config

### 프로젝트 구현 방향
* 단일 책임원칙을 준수하기 위해 모듈별 역할들을 package로 구분하였고 유지보수에 있어서 가독성에 어려움이 없도록 하기 위한 방향으로 진행하였습니다.
* 공통적으로 사용되는 코드는 통합 common package를 생성하였고 상속을 통해 사용하도록 하였습니다.
* Entity 데이터의 일관성,안정성을 주기위하여 DTO와 구분지어 데이터 처리하는 방식을 이용하였습니다.
* 단위 테스트를 통해 코드의 안정성 및 로직을 체크하여 프로세스에 이상이 없도록 작업을 진행하였습니다.
* main(Controller)와 service를 구분지어 main(Controller)에서 데이터의 유동성에 집중하였고, service는 실질적 데이터 처리를 하는 구간으로 정의하였습니다.


### 무신사 프로젝트 결과
* [무신사 프로젝트 피드백..](https://github.com/rhkdgur/musinsa_homwork/wiki/%EB%AC%B4%EC%8B%A0%EC%82%AC-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B2%B0%EA%B3%BC-%ED%94%BC%EB%93%9C%EB%B0%B1%EC%97%90-%EB%8C%80%ED%95%9C-%EB%82%B4%EC%9A%A9)
* [무신사 프로젝트 피드백 해결 및 회고](https://github.com/rhkdgur/musinsa_homwork/wiki/%EB%AC%B4%EC%8B%A0%EC%82%AC-%ED%94%BC%EB%93%9C%EB%B0%B1%EC%97%90-%EB%8C%80%ED%95%9C-%ED%95%B4%EA%B2%B0-%EB%B0%8F-%ED%9A%8C%EA%B3%A0)
