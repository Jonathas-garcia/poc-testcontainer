package br.com.jonathas.poctestcontainer.repository;

import br.com.jonathas.poctestcontainer.model.Mensagem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MensagemRepository extends MongoRepository<Mensagem, String> {
}
