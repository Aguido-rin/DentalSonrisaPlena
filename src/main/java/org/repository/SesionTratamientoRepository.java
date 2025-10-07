package org.repository;

import org.model.SesionTratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionTratamientoRepository extends JpaRepository<SesionTratamiento, Long> {
}
