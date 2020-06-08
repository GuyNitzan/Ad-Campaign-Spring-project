package me.guy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CampaignController {

    @GetMapping("/")
    public String index() {
        return "Congratulations from BlogController.java";
    }
}
