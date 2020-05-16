package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mgilivanov.project.domain.Client;
import ru.mgilivanov.project.exception.BusinessLogicException;
import ru.mgilivanov.project.model.client.ClientEditRequest;
import ru.mgilivanov.project.model.client.ClientFindRequest;
import ru.mgilivanov.project.model.client.ClientAddRequest;
import ru.mgilivanov.project.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public Client add(ClientAddRequest request){
        return clientRepository.save(new Client(request));
    }

    @Override
    public Client edit(ClientEditRequest request) {
        findById(request.getId()).orElseThrow(() -> new BusinessLogicException(BusinessLogicException.CLIENT_NOT_FOUND_CODE, BusinessLogicException.CLIENT_NOT_FOUND_MESSAGE));
        return clientRepository.save(new Client(request));
    }

    @Override
    public Optional<Client> findById(long id){
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> find(ClientFindRequest request){
            return clientRepository.findByPassportIdLikeAndLastNameLikeAndFirstNameLikeAndMiddleNameLike(
                    request.getPassportId() + "%", request.getLastName() + "%", request.getFirstName() + "%", request.getMiddleName() + "%");
    }
}
