package hellojpa;

import javax.persistence.*;

@Entity
public class Locker {
    @Id @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    @Column(name = "LOCKER_NAME")
    private String name;

    @OneToOne(mappedBy = "locker")
    private Member member;

    /*
    * 일대일 정리
    *
    *   주 테이블에 외래 키
    *       - 주 객체가 대상 객체의 참조를 가지는 것 처럼
    *       - 주 테이블에 외래 키를 두고 대상 테이블을 찾음
    *       - 객체 지향 개발자 선호
    *       - JPA 맴핑 편리
    *       - 장점 : 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
    *       - 단점 : 값이 없으면 외래키에 NULL을 허용
    *
    *   대상 테이블에 외래 키
    *       - 대상 테이블에 외래키가 존재
    *       - 전통적인 데이터베이스 개발자 선호
    *       - 장점 : 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
    *       - 단점 : 프록시 기능의 한계로 지연로딩으로 설정해도 항상 즉시 로딩됨됨    * */
}
