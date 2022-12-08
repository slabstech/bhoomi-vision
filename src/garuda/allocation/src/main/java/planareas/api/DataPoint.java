package planareas.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class DataPoint {

    @NotBlank private String name;

    @NotNull(message = "latitude is mandatory")
    @DecimalMin(value = "-90.0", message = "Minimum latitude -90")
    @DecimalMax(value = "90.0", message = "Maximun latitude 90")
    private double lat;

    @NotNull(message = "longitude is mandatory")
    @DecimalMax(value = "-180.0", message = "Minimum longitude -180 ")
    @DecimalMax(value = "180.0", message = "Maximun longitude 180")
    private double lon;
}
