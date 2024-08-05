package com.ohgiraffers.lovematchproject.match.matchrepository;


import com.ohgiraffers.lovematchproject.match.matchmodel.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, Long>{
    MatchEntity findById(long id);
}
