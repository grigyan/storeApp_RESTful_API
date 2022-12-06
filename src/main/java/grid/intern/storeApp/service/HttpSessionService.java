package grid.intern.storeApp.service;

import grid.intern.storeApp.model.entity.Customer;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class HttpSessionService {
    private final HttpSession httpSession;

    public HttpSessionService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
}
