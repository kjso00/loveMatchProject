package com.ohgiraffers.lovematchproject.match.matchrepository;

import com.ohgiraffers.loveMatchProject.match.matchmodel.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, Long>{

}
