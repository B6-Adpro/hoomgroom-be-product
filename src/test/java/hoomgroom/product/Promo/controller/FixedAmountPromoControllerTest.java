package hoomgroom.product.promo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hoomgroom.product.promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.FixedAmountPromo;
import hoomgroom.product.promo.service.FixedAmountPromoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FixedAmountPromoControllerTest {

    @Mock
    private FixedAmountPromoService service;

    @InjectMocks
    private FixedAmountPromoController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllFixedAmountPromo() throws Exception {
        FixedAmountPromo promo = new FixedAmountPromo();
        List<FixedAmountPromo> promos = Collections.singletonList(promo);

        when(service.findAll()).thenReturn(ResponseEntity.ok(promos));

        mockMvc.perform(get("/api/promo/fixed/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testCreateFixedAmountPromo() throws Exception {
        FixedAmountPromoRequest request = FixedAmountPromoRequest.builder().build();
        PromoResponse response = PromoResponse.builder().build();

        when(service.create(request)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/promo/fixed/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateFixedAmountPromo() throws Exception {
        UUID id = UUID.randomUUID();
        FixedAmountPromoRequest request = FixedAmountPromoRequest.builder().build();
        PromoResponse response = PromoResponse.builder().build();

        when(service.update(id, request)).thenReturn(ResponseEntity.ok(response));

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/promo/fixed/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }
}