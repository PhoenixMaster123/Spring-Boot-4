package springboot.mfa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String home() {

        log.info("Home endpoint called");
        return "Hello World!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin page";
    }

    @GetMapping("/ott/sent")
    public String ottSent() {
        return "One Time Token sent";
    }
}