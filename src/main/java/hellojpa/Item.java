package hellojpa;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Item {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

    /*
    * 단일테이블 전략 SINGLE_TABLE(단순한 비지니스의 경우 채택)
    * 장점
    *   조인 필요 x,성능이 빠름
    *   조회쿼리가 단순함
    * 단점
    *   자식 엔티티가 매핑한 컬럼은 모두 널허용
    *   단일테이블에 모든것을 저장하므로 테이블이 커질 수 있고 때에 따라서 성능이 저하됨
    *
    * 조인 전략 JOIN(정석)
    * 장점
    *   테이블 정규화
    *   외래키 참조 무결성 제약조건 활용 가능
    *   저장공간 효율화
    * 단점
    *   조회시 조인을 많이 사용, 성능 저하
    *   조회쿼리 복잡
    *   데이터 저장시 INSERT QUERY 2번 나감
    *
    * 구현 클래스마다 테이블 전략(추천 X)
    * 장점
    *   서브타입 명확하게 구분해서 처리할때 효율적
    *   NOT NULL 제약조건 사용 가능
    * 단점
    *   여러 자식 테이블을 조회할때 성능이 느림
    *   자식테이블을 통합해서 쿼리하기 어려움
    * */
}
