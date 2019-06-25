package com.es.phoneshop.web.controller.pages.ajax;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:context/test-context.xml")
public class AjaxCartControllerTest {

    private final static String URL = "/ajaxCart";
    private final static Long INVALID_ID = 600L;
    private final static Long OK_ID = 1025L;
    private final static Long OUT_OF_STOCK = 9999L;
    private final static Long INVALID_QUANTITY = 0L;

    @Resource
    private AjaxCartController ajaxCartController;

    private MockMvc mockMvc;

    @Value("${productIdError.doesntExist}")
    private String productIdDoesNotExist;

    @Value("${quantityError.outOfStock}")
    private String outOfStock;

    @Value("${quantityError.invalidQuantity}")
    private String invalidQuntity;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(ajaxCartController).build();
    }

    @Test
    public void addPhoneTest() throws Exception {
        JSONObject jsonObject = getJson(OK_ID, 1L);

        RequestBuilder requestBuilder = getRequestBuilder(jsonObject);

        mockMvc.perform(requestBuilder).andExpect(content()
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorMessage").doesNotExist());
    }

    @Test
    public void addPhoneInvalidIdTest() throws Exception {
        JSONObject jsonObject = getJson(INVALID_ID, 1L);

        RequestBuilder requestBuilder = getRequestBuilder(jsonObject);

        mockMvc.perform(requestBuilder).andExpect(content()
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartQuantity").value(0))
                .andExpect(jsonPath("$.overallPrice").value(BigDecimal.ZERO.toString()))
                .andExpect(jsonPath("$.errorMessage").value(productIdDoesNotExist));
    }

    private JSONObject getJson(Long id, Long quantity) {
        return new JSONObject() {{
            put("productId", id);
            put("quantity", quantity);
        }};
    }

    @Test
    public void addPhoneOutOfStockTest() throws Exception {
        JSONObject jsonObject = getJson(OK_ID, OUT_OF_STOCK);

        RequestBuilder requestBuilder = getRequestBuilder(jsonObject);
        mockMvc.perform(requestBuilder).andExpect(content()
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartQuantity").value(0))
                .andExpect(jsonPath("$.overallPrice").value(BigDecimal.ZERO.toString()))
                .andExpect(jsonPath("$.errorMessage").value(outOfStock));
    }

    @Test
    public void addPhoneInvalidQuantityTest() throws Exception {
        JSONObject jsonObject = getJson(OK_ID, INVALID_QUANTITY);

        RequestBuilder requestBuilder = getRequestBuilder(jsonObject);
        mockMvc.perform(requestBuilder).andExpect(content()
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartQuantity").value(0))
                .andExpect(jsonPath("$.overallPrice").value(BigDecimal.ZERO.toString()))
                .andExpect(jsonPath("$.errorMessage").value(invalidQuntity));
    }

    private RequestBuilder getRequestBuilder(JSONObject jsonObject) {
        return post(AjaxCartControllerTest.URL)
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON);
    }
}
