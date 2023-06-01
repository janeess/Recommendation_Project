package com.example.project.api.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoUriBuilderServiceTest extends Specification {

    private KakaoUriBuilderService kakaoUriBuilderService

    //모든 메소드 전에 실행되게 해주는 메소드
    def setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService();
    }

    def "buildUriByAddressSearch - 한글 파라미터의 경우 정상적으로 인코딩"() {

        given:  //테스트에 필요한 값을 준비
        String address = "서울 성북구"
        def charset= StandardCharsets.UTF_8

        //spock는 groovy라는 동적 프로그래밍언어로 되어있기 때문에
        // def 키워드를 통해 동적으로 타입을 선언해줘야한다.
        when: //테스트할 코드를 실행
        def uri = kakaoUriBuilderService.buildUriByAddressSearch(address)
        //인코딩 된 uri 정보를 디코딩 하기
        def decodeResult = URLDecoder.decode(uri.toString(), charset)

        then: //when과 함께 사용하며 예외 및 결과 값을 검증
        decodeResult == "https://dapi.kakao.com/v2/local/search/address.json?query=서울 성북구"
    }
}
