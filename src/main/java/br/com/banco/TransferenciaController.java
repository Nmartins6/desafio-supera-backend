package br.com.banco;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
public class TransferenciaController {
    private TransferenciaRepository transferenciaRepository;

    public TransferenciaController(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    private Specification<Transferencia> filtraDataInicio(Date dataInicio) {
        return (root, cq, cb) -> {
            if (dataInicio == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(root.get("data"), dataInicio);
        };
    }

    private Specification<Transferencia> filtraDataFim(Date dataFim) {
        return (root, cq, cb) -> {
            if (dataFim == null) {
                return null;
            }

            return cb.lessThanOrEqualTo(root.get("data"), dataFim);
        };
    }

    private Specification<Transferencia> filtraNomeOperador(String nomeOperador) {
        return (root, cq, cb) -> {
            if (nomeOperador == null) {
                return null;
            }

            return cb.equal(root.get("nomeOperador"), nomeOperador);
        };
    }

    @CrossOrigin
    @GetMapping("/transferencias")
    List<Transferencia> transferencias(
            @RequestParam(value = "dataInicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataInicio,

            @RequestParam(value = "dataFim", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataFim,

            @RequestParam(value = "nomeOperador", required = false) String nomeOperador
    ) {
        Specification<Transferencia> query = filtraDataInicio(dataInicio).and(
                filtraDataFim(dataFim)
        ).and(
                filtraNomeOperador(nomeOperador)
        );

        return this.transferenciaRepository.findAll(query);
    }
}
