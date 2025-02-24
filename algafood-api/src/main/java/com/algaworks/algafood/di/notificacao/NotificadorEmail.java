package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.config.oap.LogContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmail implements Notificador {

	private static final Logger log = LoggerFactory.getLogger(NotificadorEmail.class);

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s através do e-mail %s: %s\n", 
				cliente.getNome(), cliente.getEmail(), mensagem);

		LogContext.put("notificacao_por_mail", "true");
	}

}
