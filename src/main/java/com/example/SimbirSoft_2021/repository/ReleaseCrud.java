package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseCrud extends JpaRepository<ReleaseEntity, Long> {
    ReleaseEntity findByReleaseId(Long releaseId);
    ReleaseEntity findByDataStartAndDataEnd(String dataStart, String dataEnd);
}
