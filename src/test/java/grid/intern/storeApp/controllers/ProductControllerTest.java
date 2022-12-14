package grid.intern.storeApp.controllers;

import grid.intern.storeApp.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {



    @Test
    public void shouldGetProductList() {
        String uri = "/products/";

    }
}
