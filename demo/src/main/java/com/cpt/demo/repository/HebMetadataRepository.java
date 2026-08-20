package com.cpt.demo.repository;

import com.cpt.demo.model.HebMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HebMetadataRepository extends JpaRepository<HebMetadata, Long> {
}
