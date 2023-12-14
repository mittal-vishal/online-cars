package com.intuit.craft.onlinecars.repository;

import com.intuit.craft.onlinecars.model.SimilarityScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SimilarityScoreRepository extends JpaRepository<SimilarityScore, String> {

    @Query(value = "SELECT * FROM similarity_score where id != ?1", nativeQuery = true)
    List<SimilarityScore> findAllExceptCurrentID(String id);

}
