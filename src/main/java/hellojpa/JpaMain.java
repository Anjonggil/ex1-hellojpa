package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2);
//
//            Member findMember = em.find(Member.class, 150L);
//            findMember.setName("test");

            /*
             * 준영속 상태
             *
             * 영속성 컨텍스트에서 빼버리는 것
             *
             * detach
             * 객체를 표적해서 빼버리는것
             *
             * clear
             * 전체 제거
             * */

//            em.detach(findMember);

            //tx.commit(); // 실제 쿼리의 경우에는 COMMIT 에서 날라감

            /*
             * 변경 감지
             *
                 * commit이 발생할 경우 엔티티와 스냅샷을 비교
                 *
                 * entity가 영속성 컨텍스트 안에 들어왔을 때 entity 스냅샷을 저장
                 * 그리고 커밋이 되는 시점에서 entity가 변경이 되었을 경우 entity와 스냅샷을 비교한 뒤 변경되었을 경우 쿼리를 쓰기 지연 SQL 저장소에 저장
                 *
                 * 쉽게 생각 하면 JPA는 값이 변경 되면 무조건 update 쿼리가 날라간다.
             * */

            /*
             * flush
             *
             * 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
             * 플러시 발생
             *
             * 변경 감지
             *
             * 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
             *
             * 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송
                * 플러시 하는 방법
                *
                * em.flush() 직접호출
                *
                * transaction commit
                *
                * jpql 실행 시
                *
                *
                    *  영속성 컨텍스트를 비우지 않음
                    *  영속성 컨텍스트릐 변경 내용을 데이터베이스에 동기화
                    *  트랜잭션이라는 작업단위가 중요한데 커밋직전에만 동기화 되면 된다.
                * */
            Team team = new Team();
            team.setName("TeamA");
            //team.getMembers().add(member);
            //연관관계의 주인에 값을 입력하지 않는다. 이 경우 외래키의 값이 null이 들어가짐;;

            em.persist(team);

            Member member = new Member();
            member.setUsername("userA");
            member.setTeam(team);

            Member member1 = new Member();
            member1.setUsername("userB");
            member1.setTeam(team);

            Member member2 = new Member();
            member2.setUsername("userC");
            member2.setTeam(team);

            em.persist(member);
            em.persist(member1);
            em.persist(member2);

            // 그냥 두가지 다 값을 넣어주자
            // 왜냐하면 transaction commit이 날라가거나 flush로 데이터를 넣어주지 않으면 list 를 사용 할때 세팅이 되어있지 않다
//            team.getMembers().add(member);
//            team.getMembers().add(member1);
//            team.getMembers().add(member2);
//            em.flush();
//            em.clear();
//

            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();

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
