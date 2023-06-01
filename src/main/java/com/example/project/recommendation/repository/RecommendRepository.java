package com.example.project.recommendation.repository;

import com.example.project.recommendation.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommendation, Long> {


}
