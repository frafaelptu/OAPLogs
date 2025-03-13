package com.algaworks.algafood.di.service;

import com.algaworks.algafood.config.oap.LogContext;
import com.algaworks.algafood.log.ConsultaViacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;

import java.util.List;

@Component
public class AtivacaoClienteService {

	@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
	@Autowired
	private Notificador notificador;
	
	public void ativar(Cliente cliente) throws InterruptedException {
        //simula uma espera
        Thread.sleep(100);
        try {
            cliente.ativar();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        Thread.sleep(150);
        notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
        Thread.sleep(300);

        //Exemplo recuperando um objeto com mais de uma instância por algum atributo especifico
        ConsultaViacao consultaViacao = LogContext.getDataByAttribute(ConsultaViacao.class, "grupo", "jca");
        if (consultaViacao != null){
            consultaViacao.setIdTransacao("11111111111111111");
        }

    }

}
