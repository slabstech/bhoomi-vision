package planareas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanAreaRepository extends JpaRepository<PlanArea, Long> {

    PlanArea findByName(String name);

    List<PlanArea> findAllByOrderByName();
}
