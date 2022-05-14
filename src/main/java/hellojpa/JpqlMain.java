package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            List<Member> result = em.createQuery("SELECT m FROM Member m where m.username like '%kim%'",Member.class).getResultList();

            for (Member member : result) {
                System.out.println("member = " + member);
            }
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close(); // 반드시 닫아줘야함. 그래야 내부적으로 DB Connection 을 반환한다.
        }

        emf.close();
    }
}
