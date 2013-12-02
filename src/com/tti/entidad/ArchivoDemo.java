package com.tti.entidad;

import java.util.Date;

public class ArchivoDemo {
	private int id;
	private String nombre;
	private String path;
	private String tipo;
	private Date fecha;
	
	public ArchivoDemo(int id, String nombre, String path, String tipo, Date fecha) {
		this.id = id;
		this.nombre = nombre;
		this.path = path;
		this.tipo = tipo;
		this.fecha = fecha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
