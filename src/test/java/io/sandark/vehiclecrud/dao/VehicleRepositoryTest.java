package io.sandark.vehiclecrud.dao;

import io.sandark.vehiclecrud.builder.VehicleBuilder;
import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.entity.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class VehicleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private VehicleRepository repository;

    private Vehicle persistedVehicle;

    @BeforeEach
    public void init() {
        Vehicle vehicle = VehicleBuilder.aVehicle()
                .withBrand("Volkswagen")
                .withPlateNumber("UNI130")
                .withVehicleType(VehicleType.HATCHBACK)
                .withVin("VWTESTVIN01314532")
                .build();

        persistedVehicle = entityManager.persistAndFlush(vehicle);
    }

    @Test
    public void shouldFindByVin() {
        Optional<Vehicle> foundVehicle = repository.findByVin(persistedVehicle.getVin());
        assertThat(foundVehicle).isNotEmpty();
        assertThat(foundVehicle.get().getId()).isEqualTo(persistedVehicle.getId());
        assertThat(foundVehicle.get().getVin()).isEqualTo("VWTESTVIN01314532");
    }

    @Test
    public void shouldFindAll() {
        List<Vehicle> all = repository.findAll();

        assertThat(all).isNotEmpty();
        assertThat(all).allMatch(v -> v.getId() != null);
    }
}