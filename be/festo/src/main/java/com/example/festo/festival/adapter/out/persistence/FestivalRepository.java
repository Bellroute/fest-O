package com.example.festo.festival.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FestivalRepository extends JpaRepository<FestivalEntity,Long> {
    boolean existsByInviteCode(String inviteCode);
    List<MainFestivalProjection> findAllProjectedBy();

    List<SearchFestivalProjection> findByNameContaining(String keyword);

    Optional<FestivalEntity> findByInviteCode(String inviteCode);
    List<MainFestivalProjection> findAllProjectedByManagerId(Long managerId);
    Optional<FestivalEntity> findByManagerId(Long mangerId);
    Optional<Boolean> existsByManagerId(Long managerId);

}
