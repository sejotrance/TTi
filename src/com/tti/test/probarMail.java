package com.tti.test;

import java.util.UUID;

import com.tti.Email;

public class probarMail {
	public static void main(String [] args)
	   {
		String email = "sejo1234@gmail.com";
		String username = "Sejotrance";
		String asunto = "Bienvenido a TTI";
		String cuerpo = "Te damos al bienvenida a nuestro sistema";
		String password = UUID.randomUUID().toString();
		Email mailRegistro = new Email("mail@mail.com",email , asunto, cuerpo);
		System.out.println("FROM: " + mailRegistro.getFrom());
		System.out.println("TO: " + mailRegistro.getTo());
		System.out.println("ASUNTO: " + mailRegistro.getAsunto());
		System.out.println("CUERPO: " + mailRegistro.getCuerpo());
  	  	mailRegistro.sendRegistroMail(username, password);
//		mailRegistro.sendMail();
	   }
}
