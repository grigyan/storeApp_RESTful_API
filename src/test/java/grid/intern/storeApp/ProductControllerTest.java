package grid.intern.storeApp;

import grid.intern.storeApp.controller.ProductController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {


}
