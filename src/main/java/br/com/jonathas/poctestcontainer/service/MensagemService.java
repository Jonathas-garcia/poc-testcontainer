package br.com.jonathas.poctestcontainer.service;

import br.com.jonathas.poctestcontainer.model.Mensagem;

import java.util.List;

public interface MensagemService {

    List<Mensagem> findAll();

    Mensagem findById(String id);

    Mensagem create(Mensagem msg);

    Mensagem update(Mensagem msg, String id);

    void delete(String id);
}
