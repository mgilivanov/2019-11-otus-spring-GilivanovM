package ru.mgilivanov.project.service;

import ru.mgilivanov.project.domain.Client;
import ru.mgilivanov.project.model.client.ClientEditRequest;
import ru.mgilivanov.project.model.client.ClientFindRequest;
import ru.mgilivanov.project.model.client.ClientAddRequest;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client add(ClientAddRequest request);

    Client edit(ClientEditRequest request);

    Optional<Client> findById(long id);

    List<Client> find(ClientFindRequest request);

}
