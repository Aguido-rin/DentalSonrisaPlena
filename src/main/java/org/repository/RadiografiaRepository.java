package org.repository;

import org.model.Radiografia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RadiografiaRepository extends JpaRepository<Radiografia, Long> {
}
