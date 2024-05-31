package hoomgroom.product.promo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hoomgroom.product.promo.dto.PercentagePromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.PercentagePromo;
import hoomgroom.product.promo.model.factory.PercentagePromoFactory;
import hoomgroom.product.promo.service.PercentagePromoService;
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

class PercentagePromoControllerTest {

    @Mock
    private PercentagePromoService service;

    @InjectMocks
    private PercentagePromoController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private PercentagePromoFactory promoFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        promoFactory = new PercentagePromoFactory();
    }

    @Test
    void testGetAllPercentagePromo() throws Exception {
        PercentagePromo promo = promoFactory.createPromo();
        List<PercentagePromo> promos = Collections.singletonList(promo);

        when(service.findAll()).thenReturn(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(promos));

        mockMvc.perform(get("/api/promo/percent/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testCreatePercentagePromo() throws Exception {
        PercentagePromoRequest request = PercentagePromoRequest.builder().build();
        PromoResponse response = PromoResponse.builder().build();

        when(service.create(request)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response));

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/promo/percent/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePercentagePromo() throws Exception {
        UUID id = UUID.randomUUID();
        PercentagePromoRequest request = PercentagePromoRequest.builder().build();
        PromoResponse response = PromoResponse.builder().build();

        when(service.update(id, request)).thenReturn(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response));

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/promo/percent/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }
}
