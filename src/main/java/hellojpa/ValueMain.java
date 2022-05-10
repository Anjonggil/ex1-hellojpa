package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ValueMain {
    public static void main(String[] args) {
        {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

            EntityManager em = emf.createEntityManager();

            EntityTransaction tx = em.getTransaction();
            tx.begin();

            try{
                Address address = new Address("city","street","10000");
                Member member = new Member();
                member.setUsername("member1");
                member.setAddress(address);
                em.persist(member);



                Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode()); //인스턴스의 값을 복사해서 사용해야함
                Member member2 = new Member();
                member2.setUsername("member2");
                member2.setAddress(copyAddress);
                em.persist(member);

//              `member.getAddress().setCity("newCity"); // 값타입의 인스턴스 값을 공유하는 것은 매우 위험함

                /*
                * 객체 타입의 한계
                *
                * 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다.
                * 문제는 임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본 타입이 아니라 객체타입이다.
                * 자바 기본 타입에 값을 대입하면 값을 복사한다.
                * 객체타입은 참조값을 직접 대입하는것을 막을 방법이 없다.
                * 객체의 공유 참조는 피할 수 없다.
                * */

                /*
                * 불변 객체
                *
                * 객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
                * 값타입은 불변 객체로 설계해야함
                * 불변 객체 : 생성 시점 이후 절대값을 변경할 수 없는 객체
                * 생성자로만 값을 설정하고 수정자를 만들지 않으면 됨 (setter 사용 X)
                * Integer,String은 자바가 제공하는 대표적인 불변객체
                * */
                tx.commit();

            }catch (Exception e){
                tx.rollback();
            }finally {
                em.close(); // 반드시 닫아줘야함. 그래야 내부적으로 DB Connection 을 반환한다.
            }

            /*
             * 주의사항
             *
             * 엔티티 매니저 팩토리는 하나만 생성해서 application 전체에서 공유
             * 엔티티 매니저는 쓰레드 간의 공유해선 안된다. 그래야 내부적으로 DB Connection 을 반환한다.
             * JPA의 모든 데이터 변경을 트랜잭션 안에서 실행되어야 한다
             * */

            emf.close();
        }
    }
}
