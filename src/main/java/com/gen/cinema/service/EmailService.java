package com.gen.cinema.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendMail(String messages) throws Exception;
}
