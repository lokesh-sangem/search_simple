package com.tac.search_simple.repo;

import com.tac.search_simple.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface TagRepo extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByTag(String tag);
}
