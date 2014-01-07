package com.tti.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ttiws.model.PersonaModel;

import com.tti.entidad.Usuario;

public class UserLogin {
	private static final String PERSISTENCE_UNIT_NAME = "TTIServicios";
	private static EntityManagerFactory factory;
	public static boolean login(String username, String password){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();
	    // read the existing entries and write to console
	    Query q = em.createQuery("select p FROM PersonaModel p WHERE p.per_Usuario = :username");
	    q.setParameter("username", username);
	    List<PersonaModel> personaList = q.getResultList();
	    for (PersonaModel persona : personaList) {
	      System.out.println(persona.getPer_Nombre() + " " + persona.getPer_Apellido_Paterno());
	    }
	    Usuario usuario = new Usuario();
	    if(personaList.size() > 0){
	    	PersonaModel usuarioFromDB = personaList.get(0);
	    	if(password.equals(usuarioFromDB.getPer_Password())){
	    		usuario.setUsername(usuarioFromDB.getPer_Usuario());
	    		usuario.setPassword(usuarioFromDB.getPer_Password());
	    		return true;
	    	}
	    	
	    }
	    //System.out.println("Size: " + personaList.size());
	    em.close();
		return false;
	}
}
