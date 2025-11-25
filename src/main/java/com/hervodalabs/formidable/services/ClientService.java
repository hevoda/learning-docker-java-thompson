package com.hervodalabs.formidable.services;

import com.hervodalabs.formidable.domain.Client;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ClientService {
    List<Client> listAll();
    Client getById(Long id);
}
