package br.com.jonathas.poctestcontainer.controller;

import br.com.jonathas.poctestcontainer.model.Mensagem;
import br.com.jonathas.poctestcontainer.service.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mensagens")
public class MensagemController {

    private final MensagemService service;

    public MensagemController(MensagemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Mensagem>> getAllMensagens() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Mensagem> getMensagem(@PathVariable String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mensagem create(@RequestBody Mensagem mensagem) {
        return service.create(mensagem);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
