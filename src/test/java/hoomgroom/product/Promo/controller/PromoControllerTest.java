package hoomgroom.product.promo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.dto.RedeemPromoRequest;
import hoomgroom.product.promo.dto.RedeemResponse;
import hoomgroom.product.promo.dto.Response;
import hoomgroom.product.promo.model.Promo;
import hoomgroom.product.promo.model.factory.PercentagePromoFactory;
import hoomgroom.product.promo.service.PromoService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PromoControllerTest {

    @Mock
    private PromoService promoService;

    @InjectMocks
    private PromoController promoController;

    private MockMvc mockMvc;

    private PercentagePromoFactory promoFactory;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(promoController).build();
        objectMapper = new ObjectMapper();
        promoFactory = new PercentagePromoFactory();
    }

    @Test
    void testGetAllPromos() throws Exception {
        Promo promo = promoFactory.createPromo();
        List<Promo> promos = Collections.singletonList(promo);

        when(promoService.findAll()).thenReturn(ResponseEntity.ok(promos));

        mockMvc.perform(get("/api/promo/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testGetPromoById() throws Exception {
        UUID id = UUID.randomUUID();
        PromoResponse promoResponse = PromoResponse.builder().build();

        when(promoService.findById(id)).thenReturn(ResponseEntity.ok(promoResponse));

        mockMvc.perform(get("/api/promo/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDeletePromo() throws Exception {
        UUID id = UUID.randomUUID();
        Response response = Response.builder().build();
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

        when(promoService.delete(id)).thenReturn(responseEntity);

        mockMvc.perform(delete("/api/promo/delete/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testRedeemPromo() throws Exception {
        UUID id = UUID.randomUUID();
        RedeemPromoRequest redeemPromoRequest = RedeemPromoRequest.builder()
                .transactionId(UUID.randomUUID())
                .totalPrice(100L)
                .build();
        RedeemResponse redeemResponse = RedeemResponse.builder().build();

        String redeemPromoRequestJson = objectMapper.writeValueAsString(redeemPromoRequest);

        when(promoService.redeem(redeemPromoRequest.getTransactionId(), id, redeemPromoRequest.getTotalPrice()))
                .thenReturn(ResponseEntity.ok(redeemResponse));

        mockMvc.perform(get("/api/promo/redeem/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(redeemPromoRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
