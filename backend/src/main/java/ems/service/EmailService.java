package ems.service;

import ems.module.EmailDetails;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    String sendEmail(EmailDetails emailDetails);
}