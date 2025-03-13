package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.config.oap.LogContext;
import com.algaworks.algafood.log.ConsultaViacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

import java.util.ArrayList;

@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmail implements Notificador {

	private static final Logger log = LoggerFactory.getLogger(NotificadorEmail.class);

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s através do e-mail %s: %s\n", 
				cliente.getNome(), cliente.getEmail(), mensagem);

		ConsultaViacao consultaViacao = new ConsultaViacao();
		consultaViacao.setOperacao("Consulta viação");
		consultaViacao.setGrupo("RJ");
		consultaViacao.setNome("Eucatur");
		consultaViacao.setIdTransacao("YYYY");
		ArrayList<String> rotas = new ArrayList<>();
		rotas.add("Rota ABC");
		rotas.add("Rota XPT");
		consultaViacao.setRotas(rotas);
		LogContext.addData(consultaViacao);
	}

}
