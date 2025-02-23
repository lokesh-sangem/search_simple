package com.tac.search_simple.repo;

import com.tac.search_simple.entity.JiraTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface JiraTicketRepo extends JpaRepository<JiraTicket, UUID> {
    @Query(value="SELECT * FROM jira_ticket_data WHERE unique_id = :uniqueId",nativeQuery = true)
    Optional<JiraTicket> findByUniqueId(@Param("uniqueId")UUID uniqueId);

    void deleteByUniqueId(UUID uniqueId);
    @Query(value="SELECT * FROM jira_ticket_data  WHERE unique_id IN (:uniqueIds) ",nativeQuery=true)
    List<JiraTicket> findByUniqueIds(@Param("uniqueIds") List<UUID>uniqueIds);
}
