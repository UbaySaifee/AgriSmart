package com.agrismart.backend;

import com.agrismart.backend.integration.weather.WeatherApiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AgriSmartApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private WeatherApiClient weatherApiClient;

    @Test
    void completeFarmerJourneyAndOwnershipProtectionWork() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));

        String token = register("rahul@example.com", "Rahul Farmer");
        String otherToken = register("other@example.com", "Other Farmer");

        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Authentication is required"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName":"Duplicate Farmer",
                                  "email":"RAHUL@example.com",
                                  "password":"StrongPass123"
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email is already registered"));
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"rahul@example.com","password":"StrongPass123"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"rahul@example.com","password":"WrongPass123"}
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid email or password"));

        mockMvc.perform(get("/api/users/me").header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("rahul@example.com"));

        JsonNode farm = body(mockMvc.perform(post("/api/farms")
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "farmName":"Rahul Smart Farm",
                                  "village":"Khandwa",
                                  "district":"Khandwa",
                                  "state":"Madhya Pradesh",
                                  "pincode":"450001",
                                  "areaAcres":5.5,
                                  "soilType":"Black soil",
                                  "irrigationType":"DRIP",
                                  "latitude":21.8257,
                                  "longitude":76.3526
                                }
                                """))
                .andExpect(status().isCreated()));
        String farmId = farm.get("id").asText();

        mockMvc.perform(get("/api/farms").header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].farmName").value("Rahul Smart Farm"));

        mockMvc.perform(get("/api/farms/{farmId}", farmId).header("Authorization", bearer(otherToken)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Farm not found"));

        mockMvc.perform(put("/api/farms/{farmId}/soil", farmId)
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "soilType":"Black soil",
                                  "ph":6.7,
                                  "nitrogen":48,
                                  "phosphorus":32,
                                  "potassium":44,
                                  "organicCarbon":0.72,
                                  "electricalConductivity":0.42,
                                  "moisturePercentage":24
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moisturePercentage").value(24));

        JsonNode crops = body(mockMvc.perform(get("/api/crops").header("Authorization", bearer(token)))
                .andExpect(status().isOk()));
        String cropId = crops.get(0).get("id").asText();

        mockMvc.perform(post("/api/crops/farms/{farmId}", farmId)
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "cropId":"%s",
                                  "plantingDate":"2026-06-15",
                                  "expectedHarvestDate":"2026-10-20",
                                  "season":"Kharif",
                                  "status":"GROWING"
                                }
                                """.formatted(cropId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.farmId").value(farmId));

        when(weatherApiClient.getCurrentWeather(anyDouble(), anyDouble()))
                .thenReturn(new WeatherApiClient.WeatherDataDto(
                        31.2, 64.0, 0.0, 11.5, 996.0, 2,
                        "Partly cloudy", LocalDateTime.of(2026, 9, 22, 12, 0),
                        33.0, 23.0, 0.0
                ));

        mockMvc.perform(get("/api/farms/{farmId}/weather/current", farmId)
                        .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source").value("LIVE"))
                .andExpect(jsonPath("$.temperatureCelsius").value(31.2));

        mockMvc.perform(get("/api/farms/{farmId}/recommendations", farmId)
                        .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prototypeNotice").isNotEmpty())
                .andExpect(jsonPath("$.recommendations.length()").value(3));

        mockMvc.perform(get("/api/dashboard").header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.farmer.fullName").value("Rahul Farmer"))
                .andExpect(jsonPath("$.selectedFarm.id").value(farmId))
                .andExpect(jsonPath("$.soil.ph").value(6.7))
                .andExpect(jsonPath("$.weather.source").value("LIVE"))
                .andExpect(jsonPath("$.advisory.recommendations").isArray());
    }

    private String register(String email, String fullName) throws Exception {
        JsonNode response = body(mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fullName":"%s",
                                  "email":"%s",
                                  "password":"StrongPass123",
                                  "phone":"9876543210"
                                }
                                """.formatted(fullName, email)))
                .andExpect(status().isCreated()));
        return response.get("accessToken").asText();
    }

    private JsonNode body(org.springframework.test.web.servlet.ResultActions result) throws Exception {
        return objectMapper.readTree(result.andReturn().getResponse().getContentAsString());
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}
