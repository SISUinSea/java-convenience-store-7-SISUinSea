# java-convenience-store-precourse


# 핵심 기능
- [x] products.md, promotion.md에서 재고 정보 읽어 객체로 만들기
    - [x] products.md 파일을 읽기
    - [x] 파일의 데이터를 사용해 품목을 추가
    - [x] promotions.md 파일을 읽기
    - [x] 파일의 데이터를 사용해 프로모션 정보를 추가
- [ ] 구매 요청 처리 로직 수행
    - [x] 구매 요청 입력을 객체화
    - [x] 가능한 요청인지 검사
    - [ ] 프로모션 여부 검사 후 프로모션 로직을 수행
        - [x] 프로모션 적용 상품인지 검사
        - [x] 프로모션 기간인지 검사
        - [ ] 요청한 수량이 프로모션 재고 내에서 처리할 수 있는지 검사
            - 요청한 수량 > 프로모션 재고인 경우, 사용자에게 프로모션이 적용되지 않는 상품이 있음을 알림 + 그 상품들을 결제에서 제외할지 묻기
                - 사용자가 프로모션 비적용 상품을 제외했을 때, 프로모션 적용 상품만 결제 목록에 추가
                - 사용자가 프로모션 비적용 상품을 포함했을 때, 프로모션 적용 상품 + 비적용 상품을 결제 목록에 추가
        - 프로모션 추가 상품을 받을 수 있을 때: 사용자에게 프로모션 상품을 추가할지 묻기
    - [ ] 멤버십 할인 로직을 수행


# 입력
- [x] 구매 "[상품-수량]"을 입력
    - [ ] 예외적인 상황 처리
        - [ ] 잘못 된 형식으로 값을 입력했을 경우
- [ ] 의사결정 입력
    - [ ] 예외적인 상황 처리
        - [ ] "Y" 또는 "N" 이외 다른 값을 입력했을 경우

# 출력
- [x] 재고 정보를 출력
- [ ] 영수증 출력
