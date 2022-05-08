package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "MEMBER_NAME")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Embedded
    private Period period;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY) // 멤버 입장에서 many team 입장에서 one  -> 관계가 무엇인지
    @JoinColumn(name = "TEAM_ID") // -> 외래키를 설정
    private Team team;

    /*
    * 프록시와 지연 로딩 주의
    *
    * 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
    * 즉시 로딩은 JPQL에서 N + 1문제를 일으킨다
    * 기본이 LAZY로 설정
    * */

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> products = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT")
//    private List<Product> products = new ArrayList<>();
    /*
    * 실무에서 다대다 맵핑을 사용할 수 없는 이유
    *
    * 중간 테이블에 맵핑 정보 이외에 다른 정보를 넣을 수 없음
    * 중간 숨어있는 테이블로 인해 쿼리가 이상하게 나감감    * */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        //team을 세팅하는 시점에 코드를 추가해주자!!
        //이거 히트다..
        team.getMembers().add(this);
    }
    /*
     * 요구사항 추가
     *
     * 1. 회원은 일반 회원과 관리자로 구분해야 한다.
     * 2. 회원 가입일과 수정일이 있어야 한다.
     * 3. 회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이 제한이 없다.
     *  */

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이 경우에는 commit 시점이 아닌 persist 가 실행 될때 INSERT가 됨.
//    private Long id;
//
//    @Column(name = "name", insertable = true, updatable = true , nullable = false, length = 10)
//    private String username;
//
//    private BigDecimal age;
//
//    @Enumerated(EnumType.STRING) // ORDINAL 절대 사용 금지 !!!!!!!!!!
//    private RoleType roleType;
//
//    @Temporal(TemporalType.TIMESTAMP) // 하이버네이트 최신 버전 사용시 LocalDate, LocalDateTime 까지 지원하면서 생략이 가능해짐
//    private Date createdDate;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDAte;
//
//    @Lob
//    private String description;
//
//    @Transient // DB에 반영 X
//    private int temp;
//
//    public Member() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public BigDecimal getAge() {
//        return age;
//    }
//
//    public RoleType getRoleType() {
//        return roleType;
//    }
//
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public Date getLastModifiedDAte() {
//        return lastModifiedDAte;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public int getTemp() {
//        return temp;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setAge(BigDecimal age) {
//        this.age = age;
//    }
//
//    public void setRoleType(RoleType roleType) {
//        this.roleType = roleType;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public void setLastModifiedDAte(Date lastModifiedDAte) {
//        this.lastModifiedDAte = lastModifiedDAte;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setTemp(int temp) {
//        this.temp = temp;
//    }
// 데이터베이스 스키마 자동 생성 주의점
    /*
    * 운영 장비에는 절대 create, create-drop, update 사용하면 안된다.
    * 개발 초기 단계에는 create, update
    * 테스트 서버는 update, validate
    * 스테이징과 운영 서버는 validate 또는 none
    * */
    /*
     * 엔티티 타입
     * @Entity로 정의 하는 객체
     * 데이터가 뱐해도 식별자로 지속해서 추적 가능
     *
     * 값타입
     * int, Integer, String처럼 단순히 값으로 사용하는 자바 기본타입이나 객체
     * 식별자가 없고 값만 있으므로 변경시 추적 불가
     */
}
