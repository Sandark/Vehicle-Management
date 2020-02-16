package io.sandark.vehiclecrud.ws;

import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.entity.VehicleType;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehicleMapper {

    public List<io.sandark.vehiclecrud.Vehicle> mapEntitiesListToXmlTypeList(List<Vehicle> v) {
        return v.stream()
                .map(this::mapEntityToXmlType)
                .collect(Collectors.toList());
    }

    public io.sandark.vehiclecrud.Vehicle mapEntityToXmlType(Vehicle v) {
        io.sandark.vehiclecrud.Vehicle vehicleXmlType = new io.sandark.vehiclecrud.Vehicle();

        vehicleXmlType.setId(v.getId());
        vehicleXmlType.setBrand(v.getBrand());
        vehicleXmlType.setColor(v.getColor());
        vehicleXmlType.setModel(v.getModel());
        vehicleXmlType.setPlateCountry(v.getPlateCountry());
        vehicleXmlType.setPlateNumber(v.getPlateNumber());
        vehicleXmlType.setManufacturedCountry(v.getManufacturedCountry());
        vehicleXmlType.setCreationDate(mapLocalDate(v.getCreationDate()));
        vehicleXmlType.setVin(v.getVin());
        vehicleXmlType.setVehicleType(v.getVehicleType().toString());
        return vehicleXmlType;
    }

    public List<Vehicle> mapXmlTypeListToEntitiesList(List<io.sandark.vehiclecrud.Vehicle> v) {
        return v.stream()
                .map(this::mapXmlTypeToEntity)
                .collect(Collectors.toList());
    }

    public Vehicle mapXmlTypeToEntity(io.sandark.vehiclecrud.Vehicle v) {
        Vehicle vehicle = new Vehicle();

        vehicle.setBrand(v.getBrand());
        vehicle.setColor(v.getColor());
        vehicle.setModel(v.getModel());
        vehicle.setPlateCountry(v.getPlateCountry());
        vehicle.setPlateNumber(v.getPlateNumber());
        vehicle.setManufacturedCountry(v.getManufacturedCountry());
        vehicle.setCreationDate(mapXMLGregorianCalendar(v.getCreationDate()));
        vehicle.setVin(v.getVin());
        vehicle.setVehicleType(VehicleType.valueOf(v.getVehicleType()));

        return vehicle;
    }

    private XMLGregorianCalendar mapLocalDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar gcal = GregorianCalendar.from(date.atZone(ZoneId.systemDefault()));

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private LocalDateTime mapXMLGregorianCalendar(XMLGregorianCalendar date) {
        if (date == null) {
            return null;
        }
        return date.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
    }
}
