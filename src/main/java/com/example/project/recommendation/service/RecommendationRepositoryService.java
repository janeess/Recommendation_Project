package com.example.project.recommendation.service;


import com.example.project.recommendation.entity.Recommendation;
import com.example.project.recommendation.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;


import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* Transactional
 * = 스프링에선 트랜잭션 처리를 @Transactional을 이용해서 처리
 * @Transactional이 포함된 메소드가 호출될 경우, 프록시 객체를 생성함으로써 트랜잭션 생성 및 커밋
 * 또는 롤백 후 트랜잭션 닫는 부수적인 작업을 프록시 객체에게 위임
 * cf) 프록시의 핵심적인 기능은 지정된 메소드가 호출될 때 이 메소드를 가로채어 부가 기능들을 프록시 객체에기 위임
 *      -> 즉, 개발자가 메소드에 @Transaction만 선언하고 비즈니스 로직에 집중 가능
 *
 *  - 스프링 AOP 기반으로 하는 기능들 (@Transactional, @Cacheable, @Async) 사용 시 self Invocation 문제로 인해 장애가 발생할 수 있음
 * 메소드가 호출되는 시점에 프록시 객체를 생성하고, 프록시 객체는 부가기능(트랜잭션)을 주입해준다.
 * ex) 외부에서 bar()라는 메소드가 실행될 때, 정상적으로 프록시가 동작
 *     but, @Transactional을 foo()에만 선언하고 외부에서 bar()를 호출하고, bar()->foo() 호출했다면?
 *
 *
 * */

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationRepositoryService {

    private final RecommendRepository recommendRepository;

    // self invocation test - 내부에서 호출
    public void bar(List<Recommendation> recommendationList) {
        log.info("bar CurrentTransactionName: "+ TransactionSynchronizationManager.getCurrentTransactionName());
        foo(recommendationList);
    }

    // self invocation test
    @Transactional
    public void foo(List<Recommendation> recommendationList) {
        log.info("foo CurrentTransactionName: "+ TransactionSynchronizationManager.getCurrentTransactionName());
        recommendationList.forEach(recommendation -> {
            recommendRepository.save(recommendation);
            throw new RuntimeException("error"); //에러발생
        });
    }



    @Transactional
    public List<Recommendation> saveAll(List<Recommendation> recommendationList) {
        if(CollectionUtils.isEmpty(recommendationList)) return Collections.emptyList();
        return recommendRepository.saveAll(recommendationList);
    }

    @Transactional
    public void updateAddress(Long id, String address) {
        Recommendation entity = recommendRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)) {
            log.error("[RecommendationRepositoryService updateAddress] not found id: {}", id);
            return;
        }

        entity.changeRecommendAddress(address);
    }


    //for test
    public void updateAddressWithoutTransaction(Long id, String address) {
        Recommendation entity = recommendRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)) {
            log.error("[RecommendationRepositoryService updateAddress] not found id: {}", id);
            return;
        }

        entity.changeRecommendAddress(address);
    }

    @Transactional(readOnly = true)
    public List<Recommendation> findAll() {
        return recommendRepository.findAll();
    }


}
