package io.sandark.vehiclecrud.ws;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreators;

import java.io.IOException;

import static org.springframework.ws.test.server.ResponseMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VehicleEndpointITest {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ResourceLoader resourceLoader;

    private MockWebServiceClient mockClient;
    private Resource xsdSchema = new ClassPathResource("xsd/vehicles.xsd");

    @BeforeEach
    public void init() {
        mockClient = MockWebServiceClient.createClient(context);
    }

    @Test
    public void findVehicleById_validRequest() throws IOException {
        Resource requestPayload = resourceLoader.getResource("classpath:ws/findVehicleRequest.xml");;
        Resource responsePayload = resourceLoader.getResource("classpath:ws/findVehicleResponse.xml");;

        mockClient
                .sendRequest(RequestCreators.withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));
    }

    @Test
    public void createVehicle_validRequest() throws IOException {
        Resource requestPayload = resourceLoader.getResource("classpath:ws/createVehicleValidRequest.xml");;
        Resource responsePayload = resourceLoader.getResource("classpath:ws/createVehicleResponse.xml");;

        mockClient
                .sendRequest(RequestCreators.withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));
    }
}