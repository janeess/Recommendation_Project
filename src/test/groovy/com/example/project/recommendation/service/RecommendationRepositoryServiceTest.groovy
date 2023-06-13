package com.example.project.recommendation.service

import com.example.project.AbstractIntegrationContainerBaseTest
import com.example.project.recommendation.entity.Recommendation
import com.example.project.recommendation.repository.RecommendRepository
import org.springframework.beans.factory.annotation.Autowired
import org.testcontainers.shaded.org.bouncycastle.crypto.engines.EthereumIESEngine
import spock.lang.Specification

class RecommendationRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private RecommendationRepositoryService recommendationRepositoryService

    @Autowired
    private RecommendRepository recommendRepository

    void setup() {
        recommendRepository.deleteAll()
    }

    //dirty checking이 잘 되는지 확인
    def "RecommendRepository update - dirty checking success"() {
        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def recommendation = Recommendation.builder()
                .recommendAddress(inputAddress)
                .recommendName(name)
                .build()

        when:
        def entity = recommendRepository.save(recommendation)
        recommendationRepositoryService.updateAddress(entity.getId(), modifiedAddress)

        def result = recommendRepository.findAll()

        then:
        result.get(0).getRecommendAddress() == modifiedAddress
    }

    def "recommendRepository update - dirty checking fail"() {

        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def recommendation = Recommendation.builder()
                .recommendAddress(inputAddress)
                .recommendName(name)
                .build()
        when:
        def entity = recommendRepository.save(recommendation)
        recommendationRepositoryService.updateAddressWithoutTransaction(entity.getId(), modifiedAddress)

        def result = recommendRepository.findAll()

        then:
        result.get(0).getRecommendAddress() == inputAddress
    }


    def "self invocation"() {

        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def recommendation = Recommendation.builder()
                .recommendAddress(address)
                .recommendName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()
        when:
        recommendationRepositoryService.bar(Arrays.asList(recommendation))

        then:
        def e = thrown(RuntimeException.class)
        def result = recommendationRepositoryService.findAll()
        result.size() == 1 // 트랜잭션이 적용되지 않는다( 롤백 적용 X )
    }


    def "transactional readOnly test"() {

        given:
        String inputAddress = "서울 특별시 성북구"
        String modifiedAddress = "서울 특별시 광진구"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def input = Recommendation.builder()
                .recommendAddress(inputAddress)
                .recommendName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def recommendation = recommendRepository.save(input)
        recommendationRepositoryService.startReadOnlyMethod(recommendation.id)

        then:
        def result = recommendationRepositoryService.findAll()
        result.get(0).getRecommendAddress() == inputAddress
    }
}
