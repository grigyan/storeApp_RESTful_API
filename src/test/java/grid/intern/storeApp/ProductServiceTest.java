package grid.intern.storeApp;

import grid.intern.storeApp.model.entity.Product;
import grid.intern.storeApp.repository.ProductRepository;
import grid.intern.storeApp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    public void shouldFindAllProducts() {
        // given
        Product product = new Product(1, "iPhone", 2, 102.5);
        List<Product> products = List.of(product);

        // when
        when(productRepository.findAll()).thenReturn(products);
        List<Product> actual = productService.findAll();

        // then
        assertEquals(actual.size(), 1);
        assertEquals(actual.get(0).getId(), 1);
        assertEquals(actual.get(0).getTitle(), "iPhone");
        assertEquals(actual.get(0).getAvailable(), 2);
        assertEquals(actual.get(0).getPrice(), 102.5);
    }

    @Test
    public void shouldFindProductById() {
        // given
        Product product = new Product(1, "iPhone", 2, 102.5);

        // when
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        Product actual = productService.findById(1);

        // then
        assertEquals(product.getId(), actual.getId());
        assertEquals(product.getPrice(), actual.getPrice());
        assertEquals(product.getTitle(), actual.getTitle());
        assertEquals(product.getAvailable(), actual.getAvailable());
    }
}
