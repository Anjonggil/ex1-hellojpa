package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {
    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private long id;
    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true) //casecade 사용 규칙 -> 두가지를 만족시에만 사용 : 두 객체의 life circle이 거이 유사할때, 아래의 객체를 접근하는것이 유일할때
    private List<Child> childList = new ArrayList<>();

    /*
    * 고아객체
    *
    * 참조가 제거된 엔티티는 다른곳에서 참조하지 않는 고아객체로 보고 삭제하는 기능
    * 특정 엔티티가 개인 소유할 때 사용
    * 참조하는 곳이 하나일 때 사용해야함
    * ONETOONE ONETOMANY 일 때 만 사용이 가능
    * */

    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
