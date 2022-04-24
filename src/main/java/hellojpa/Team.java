package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") //반대편 사이드에 걸려있는 것을 적어준다.
    private List<Member> members = new ArrayList<>();

    /*
    * mappedBy
    *
    * 테이블 연관관계와 객체 연관의 차이로 인해
    * 테이블의 경우 PK,FK를 사용하여 연관관계를 정의 할 수 있음
    * 하지만 객체의 연관관계의 경우 회원에서 팀으로의 참조값, 팀에서 회원에서의 참조값이 필요함
    *
    * 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다.(LIKE 파이프 통신)
    *
    * 위 예제의 경우 관계형 데이터베이스에서는 member에 있는 team_id를 변경함으로써 관리가 가능하지만
    * 객체에서는 두 가지의 외래키가 존재한다. 하지만 두 객체 외래키를 바꿔주는 것은 비효율적이기 때문에 둘 중 하나의 외래키만 관리가 필요
    * 객체의 연관 관계의 둘 중 하나를 주인으로 지정
    *
    * 연관관계의 주인
    *
    * 양방향 매핑의 규칙
    * 객체의 두 관계중 하나를 연관관계의 주인으로 지정
    * 연관관계의 주인만이 외래 키를 관리(등록, 수정)
    * 주인이 아닌쪽은 읽기만 가능
    * 주인은 mappedBy 속성 사용 X
    * 주인이 아니면 mappedBy 속성으로 주인 지정
    * 외래키가 있는 곳이 주인으로 정해라(가이드)
    * */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
