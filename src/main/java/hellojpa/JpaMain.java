package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //생성
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("HelloA");
//
//            em.persist(member);

            //조회
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA"); //더티 체킹을 통한 JPA에서 업데이트
//           List<Member> result =  em.createQuery("select m from Member as m",Member.class).getResultList();
//
//            for (Member member : result){
//                System.out.println("member.name = " + member.getName());
//            }

//            비영속 상태
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");
//
//            //영속 상태
//            em.persist(member);

            //같은 트랜잭션 안에서 동일성이 보장됨
//            Member findMember1 = em.find(Member.class, 101L);
//            Member findMember2 = em.find(Member.class, 101L);
//
//            System.out.println("result = " + (findMember1 == findMember2)); // 엔티티의 동일성을 보장

            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);

            tx.commit(); // 실제 쿼리의 경우에는 COMMIT 에서 날라감
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
