package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory = new ArrayList<>(); // 이런 식으로 사용하면 안됨

    //값 타입 컬렉션 대안

    /*
    * 실무에서는 상황에 따라 값타입 컬렉션 대신에 일대다 관계를 고려
    * 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용
    * 영속성 전이 + 고아 객체 제거를 사용하여 값타입 컬렉션처럼 사용
    * */

    /*
    * 값타입 컬렉션
    *
    * 값 타입을 하나 이상 저장할때 사용
    * 데이터 베이스는 컬렉션을 같은 테이블에 저장할 수 없다.
    * 컬렉션을 저장하기 위한 별도의 테이블이 필요함
    * 값타입 컬렉션의 생명주기는 entity에 의존한다.
    * 컬랙션 값타입의 경우 지연로딩으로 가져오게됨
    * 컬렉션의 데이터만 변경해도 update 쿼리가 날라감
    *
    * 제약 사항
    *
    * 값 타입은 엔티티와 다르게 식별자 개념이 없다.
    * 값은 변경하면 추적이 어렵다.
    * 값타입 컬렉션에 변경이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재값을 모두 다시 저장한다.
    * 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본 키를 구성해야함: null 입력 X, 중복저장 X
    * */

    @ManyToOne(fetch = FetchType.LAZY) // 멤버 입장에서 many team 입장에서 one  -> 관계가 무엇인지
    @JoinColumn(name = "TEAM_ID") // -> 외래키를 설정
    private Team team;

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public List<MemberProduct> getProducts() {
        return products;
    }

    public void setProducts(List<MemberProduct> products) {
        this.products = products;
    }

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
