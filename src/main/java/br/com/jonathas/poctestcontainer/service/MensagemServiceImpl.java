package br.com.jonathas.poctestcontainer.service;

import br.com.jonathas.poctestcontainer.model.Mensagem;
import br.com.jonathas.poctestcontainer.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensagemServiceImpl implements MensagemService {

    private final MensagemRepository repository;

    public MensagemServiceImpl(MensagemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Mensagem> findAll() {
        return repository.findAll().stream().filter(Mensagem::getAtivo).collect(Collectors.toList());
    }

    @Override
    public Mensagem findById(String id) {
        return getMensagemById(id);
    }

    @Override
    public Mensagem create(Mensagem msg) {
        return repository.save(msg);
    }

    @Override
    public Mensagem update(Mensagem msg, String id) {
        Mensagem mensagem = getMensagemById(id);
        mensagem.setTexto(msg.getTexto());

        Mensagem savedMsg = repository.save(mensagem);
        return savedMsg;
    }

    @Override
    public void delete(String id) {
        Mensagem msg = getMensagemById(id);
        msg.setAtivo(false);
        repository.save(msg);
    }

    private Mensagem getMensagemById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
