package grid.intern.storeApp.controllerTests;

import grid.intern.storeApp.controller.ProductController;
import grid.intern.storeApp.model.entity.Product;
import grid.intern.storeApp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)   // disabling security for tests
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void shouldGetAllProducts() throws Exception {
        // given
        List<Product> products = Arrays.asList(new Product(501, "product 1", 141, 13.11),
                new Product(2091, "product 2", 8, 108.7));
        when(productService.findAll()).thenReturn(products);

        // then
        mockMvc.perform(get("/product/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(501)))
                .andExpect(jsonPath("$[0].title", is("product 1")))
                .andExpect(jsonPath("$[0].available", is(141)))
                .andExpect(jsonPath("$[0].price", is(13.11)))
                .andExpect(jsonPath("$[1].id", is(2091)))
                .andExpect(jsonPath("$[1].title", is("product 2")))
                .andExpect(jsonPath("$[1].available", is(8)))
                .andExpect(jsonPath("$[1].price", is(108.7)));
    }

    @Test
    public void shouldGetProductById() throws Exception {
        // given
        List<Product> products = Arrays.asList(new Product(501, "product 1", 141, 13.11),
                new Product(2091, "product 2", 8, 108.7));
        when(productService.findById(501)).thenReturn(products.get(0));

        // then
        mockMvc.perform(get("/product/501"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(501)))
                .andExpect(jsonPath("$.title", is("product 1")))
                .andExpect(jsonPath("$.available", is(141)))
                .andExpect(jsonPath("$.price", is(13.11)));
    }



}
