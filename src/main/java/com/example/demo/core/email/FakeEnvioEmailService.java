package com.example.demo.core.email;

import com.example.demo.infrastructure.service.email.SmtpEnvioEmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService{

	@Override
	public void enviar(Mensagem mensagem) {
		
		String corpo = processarTemplate(mensagem);
		
		log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
	}
	
}
