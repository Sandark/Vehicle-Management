package io.sandark.vehiclecrud.builder;

import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.entity.VehicleType;

import java.time.LocalDateTime;

public class VehicleBuilder {

    private long id;
    private String brand;
    private String model;
    private VehicleType vehicleType;
    private String plateCountry;
    private String plateNumber;
    private String vin;
    private String manufacturedCountry;
    private LocalDateTime creationDate;
    private String color;

    public static VehicleBuilder aVehicle() {
        return new VehicleBuilder();
    }

    public Vehicle build() {
        Vehicle vehicle = new Vehicle();

        vehicle.setId(id);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setVehicleType(vehicleType);
        vehicle.setPlateCountry(plateCountry);
        vehicle.setPlateNumber(plateNumber);
        vehicle.setVin(vin);
        vehicle.setManufacturedCountry(manufacturedCountry);
        vehicle.setCreationDate(creationDate);
        vehicle.setColor(color);

        return vehicle;
    }

    public VehicleBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public VehicleBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public VehicleBuilder withModel(String model) {
        this.model = model;
        return this;
    }

    public VehicleBuilder withVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public VehicleBuilder withPlateCountry(String plateCountry) {
        this.plateCountry = plateCountry;
        return this;
    }

    public VehicleBuilder withPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public VehicleBuilder withVin(String vin) {
        this.vin = vin;
        return this;
    }

    public VehicleBuilder withManufacturedCountry(String manufacturedCountry) {
        this.manufacturedCountry = manufacturedCountry;
        return this;
    }

    public VehicleBuilder withCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public VehicleBuilder withColor(String color) {
        this.color = color;
        return this;
    }
}
