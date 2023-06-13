package com.example.project.recommendation.service;


import com.example.project.recommendation.dto.RecommendDto;
import com.example.project.recommendation.entity.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendSearchService {

    private final RecommendationRepositoryService recommendationRepositoryService;

    public List<RecommendDto> searchRecommendDtoList() {

        //redis

        //db
        return recommendationRepositoryService.findAll()
                .stream()
                .map(this::convertToRecommnedDto)
                .collect(Collectors.toList());
    }

    private RecommendDto convertToRecommnedDto(Recommendation recommendation){

        return RecommendDto.builder()
                .id(recommendation.getId())
                .recommendAddress(recommendation.getRecommendAddress())
                .recommendName(recommendation.getRecommendName())
                .latitude(recommendation.getLatitude())
                .longitude(recommendation.getLongitude())
                .build();
    }


}
