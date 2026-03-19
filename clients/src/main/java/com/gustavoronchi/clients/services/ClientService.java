package com.gustavoronchi.clients.services;

import com.gustavoronchi.clients.dto.ClientDTO;
import com.gustavoronchi.clients.entities.Client;
import com.gustavoronchi.clients.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<Client> result = clientRepository.findAll(pageable);
        return result.map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {
        List<Client> result = clientRepository.findAll();
        return result.stream().map(ClientDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Client entity = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        return new ClientDTO(entity);
    }
}