package planareas.config;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import planareas.api.PlanArea;
import planareas.api.PlanAreaController;
import planareas.repository.PlanAreaRepository;
import planareas.service.CoordinateTransformationService;
import planareas.service.PlanAreaService;

@Configuration
@EnableJpaRepositories(basePackageClasses = PlanAreaRepository.class)
@EntityScan(basePackageClasses = PlanArea.class)
public class PlanAreasAutoConfiguration {

    public @Bean PlanAreaController planAreasController(PlanAreaService service) {
        return new PlanAreaController(service);
    }

    @Bean
    PlanAreaService planAreaService(
            GeometryFactory gFac, CoordinateTransformationService cts, PlanAreaRepository repo) {
        return new PlanAreaService(gFac, cts, repo);
    }

    @Bean
    GeometryFactory geometryFactory() {
        return new GeometryFactory();
    }

    @Bean
    CoordinateTransformationService coordinateTransformationService() {
        return new CoordinateTransformationService();
    }
}
