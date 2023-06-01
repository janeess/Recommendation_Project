package com.example.project.recommendation.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

//통합 테스트 환경 구축
@SpringBootTest
class RecommendRepositoryTest extends Specification {

    @Autowired
    private RecommendRepository recommendRepository

    def "test"() {

    }

}
