package planareas.service;

import org.locationtech.jts.geom.Geometry;

public record PlanArea(String name, Geometry area) {}
