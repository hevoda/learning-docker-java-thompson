package com.hervodalabs.formidable.services.impl;

import com.hervodalabs.formidable.domain.Client;
import com.hervodalabs.formidable.repositories.ClientRepository;
import com.hervodalabs.formidable.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@Profile("h2")
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> listAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(Long id) {
        Optional<Client> client = clientRepository.findById(id);  // Recupera il cliente per ID
        return client.orElse(null);  // Restituisce il cliente se presente, altrimenti null
    }
}
