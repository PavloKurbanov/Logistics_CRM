package org.example.logistics_crm.client.service.impl;

import org.example.logistics_crm.client.Client;
import org.example.logistics_crm.client.repository.ClientRepository;
import org.example.logistics_crm.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {
        return null;
    }

    @Override
    public Optional<Client> findById(Long clientId) {
        if(clientId == null){
            throw new IllegalArgumentException("Client id can't be null");
        }
        return clientRepository.findById(clientId);
    }

    @Override
    public List<Client> findByFirstName(String firstName) {
        return List.of();
    }

    @Override
    public List<Client> findByLastName(String lastName) {
        return List.of();
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> findByPhone(String phone) {
        return Optional.empty();
    }

    @Override
    public List<Client> findByFirstNameAndLastName(String firstName, String lastName) {
        return List.of();
    }
}
