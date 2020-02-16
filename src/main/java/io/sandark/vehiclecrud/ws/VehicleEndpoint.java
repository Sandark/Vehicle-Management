package io.sandark.vehiclecrud.ws;

import io.sandark.vehiclecrud.CreateVehicleRequest;
import io.sandark.vehiclecrud.CreateVehicleResponse;
import io.sandark.vehiclecrud.FindVehicleRequest;
import io.sandark.vehiclecrud.FindVehicleResponse;
import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.exceptions.NonUniqueVinException;
import io.sandark.vehiclecrud.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

@Endpoint
public class VehicleEndpoint {

    private static final String NAMESPACE = "http://www.sandark.io/vehiclews";

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @Autowired
    public VehicleEndpoint(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createVehicleRequest")
    @ResponsePayload
    public CreateVehicleResponse createVehicleRequest(@RequestPayload CreateVehicleRequest request) throws NonUniqueVinException {
        Vehicle createdVehicle = vehicleService.create(vehicleMapper.mapXmlTypeToEntity(request.getVehicle()));

        CreateVehicleResponse response = new CreateVehicleResponse();
        response.setVehicle(vehicleMapper.mapEntityToXmlType(createdVehicle));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "findVehicleRequest")
    @ResponsePayload
    public FindVehicleResponse findVehicleRequest(@RequestPayload FindVehicleRequest request) {
        Optional<Vehicle> vehicle = vehicleService.findById(request.getId());

        FindVehicleResponse response = new FindVehicleResponse();
        response.setVehicle(vehicleMapper.mapEntityToXmlType(vehicle.get()));

        return response;
    }
}
