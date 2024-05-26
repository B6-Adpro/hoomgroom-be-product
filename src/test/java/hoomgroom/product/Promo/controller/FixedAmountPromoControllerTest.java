package hoomgroom.product.Promo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hoomgroom.product.Promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.Promo.model.Factory.FixedAmountPromoFactory;
import hoomgroom.product.Promo.model.FixedAmountPromo;
import hoomgroom.product.Promo.service.FixedAmountPromoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FixedAmountPromoController.class)
@AutoConfigureMockMvc
class FixedAmountPromoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FixedAmountPromoService service;

    FixedAmountPromoFactory fixedAmountPromoFactory = new FixedAmountPromoFactory();
    FixedAmountPromo promo;
    Object bodyContent;

    @BeforeEach
    void setUp() {
        promo = fixedAmountPromoFactory.createPromo();
        promo.setName("BELANJAHEMAT20000");
        promo.setMinimumPurchase(20000000000L);
        promo.setDescription("Diskon Belanja 20000 dengan minimal pembelian 20 milyar");
        promo.setExpirationDate(LocalDateTime.of(2024,10, 5, 0, 0));
        promo.setDiscountAmount(20000L);

        bodyContent = new Object() {
            public final String name = "BELANJAHEMAT20000";
            public final String description = "Diskon Belanja 20000 dengan minimal pembelian 20 milyar";
            public final LocalDateTime expirationDate = LocalDateTime.of(2024,10, 5, 0, 0);
            public final Long minimumPurchase = 0L;
            public final Long discountAmount = 20000L;
        };
    }

    @Test
    void testGetAllFixedAmountPromo() throws Exception {
        List<FixedAmountPromo> allPromos = List.of(promo);

        when(service.findAll()).thenReturn(allPromos);

        mvc.perform(get("/api/promo/fixed/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(handler().methodName("getAllFixedAmountPromo"))
            .andExpect(jsonPath("$[0].name").value(promo.getName()));

        verify(service, atLeastOnce()).findAll();
    }

    @Test
    void testGetFixedAmountPromoById() throws Exception {
        when(service.findById(any(UUID.class))).thenReturn(promo);

        mvc.perform(get("/api/promo/fixed/6dcf51bd-b0b4-4cd6-a07f-35f9794049a6")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(handler().methodName("getFixedAmountPromoById"))
            .andExpect(jsonPath("$.name").value(promo.getName()));

        verify(service, atLeastOnce()).findById(any(UUID.class));
    }

    @Test
    void testCreateFixedAmountPromo() throws Exception {
        when(service.create(any(FixedAmountPromoRequest.class))).thenReturn(promo);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String promoJson = objectMapper.writeValueAsString(bodyContent);

        mvc.perform(post("/api/promo/fixed/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(promoJson))
            .andExpect(status().isOk())
            .andExpect(handler().methodName("createFixedAmountPromo"))
            .andExpect(jsonPath("$.name").value(promo.getName()));

        verify(service, atLeastOnce()).create(any(FixedAmountPromoRequest.class));
    }

    @Test
    void testUpdateFixedAmountPromo() throws Exception {
        when(service.update(any(UUID.class), any(FixedAmountPromoRequest.class))).thenReturn(promo);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String promoJson = objectMapper.writeValueAsString(bodyContent);

        mvc.perform(put("/api/promo/fixed/update/6dcf51bd-b0b4-4cd6-a07f-35f9794049a6")
            .contentType(MediaType.APPLICATION_JSON)
            .content(promoJson))
            .andExpect(status().isOk())
            .andExpect(handler().methodName("updateFixedAmountPromo"))
            .andExpect(jsonPath("$.name").value(promo.getName()));

        verify(service, atLeastOnce()).update(any(UUID.class), any(FixedAmountPromoRequest.class));
    }

    @Test
    void testDeleteFixedAmountPromo() throws Exception {
        mvc.perform(delete("/api/promo/fixed/delete/c1d6e60d-f620-48ab-aae7-30238651d673")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(handler().methodName("deleteFixedAmountPromo"));

        verify(service, atLeastOnce()).delete(any(UUID.class));
    }

    @Test
    void testDeletePromoNotFound() throws Exception {
        UUID randomId = UUID.fromString("c1d6e60d-f620-48ab-aae7-30238651d673");
        doThrow(NoSuchElementException.class).when(service).delete(randomId);

        mvc.perform(delete("/api/promo/fixed/delete/{id}", randomId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Promo not found"));

        verify(service, atLeastOnce()).delete(randomId);
    }
}
