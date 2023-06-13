package com.example.project.recommendation.service;

import com.example.project.api.dto.DocumentDto;
import com.example.project.api.dto.KakaoApiResponseDto;
import com.example.project.api.service.KakaoAddressSearchService;
import com.example.project.direction.entity.Direction;
import com.example.project.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendList(String address) { //고객의 요청 (주소 기반)

        //위치기반 데이터로 변환
       KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

       //응답 값 벨리데이션 체크
        if (Objects.isNull(kakaoAddressSearchService) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[RecommendationService is fail] Input address: {}", address);
            return;
        }

        //이상이 없으면 변환된 위도 경도 기준으로 가까운 약국 찾기
        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        //거리계산 알고리즘을 통해 가까운 추천장소 리스트 리턴& 저장
        List<Direction> directionList = directionService.buildDirectionList(documentDto);

        //고객에게 길안내 ㄱㄱ
        directionService.saveAll(directionList);
    }
}
