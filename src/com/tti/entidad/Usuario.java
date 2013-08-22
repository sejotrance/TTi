package com.tti.entidad;

import com.tti.enums.Rol;

public class Usuario {
	private Rol rolUsuario;
	private String username;
	private String password;
	
	public Usuario() {
		rolUsuario = Rol.UNDEFINED;
		username = "";
		password = "";
	}
	
	public Usuario(String username, String password, Rol rolUsuario){
		this.username = username;
		this.password = password;
		this.rolUsuario = rolUsuario;		
	}
	/************* SETTERS ******************/
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setRolUsuario(Rol rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	/************* GETTERS ******************/
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Rol getRolUsuario() {
		return rolUsuario;
	}
	
}
