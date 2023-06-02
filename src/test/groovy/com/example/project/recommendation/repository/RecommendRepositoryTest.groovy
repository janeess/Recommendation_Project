package com.example.project.recommendation.repository

import com.example.project.AbstractIntegrationContainerBaseTest
import com.example.project.recommendation.entity.Recommendation
import org.springframework.beans.factory.annotation.Autowired


//클래스 상속 받기 -> 통합테스트 환경 구축 완료
class RecommendRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private RecommendRepository recommendRepository

    //각 테스트 메소드 실행 중에 데이터 정리
    def setup(){
        recommendRepository.deleteAll()
    }

    def "RecommendRepository save"() {
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
            .build() //전체 빌더패턴에서 클래스 생성

        when:
        def result = recommendRepository.save(recommendation)

        //결과값이 입력한 값과 동일한지 확인
        then:
        result.getRecommendAddress() == address
        result.getRecommendName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }

    def "Recommendation saveAll"() {
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
                .build() //전체 빌더패턴에서 클래스 생성
        when:
        recommendRepository.saveAll (Arrays.asList(recommendation))
        def result= recommendRepository.findAll()

        then:
        result.size() == 1
    }

}
