package grupo_02.controllers;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/hello-world")
@RestController
public class HelloWorldController {

    public HelloWorldController() {
    }

    @GetMapping("/")
    public String helloWorld() {
        return "Hello World";
    }

}
