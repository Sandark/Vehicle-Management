package io.sandark.vehiclecrud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sandark.vehiclecrud.builder.VehicleBuilder;
import io.sandark.vehiclecrud.builder.VehicleFixture;
import io.sandark.vehiclecrud.entity.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@Sql(scripts = {"/schema.sql", "/test-data.sql"},
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
public class VehicleControllerITest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenVehicleIsInvalid_thenReturnsStatus400() throws Exception {
        Vehicle vehicle = VehicleBuilder.aVehicle().build();
        String body = objectMapper.writeValueAsString(vehicle);

        mockMvc.perform(post("/vehicle/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenVehicleIsValid_thenReturnsStatus201() throws Exception {
        Vehicle validVehicle = VehicleFixture.getValidVehicle();
        String body = objectMapper.writeValueAsString(validVehicle);

        mockMvc.perform(post("/vehicle/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void whenVehicleIsNotFound_thenReturnsStatus404() throws Exception {
        mockMvc.perform(get("/vehicle/{id}", 999999L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void whenFindAllVehicle_thenReturnResult() throws Exception {
        mockMvc.perform(get("/vehicle/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty());
    }
}
