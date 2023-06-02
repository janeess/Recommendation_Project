package com.example.project.recommendation.repository;

import com.example.project.recommendation.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

/* JPA
* JPA의 모든 데이터 변경은 트랜잭션 안에서 실행된다! cf) 트랜잭션이란 db의 상태를 변경하기 위해 수행하는 작업(select,insert,delete,update)
* 데이터변경이 일어나는 곳은 반드시 @Transactional 어노테이션을 추가해야한다.
*
* 영속성 컨텍스트 = 엔티티를 영구 저장하는 환경, 어플리케이션과 db사이에 entity를 보관하는 가상의 데이터베이스 같은 역할
* ex)EntityManager(영속성 컨텍스트)에 em.persist(member)를 이용해 Member객체를 영속 상태로 만든 상태
*    이 entity는 영속 상태, 이미 영속상태인 경우 merge를 통해 덮어 씌어짐
*
* 장점
* 1. 영속성 컨텍스트 내부에 1차 캐시를 가지고 있다. (persist 하는 순간 pk값(id),타입,객체를 맵핑해서 1차 캐시에 가지고 있음)
* 2. 한 트랜잭션 내에 1차 캐시에 이미 있는 값을 조회하는 경우 db를 조회하지 않고 1차캐시에 있는 내용을 그대로 가져옴
*   -> 조회했을 때 없으면 db에서 가져와서 1차캐시에 저장 후 반환
*
* ex) MemberA entity를 em.persist(MemberA) 하는 순간, 1차 캐시에 넣고 쓰기지연 SQL 저장소에 쿼리를 만들어 쌓음
*     MemberB도 em.persist(MemberB) 하는 순간 동일한 과정을 거치며, commit 하는 순간 flush가 되면서 db에 반영됨
* cf) flush = 영속성 컨텍스트의 변경내용을 db에 반영하며, 1차 캐시를 지우지는 않음
*
* */

public interface RecommendRepository extends JpaRepository<Recommendation, Long> {


}
