package planareas.api;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import planareas.service.PlanAreaService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PlanAreaController {

    public final @NonNull PlanAreaService service;
    @Autowired private Validator validator;

    /**
     * Creates a plan area with the specified {@link DataPoint#getName() name}, at the geographical
     * location given by {@link DataPoint#getLat() latitude} and {@link DataPoint#getLon()
     * longitude}
     */
    @PostMapping("/api/planareas")
    @ResponseBody
    public PlanArea create(@Valid @RequestBody DataPoint where) throws Exception {
        log.info("Creating plan area for {}", where);

        String name = where.getName();
        double lat = where.getLat();
        double lon = where.getLon();

        if (where.getLat() < -90
                || where.getLat() > 90
                || where.getLat() < -180
                || where.getLon() > 180) throw new ResponseStatusException(BAD_REQUEST);

        double radiusInKM = 50;
        Set<ConstraintViolation<DataPoint>> violations = validator.validate(where);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<DataPoint> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ResponseStatusException(BAD_REQUEST);
            // throw new ConstraintViolationException("Error occurred: " + sb.toString(),
            // violations);
        }

        planareas.service.PlanArea created = service.createForLocation(name, lat, lon, radiusInKM);

        PlanArea ret = toApi(created);

        return ret;
    }

    /**
     * @return all available PlanAreas, in no specific order
     */
    @GetMapping("/api/planareas")
    @ResponseBody
    public Stream<PlanArea> findAll() {
        return service.findAll().stream().map(this::toApi);
    }

    @GetMapping(path = "/api/planareas/name/{name}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public PlanArea findByName(@PathVariable("name") String name) {
        Optional<planareas.service.PlanArea> found = service.findByName(name);

        return found.map(this::toApi).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST));
    }

    private PlanArea toApi(planareas.service.PlanArea modelObject) {
        String name = modelObject.name();
        String areaWKT = modelObject.area().toText();
        PlanArea ret = PlanArea.valueOf(name, areaWKT);
        return ret;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) -> {
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });
        return errors;
    }
}
