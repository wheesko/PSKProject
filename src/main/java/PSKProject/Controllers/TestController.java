package PSKProject.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class TestController {

    @GetMapping("/hello")
    public String helloGradle() {
        return "Hello Gradle!";
    }

}