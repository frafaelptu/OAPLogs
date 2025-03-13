package com.algaworks.algafood;

import com.algaworks.algafood.config.oap.LogContext;
import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;
import com.algaworks.algafood.log.ConsultaViacao;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class MeuPrimeiroController {

    private AtivacaoClienteService ativacaoClienteService;

    public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
        this.ativacaoClienteService = ativacaoClienteService;
    }

    @GetMapping("/logs")
    @ResponseBody
    public ResponseEntity<Cliente> logs() throws InterruptedException {
        ConsultaViacao consultaViacao = new ConsultaViacao();
        consultaViacao.setOperacao("Consulta viação");
        consultaViacao.setGrupo("JCA");
        consultaViacao.setNome("Garcia");
        consultaViacao.setIdTransacao("XXXXXXX");
        ArrayList<String> rotas = new ArrayList<>();
        rotas.add("Rota1");
        rotas.add("Rota2");
        consultaViacao.setRotas(rotas);


        LogContext.addData(consultaViacao);

//        if (1 == 1) {
//            throw new RuntimeException("Erro forçado no controller");
//
//        }
        Cliente joao = new Cliente("João", "joao@xyz.com", "3499998888");


        ativacaoClienteService.ativar(joao);
        return ResponseEntity.ok(joao);
    }

    @GetMapping("/sem-logs")
    @ResponseBody
    public String semLogs() {
        return "Sem Logs";
    }

}
