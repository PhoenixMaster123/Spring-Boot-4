package springboot.opentelemetry;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    // Add own metric registry to observe custom metrics (Variant 1)
    private final ObservationRegistry observationRegistry;

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    public HomeController(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }

    @GetMapping("/")
    @Observed(name = "home.counter") // Variant 2: Using annotation to create an observation
    public String home() {

        // Variant 1:
//        Observation.createNotStarted("home.counter", observationRegistry).observe(() -> {
//            log.info("Home endpoint called");
//            return "Hello World!";
//        });

        log.info("Home endpoint called");
        return "Hello World!";
    }

    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        log.info("Greeting user: {}", name);
        simulateWork();
        return "Hello, " + name + "!";
    }

    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        log.info("Starting slow operation");
        Thread.sleep(500);
        log.info("Slow operation completed");
        return "Done!";
    }

    private void simulateWork() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}