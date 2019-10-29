package org.san.home.clients.service.impl;

import org.san.home.clients.jpa.ClientRepository;
import org.san.home.clients.model.Client;
import org.san.home.clients.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;


    @Override
    @Transactional
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional
    public @NotNull Client add(@NotNull Client client) {
        return clientRepository.save(Objects.requireNonNull(client));
    }

    @Override
    @Transactional
    public @NotNull Client update(@NotNull Client client) {
        return clientRepository.save(Objects.requireNonNull(client));
    }

    @Override
    @Transactional
    public void delete(@NotNull Long clientId) {
        clientRepository.deleteById(Objects.requireNonNull(clientId));
    }

    @Override
    @Transactional
    public Client get(@NotNull Long id) {return clientRepository.getOne(id);}
}
