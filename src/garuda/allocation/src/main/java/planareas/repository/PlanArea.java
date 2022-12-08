package planareas.repository;

import lombok.Data;
import lombok.experimental.Accessors;

import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;

@Entity
@Table(name = "planarea")
@Data
@Accessors(chain = true)
public class PlanArea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    private Geometry area;
}
