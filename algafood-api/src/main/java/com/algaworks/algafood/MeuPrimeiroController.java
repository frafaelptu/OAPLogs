package com.algaworks.algafood;

import com.algaworks.algafood.config.oap.LogContext;
import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MeuPrimeiroController {

    private AtivacaoClienteService ativacaoClienteService;

    public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
        this.ativacaoClienteService = ativacaoClienteService;
    }

    @GetMapping("/logs")
    @ResponseBody
    public ResponseEntity<Cliente> logs() {
        LogContext.put("name", "João");

        Cliente joao = new Cliente("João", "joao@xyz.com", "3499998888");

        LogContext.put("cliente_criado", "true");
        LogContext.put("antes_chamar_ativação_cliente", "true");
        ativacaoClienteService.ativar(joao);
        LogContext.put("depois_chamar_ativação_cliente", "true");


        return ResponseEntity.ok(joao);
    }

    @GetMapping("/sem-logs")
    @ResponseBody
    public String semLogs() {
        return "Sem Logs";
    }

}
