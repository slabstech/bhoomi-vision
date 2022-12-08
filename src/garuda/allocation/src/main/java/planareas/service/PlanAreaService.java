package planareas.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import planareas.repository.PlanAreaRepository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@RequiredArgsConstructor
public class PlanAreaService {

    private final @NonNull GeometryFactory geometryFactory;
    private final @NonNull CoordinateTransformationService cts;
    private final @NonNull PlanAreaRepository repo;

    @Transactional
    public PlanArea createForLocation(
            String name, double latitude, double longitude, double radiusInKM) {

        Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));

        double radiusInDegrees = cts.metersToDegrees(radiusInKM * 1_000);

        Geometry area = location.buffer(radiusInDegrees);

        planareas.repository.PlanArea entity = new planareas.repository.PlanArea();
        entity.setName(name);
        entity.setArea(area);
        entity = repo.save(entity);

        return toModel(entity);
    }

    public Optional<PlanArea> findByName(@NonNull String name) {
        return Optional.ofNullable(repo.findByName(name)).map(this::toModel);
    }

    public List<PlanArea> findAll() {
        return repo.findAllByOrderByName().stream().map(this::toModel).toList();
    }

    private PlanArea toModel(planareas.repository.PlanArea entity) {
        return new PlanArea(entity.getName(), entity.getArea());
    }
}
