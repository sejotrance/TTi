package com.tti.test;

import java.util.UUID;

import com.tti.Email;

public class probarMail {
	public static void main(String [] args)
	   {
		String email = "sejo1234@gmail.com";
		String username = "Sejotrance";
		String password = UUID.randomUUID().toString();
		Email mailRegistro = new Email("sejo1234@gmail.com",email , username, password);
  	  	//mailRegistro.sendRegistroMail(username, password);
		mailRegistro.sendMail();
	   }
}
