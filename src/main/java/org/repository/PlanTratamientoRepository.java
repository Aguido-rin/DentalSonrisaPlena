package org.repository;

import org.model.PlanTratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanTratamientoRepository extends JpaRepository<PlanTratamiento, Long> {
}
