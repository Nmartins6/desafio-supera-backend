package br.com.banco;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContaController {

    private ContaRepository contaRepository;

    ContaController(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @GetMapping("/contas")
    List<Conta> contas() {
        return this.contaRepository.findAll();
    }

}
