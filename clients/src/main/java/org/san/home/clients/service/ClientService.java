package org.san.home.clients.service;

import org.san.home.clients.model.Client;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ClientService {

    List<Client> findAll();

    @NotNull Client add(@NotNull Client client);

    @NotNull Client update(@NotNull Client client);

    void delete(@NotNull Long clientId);
}
