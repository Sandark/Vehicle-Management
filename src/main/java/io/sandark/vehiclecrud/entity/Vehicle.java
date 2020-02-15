package io.sandark.vehiclecrud.entity;

import io.sandark.vehiclecrud.validation.CountryIsoCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Vehicle {

    @Id
    @SequenceGenerator(
            name = "IdSequenceGenerator",
            sequenceName = "ID_GEN",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "IdSequenceGenerator")
    private Long id;

    @NotBlank(message = "Vehicle brand is mandatory")
    private String brand;
    private String model;

    @NotNull(message = "Vehicle type is mandatory")
    @Enumerated(value = EnumType.STRING)
    private VehicleType vehicleType;

    @CountryIsoCode
    private String plateCountry;
    @NotNull
    @Size(min = 2, max = 30)
    private String plateNumber;
    @NotNull
    @Size(max = 17)
    private String vin;
    @CountryIsoCode
    private String manufacturedCountry;
    private LocalDateTime creationDate;
    private String color;

    public Vehicle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPlateCountry() {
        return plateCountry;
    }

    public void setPlateCountry(String plateCountry) {
        this.plateCountry = plateCountry;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vinNumber) {
        this.vin = vinNumber;
    }

    public String getManufacturedCountry() {
        return manufacturedCountry;
    }

    public void setManufacturedCountry(String manufacturedCountry) {
        this.manufacturedCountry = manufacturedCountry;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
