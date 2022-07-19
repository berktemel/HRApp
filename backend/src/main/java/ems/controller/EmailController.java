package ems.controller;

import ems.module.EmailDetails;
import ems.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailDetails emailDetails) {
        return emailService.sendEmail(emailDetails);
    }

}
