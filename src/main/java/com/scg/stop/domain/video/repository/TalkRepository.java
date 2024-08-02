package com.scg.stop.domain.video.repository;

import com.scg.stop.domain.video.domain.Talk;
import com.scg.stop.domain.video.dto.response.TalksResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TalkRepository extends JpaRepository<Talk, Long> {

    @Query("SELECT t from Talk t "+
            "WHERE (:year IS NULL OR t.year = :year) " +
            "AND (:title IS NULL OR t.title LIKE %:title%)")
    Page<TalksResponse> findPages(
            @Param("title") String title,
            @Param("year") Integer year,
            Pageable pageable
    );
}
