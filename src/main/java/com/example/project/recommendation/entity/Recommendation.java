package com.example.project.recommendation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "recommendation")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //db가 pk값을 대신 생성해줌
    private Long id;

    private String recommendName;
    private String recommendAddress;
    private double latitude; //위도
    private double longitude; //경도

    public void changeRecommendAddress(String address) {
        this.recommendAddress = address;
    }




}
