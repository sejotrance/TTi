package com.tti.entidad;

import java.util.ArrayList;
import java.util.List;

import com.tti.enums.Rol;

public class Usuario {
	private List<Rol> rolesUsuario;
	private String username;
	private String password;
	
	public Usuario() {
		rolesUsuario = new ArrayList<Rol>();
		username = "";
		password = "";
	}
	
	public Usuario(String username, String password, List<Rol> rolesUsuario){
		this.username = username;
		this.password = password;
		this.rolesUsuario = rolesUsuario;		
	}
	
	/************* SETTERS ******************/
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setRolesUsuario(List<Rol> rolesUsuario) {
		this.rolesUsuario = rolesUsuario;
	}
	
	public void addRolUsuario(Rol rolUsuario) {
		this.rolesUsuario.add(rolUsuario);
	}
	
	/************* GETTERS ******************/
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public List<Rol> getRolesUsuario() {
		return rolesUsuario;
	}
	
}
