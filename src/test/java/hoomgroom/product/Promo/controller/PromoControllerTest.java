//package hoomgroom.product.Promo.controller;
//
//
//import hoomgroom.product.Promo.model.Factory.FixedAmountPromoFactory;
//import hoomgroom.product.Promo.model.Factory.PercentagePromoFactory;
//import hoomgroom.product.Promo.model.FixedAmountPromo;
//import hoomgroom.product.Promo.model.PercentagePromo;
//import hoomgroom.product.Promo.model.Promo;
//import hoomgroom.product.Promo.service.PromoService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//@WebMvcTest(controllers = PromoController.class)
//@AutoConfigureMockMvc
//class PromoControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private PromoService service;
//
//    FixedAmountPromoFactory fixedAmountPromoFactory = new FixedAmountPromoFactory();
//    PercentagePromoFactory percentagePromoFactory = new PercentagePromoFactory();
//    List<Promo> promos = new ArrayList<>();
//
//    @BeforeEach
//    void setUp() {
//        FixedAmountPromo promo1 = fixedAmountPromoFactory.createPromo();
//        promo1.setName("BELANJAHEMAT20000");
//        promo1.setMinimumPurchase(20000000000L);
//        promo1.setDescription("Diskon Belanja 20000 dengan minimal pembelian 20 milyar");
//        promo1.setDiscountAmount(20000L);
//        promos.add(promo1);
//
//        PercentagePromo promo2 = percentagePromoFactory.createPromo();
//        promo2.setName("BELANJAHEMAT10");
//        promo2.setMinimumPurchase(0L);
//        promo2.setPercentage(10.0);
//        promos.add(promo2);
//    }
//
//    @Test
//    void testGetAllPromos() throws Exception {
//
//        when(service.findAll()).thenReturn(promos);
//
//        mvc.perform(get("/api/promo/")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(handler().methodName("getAllPromos"))
//            .andExpect(jsonPath("$[0].name").value(promos.getFirst().getName()));
//
//        verify(service, atLeastOnce()).findAll();
//    }
//
//    @Test
//    void testGetPromoById() throws Exception {
//        when(service.findById(any(UUID.class))).thenReturn(promos.getFirst());
//
//        mvc.perform(get("/api/promo/c1d6e60d-f620-48ab-aae7-30238651d673")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(handler().methodName("getPromoById"))
//            .andExpect(jsonPath("$.name").value(promos.getFirst().getName()));
//
//        verify(service, atLeastOnce()).findById(any(UUID.class));
//    }
//
//    @Test
//    void testGetPromoById_NotFound() throws Exception {
//        when(service.findById(any(UUID.class))).thenThrow(NoSuchElementException.class);
//
//        MvcResult mvcResult = mvc.perform(get("/api/promo/c1d6e60d-f620-48ab-aae7-30238651d673")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(handler().methodName("getPromoById"))
//                .andReturn();
//
//        verify(service, atLeastOnce()).findById(any(UUID.class));
//
//        String jsonResponse = mvcResult.getResponse().getContentAsString();
//
//        assertEquals("Promo not found", jsonResponse);
//    }
//
//    @Test
//    void testDeletePromo() throws Exception {
//        mvc.perform(delete("/api/promo/delete/c1d6e60d-f620-48ab-aae7-30238651d673")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(handler().methodName("deletePromo"));
//
//        verify(service, atLeastOnce()).delete(any(UUID.class));
//    }
//
//    @Test
//    void testDeletePromoNotFound() throws Exception {
//        UUID randomId = UUID.fromString("c1d6e60d-f620-48ab-aae7-30238651d673");
//        doThrow(NoSuchElementException.class).when(service).delete(randomId);
//
//        mvc.perform(delete("/api/promo/delete/{id}", randomId)
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound())
//            .andExpect(content().string("Promo not found"));
//
//        verify(service, atLeastOnce()).delete(randomId);
//    }
//}
//
