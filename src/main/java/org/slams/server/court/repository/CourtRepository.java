package org.slams.server.court.repository;

import org.slams.server.court.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourtRepository extends JpaRepository<Court, Long> {

    @Query("SELECT c FROM Court c " +
            "WHERE (c.latitude BETWEEN :startLatitude AND :endLatitude) " +
            "AND (c.longitude BETWEEN :startLongitude AND :endLongitude)")
    List<Court> findByBoundary(
            @Param("startLatitude") double startLatitude,
            @Param("endLatitude") double endLatitude,
            @Param("startLongitude") double startLongitude,
            @Param("endLongitude") double endLongitude
    );

}
