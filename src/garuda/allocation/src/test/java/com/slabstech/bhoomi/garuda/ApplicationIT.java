package com.slabstech.bhoomi.garuda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import planareas.api.DataPoint;
import planareas.repository.PlanArea;

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class ApplicationIT {

    private static final DockerImageName postgisImageName =
            DockerImageName.parse("postgres:15rc2-alpine3.16")
                    .asCompatibleSubstituteFor("postgres");

    @Container
    public static PostgreSQLContainer<?> postgis =
            new PostgreSQLContainer<>(postgisImageName)
                    .withDatabaseName("it") //
                    .withUsername("postgres") //
                    .withPassword("secret") //
                    .withExposedPorts(5432);

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("db.host", () -> "localhost");
        registry.add("db.port", () -> postgis.getFirstMappedPort().toString());
        registry.add("db.name", () -> "it");
        registry.add("db.user", () -> "postgres");
        registry.add("db.password", () -> "secret");
    }

    private @Autowired TestRestTemplate restTemplate;

    @Test
    public void createPlanArea() {
        DataPoint where = new DataPoint("Rosario", -32.9587, -60.6930);

        ResponseEntity<PlanArea> response = findByName(where.getName());
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);

        response = create(where);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.hasBody()).isTrue();

        PlanArea created = response.getBody();
        assertThat(created.getName()).isEqualTo(where.getName());
        // assertThat(created.getAreaWKT()).matches("POLYGON((.*))");

        response = findByName(created.getName());
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.hasBody()).isTrue();
        assertThat(response.getBody()).isEqualTo(created);

        List<PlanArea> all = List.of(getAll().getBody());
        assertThat(all).isEqualTo(List.of(created));
    }

    /** FIXME */
    @Test
    public void createInvalidPoint() {

        testCreateInvalidPoint("LatTooSmall", -90.01, 0);
        testCreateInvalidPoint("LatTooBig", 90.01, 0);
        testCreateInvalidPoint("LongTooSmall", 0, -180.01);
        testCreateInvalidPoint("LongTooBig", 0, 180.01);
    }

    private void testCreateInvalidPoint(String name, double lat, double lon) {
        DataPoint where = new DataPoint(name, lat, lon);
        ResponseEntity<PlanArea> response = create(where);
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    /** FIXME */
    @Test
    public void findAll() {
        DataPoint rosario = new DataPoint("Rosario2", -32.9587, -60.6930);
        DataPoint bsas = new DataPoint("Buenos Aires", -34.6037, -58.3816);
        assertThat(create(rosario).getStatusCode()).isEqualTo(CREATED);
        assertThat(create(bsas).getStatusCode()).isEqualTo(CREATED);

        List<String> allNames =
                List.of(getAll().getBody()).stream().map(PlanArea::getName).toList();
        assertThat(allNames).isEqualTo(List.of(rosario.getName(), bsas.getName()));
    }

    private ResponseEntity<PlanArea> create(DataPoint where) {
        return restTemplate.postForEntity("/api/planareas", where, PlanArea.class);
    }

    private ResponseEntity<PlanArea> findByName(String name) {
        String uri = "/api/planareas/name/{name}";
        return restTemplate.getForEntity(uri, PlanArea.class, Map.of("name", name));
    }

    private ResponseEntity<PlanArea[]> getAll() {
        String uri = "/api/planareas";
        return restTemplate.getForEntity(uri, PlanArea[].class);
    }
}
