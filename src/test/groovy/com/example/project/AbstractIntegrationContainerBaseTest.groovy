package com.example.project

import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import spock.lang.Specification

//통합 테스트 환경 구축
@SpringBootTest
abstract class AbstractIntegrationContainerBaseTest extends Specification{
    //static을 이용해서 테스트를 진행할때 한번만 진행되게 하기
    static final GenericContainer MY_REDIS_CONTAINER

    static {
        MY_REDIS_CONTAINER = new GenericContainer<>("redis:6")
                .withExposedPorts(6379) //포트는 도커에서 expose한 포트,
                                                // 테스트 컨테이너가 충돌되지 않은 걸 해줘서 스프링 부트와 맵핑해줌

        MY_REDIS_CONTAINER.start() //랜덤한 포트 맵핑

        //스프링부트에게 port 값 알려주기
        System.setProperty("spring.redis.host", MY_REDIS_CONTAINER.getHost())
        //6379와 랜덤하게 맵핑된 포트를 string값으로 전달해줌
        System.setProperty("spring.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379).toString())
    }
}
