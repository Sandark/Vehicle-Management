package io.sandark.vehiclecrud.builder;

import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.entity.VehicleType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;

public class VehicleFixture {

    public static Vehicle getValidVehicle() {
        return VehicleBuilder.aVehicle()
                .withBrand(RandomStringUtils.randomAscii(10))
                .withVehicleType(VehicleType.values()[RandomUtils.nextInt(0, VehicleType.values().length - 1)])
                .withPlateNumber(RandomStringUtils.randomAscii(6))
                .withVin(RandomStringUtils.randomAscii(17))
                .withCreationDate(LocalDateTime.now())
                .build();
    }
}
