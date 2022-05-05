package hellojpa;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /*
    * 상속 관계 맵핑이 아님
    * 엔티티가 아님
    * 테이블과 관계없고 단순히 엔티티가 공통으로 사용하는 매핑정보를 모으는 역할
    * 주로 등록일 수정일 등록자 수정자 같은 전체 엔티티에서 공통으로 적용하는 정보를 모을때 사용
    * 참고 : Entity 클래스는 엔티티나 @MappedSuperClass로 지정한 클래스만 상속 가능
   *
    * */
}
