package com.example.project.direction.service;

import com.example.project.api.dto.DocumentDto;
import com.example.project.direction.entity.Direction;
import com.example.project.direction.repository.DirectionRepository;
import com.example.project.recommendation.service.RecommendSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    // 추천장소 최대 검색 개수
    private static final int MAX_SEARCH_COUNT = 3;
    // 반경 10km이내
    private static final double RADIUS_KM = 10.0;

    private final RecommendSearchService recommendSearchService;
    private final DirectionRepository directionRepository;

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if(CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return directionRepository.saveAll(directionList);
    }

    //DocumentDto는 카카오 api에서 사용햇던 dto
    //고객 주소기반의 위도 경도 데이터가 담겨있는 dto
    public List<Direction> buildDirectionList(DocumentDto documentDto) {

        if(Objects.isNull(documentDto)) return Collections.emptyList();

        // 추천 데이터 조회
        return recommendSearchService.searchRecommendDtoList()
                .stream().map(recommendDto ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetPharmacyName(recommendDto.getRecommendName())
                                .targetAddress(recommendDto.getRecommendAddress())
                                .targetLatitude(recommendDto.getLatitude())
                                .targetLongitude(recommendDto.getLongitude())
                                .distance(
                                        calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                                recommendDto.getLatitude(), recommendDto.getLongitude()))
                                .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM) //위에서 지정한 반경 10km내에 있는 추천 장소만 검색되게 하기
                .sorted(Comparator.comparing(Direction::getDistance)) // 오름차순
                .limit(MAX_SEARCH_COUNT) //최대 3개까지
                .collect(Collectors.toList());

        // 거리계산 알고리즘(calculateDistance)을 이용해서, 고객과 추천 자옷 사이의 거리를 계산하고 sort


    }

    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));


    }
}
