package com.tac.search_simple.repo;

import com.tac.search_simple.entity.ConfluencePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfluencePageRepo extends JpaRepository<ConfluencePage, UUID> {

    @Query(value="SELECT * FROM confluence_page_data WHERE unique_id = :uniqueId",nativeQuery = true)
    Optional<ConfluencePage> findByUniqueId(@Param("uniqueId")UUID uniqueId);
    @Modifying
    @Transactional
    @Query(value="DELETE FROM confluence_page_data WHERE unique_id = :uniqueId",nativeQuery = true)
    void deleteByUniqueId(@Param("uniqueId")UUID uniqueId);
    @Query(value="SELECT * FROM confluence_page_data WHERE unique_id IN (:uniqueIds)",nativeQuery = true)
    List<ConfluencePage> findByUniqueIds(@Param("uniqueIds") List<UUID> uniqueIds);
}
