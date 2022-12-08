package planareas.api;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PlanArea {

    private String name;
    private String areaWKT;

    public static @NonNull PlanArea valueOf(@NonNull String name, @NonNull String area) {
        return new PlanArea().setName(name).setAreaWKT(area);
    }
}
