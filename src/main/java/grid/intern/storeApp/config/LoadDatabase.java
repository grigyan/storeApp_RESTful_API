package grid.intern.storeApp.config;

import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.model.entity.Product;
import grid.intern.storeApp.repository.CustomerRepository;
import grid.intern.storeApp.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initCustomers(CustomerRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Customer("admin@gmail.com", "adminpass")));
            log.info("Preloading " + repository.save(new Customer("user@yahoo.com", "qwerty1234")));
        };
    }


    @Bean
    CommandLineRunner initProducts(ProductRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Product(2364L, "Nail gun", 8, 23.76)));
            log.info("Preloading " + repository.save(new Product(8900L, "Charger", 2, 108.33)));
        };
    }
}
