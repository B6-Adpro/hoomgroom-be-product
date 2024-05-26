//package hoomgroom.product.Promo.controller;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import hoomgroom.product.Promo.dto.PercentagePromoRequest;
//import hoomgroom.product.Promo.model.Factory.PercentagePromoFactory;
//import hoomgroom.product.Promo.model.PercentagePromo;
//import hoomgroom.product.Promo.service.PercentagePromoService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(controllers = PercentagePromoController.class)
//@AutoConfigureMockMvc
//class PercentagePromoControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private PercentagePromoService service;
//
//    PercentagePromoFactory percentagePromoFactory = new PercentagePromoFactory();
//    PercentagePromo promo;
//    Object bodyContent;
//
//    @BeforeEach
//    void setUp() {
//        promo = percentagePromoFactory.createPromo();
//        promo.setName("BELANJAHEMAT10");
//        promo.setDescription("Diskon Belanja 10% tanpa minimal belanja");
//        promo.setExpirationDate(LocalDateTime.of(2024,10, 5, 0, 0));
//        promo.setMinimumPurchase(0L);
//        promo.setPercentage(10.0);
//
//        bodyContent = new Object() {
//            public final String name = "BELANJAHEMAT10";
//            public final String description = "Diskon Belanja 10% tanpa minimal belanja";
//            public final LocalDateTime expirationDate = LocalDateTime.of(2024,10, 5, 0, 0);
//            public final Long minimumPurchase = 0L;
//            public final Double percentage = 10.0;
//        };
//    }
//
//    @Test
//    void testGetAllPercentagePromo() throws Exception {
//        List<PercentagePromo> allPromos = List.of(promo);
//
//        when(service.findAll()).thenReturn(allPromos);
//
//        mvc.perform(get("/api/promo/percent/")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(handler().methodName("getAllPercentagePromo"))
//            .andExpect(jsonPath("$[0].name").value(promo.getName()));
//
//        verify(service, atLeastOnce()).findAll();
//    }
//
//    @Test
//    void testGetPercentagePromoById() throws Exception {
//        when(service.findById(any(UUID.class))).thenReturn(promo);
//
//        mvc.perform(get("/api/promo/percent/6dcf51bd-b0b4-4cd6-a07f-35f9794049a6")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(handler().methodName("getPercentagePromoById"))
//            .andExpect(jsonPath("$.name").value(promo.getName()));
//
//        verify(service, atLeastOnce()).findById(any(UUID.class));
//    }
//
//    @Test
//    void testCreatePercentagePromo() throws Exception {
//        when(service.create(any(PercentagePromoRequest.class))).thenReturn(promo);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String promoJson = objectMapper.writeValueAsString(bodyContent);
//
//        mvc.perform(post("/api/promo/percent/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(promoJson))
//                .andExpect(status().isOk())
//                .andExpect(handler().methodName("createPercentagePromo"))
//                .andExpect(jsonPath("$.name").value(promo.getName()));
//
//        verify(service, atLeastOnce()).create(any(PercentagePromoRequest.class));
//    }
//
//    @Test
//    void testUpdatePercentagePromo() throws Exception {
//        when(service.update(any(UUID.class), any(PercentagePromoRequest.class))).thenReturn(promo);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String promoJson = objectMapper.writeValueAsString(bodyContent);
//
//        mvc.perform(put("/api/promo/percent/update/6dcf51bd-b0b4-4cd6-a07f-35f9794049a6")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(promoJson))
//                .andExpect(status().isOk())
//                .andExpect(handler().methodName("updatePercentagePromo"))
//                .andExpect(jsonPath("$.name").value(promo.getName()));
//
//        verify(service, atLeastOnce()).update(any(UUID.class), any(PercentagePromoRequest.class));
//    }
//
//    @Test
//    void testDeletePercentagePromo() throws Exception {
//        mvc.perform(delete("/api/promo/percent/delete/c1d6e60d-f620-48ab-aae7-30238651d673")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(handler().methodName("deletePercentagePromo"));
//
//        verify(service, atLeastOnce()).delete(any(UUID.class));
//    }
//
//    @Test
//    void testDeletePercentagePromoNotFound() throws Exception {
//        UUID randomId = UUID.fromString("c1d6e60d-f620-48ab-aae7-30238651d673");
//        doThrow(NoSuchElementException.class).when(service).delete(randomId);
//
//        mvc.perform(delete("/api/promo/percent/delete/{id}", randomId)
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound())
//            .andExpect(content().string("Promo not found"));
//
//        verify(service, atLeastOnce()).delete(randomId);
//    }
//}
