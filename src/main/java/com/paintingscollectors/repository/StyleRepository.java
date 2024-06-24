package com.paintingscollectors.repository;

import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.StyleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {

    @Query("SELECT s FROM Style s WHERE s.name = :name")
    Optional<Style> findByName(StyleName name);
}
