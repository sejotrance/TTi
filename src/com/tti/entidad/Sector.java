package com.tti.entidad;

public class Sector {
	
	private String codigo;
	private String tipo;
	private String nombre;
	private String lat;
	private String lng;
	private String url;
	private String codigo_padre;
	
	public Sector(String codigo, String tipo, String nombre, String lat, String lng, String url, String codigo_padre){
		this.codigo = codigo;
		this.tipo = tipo;
		this.nombre = nombre;
		this.lat = lat;
		this.lng = lng;
		this.url = url;
		this.codigo_padre = codigo_padre;
	}
	
	public Sector() {
		this.codigo = "";
		this.tipo = "";
		this.nombre = "";
		this.lat = "";
		this.lng = "";
		this.url = "";
		this.codigo_padre = "";
	}
	
	//GETTERS
	public String getCodigo() {
		return codigo;
	}
	public String getTipo() {
		return tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public String getLat() {
		return lat;
	}
	public String getLng() {
		return lng;
	}
	public String getUrl() {
		return url;
	}
	public String getCodigo_padre() {
		return codigo_padre;
	}
	
	//SETTERS
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setCodigo_padre(String codigo_padre) {
		this.codigo_padre = codigo_padre;
	}
	
}
