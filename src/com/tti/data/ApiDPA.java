package com.tti.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tti.entidad.Sector;

public class ApiDPA {

	private Gson gson;
	
	public ApiDPA() {
		gson = new Gson();
	}
	
	public List<Sector> getRegiones(String codRegion){
	 	if(codRegion == null){
	 		codRegion = "";
	 	}
		List<Sector> regiones = new ArrayList<Sector>(); 
		try {	
			//Listado de las regiones, o en el caso de venir vacía Representación de una única Región
				URL url = new URL("http://apis.modernizacion.cl/dpa/regiones/" + codRegion);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
		 
				if(conn.getResponseCode() != 200){
					throw new RuntimeException("Error : HTTP código error : "
							+ conn.getResponseCode());
				}
		 
				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
				
				Type listType = new TypeToken<ArrayList<Sector>>() {
                }.getType();
				regiones = gson.fromJson(br, listType);

				conn.disconnect();
				
			  } catch (MalformedURLException e) {
				e.printStackTrace();
		 
			  } catch (IOException e) {
		 
				e.printStackTrace();
		 
			  }
		 return regiones;
	}
	

	public List<Sector> getProvincias(String codRegion, String codProvincia){
	 	if(codRegion == null){
	 		codRegion = "";
	 	}
	 	
	 	if(codProvincia == null){
	 		codProvincia = "";
	 	}
		List<Sector> provincias = new ArrayList<Sector>(); 
		try {	URL url;
				if((codRegion != "") && (codProvincia != "")){
					//Representación de una única Provincia perteneciente a una Región
					url = new URL("http://apis.modernizacion.cl/dpa/provincias/" + codRegion + "/provincias/" + codProvincia);
				}else if(codRegion != ""){
					//Listado de las Provincias pertenecientes a una Región
					url = new URL("http://apis.modernizacion.cl/dpa/regiones/" + codRegion + "/provincias");
				}else if(codProvincia != ""){
					//Representación de una única Provincia
					url = new URL("http://apis.modernizacion.cl/dpa/provincias/" + codProvincia);
				}else{
					//Listado de las Provincias
					url = new URL("http://apis.modernizacion.cl/dpa/provincias/");
				}
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
		 
				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Error : HTTP código error : "
							+ conn.getResponseCode());
				}
		 
				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
				
				Type listType = new TypeToken<ArrayList<Sector>>() {
                }.getType();
				provincias = gson.fromJson(br, listType);

				conn.disconnect();
				
			  } catch (MalformedURLException e) {
				e.printStackTrace();
		 
			  } catch (IOException e) {
		 
				e.printStackTrace();
		 
			  }
		 return provincias;
	}
	
	public List<Sector> getComunas(String codRegion, String codProvincia, String codComuna){
	 	if(codRegion == null){
	 		codRegion = "";
	 	}
	 	
	 	if(codProvincia == null){
	 		codProvincia = "";
	 	}
	 	if(codComuna == null){
	 		codComuna = "";
	 	}
		List<Sector> comunas = new ArrayList<Sector>(); 
		try {	URL url;
				if((codRegion != "") && (codProvincia != "") && (codComuna != "")){
					//Representación de una única Comuna perteneciente a una Provincia que a su vez pertenece a una Región
					url = new URL("http://apis.modernizacion.cl/dpa/regiones/" + codRegion + "/provincias/" + codProvincia + "/comunas/" + codComuna);
				}else if((codRegion != "") && (codProvincia != "")){
					//Listado de las Comunas pertenecientes a una Provincia que a su vez pertenece a una Región
					url = new URL("http://apis.modernizacion.cl/dpa/regiones/" + codRegion + "/provincias/" + codProvincia + "/comunas");
				}else if((codProvincia != "") && (codComuna != "")){
					//Representación de una única Comuna perteneciente a una Provincia
					url = new URL("http://apis.modernizacion.cl/dpa/provincias/" + codProvincia + "/comunas/" + codComuna);
				}else if((codRegion != "") && (codComuna != "")){
					//Representación de una única Comuna perteneciente a una Región
					url = new URL("http://apis.modernizacion.cl/dpa/regiones/" + codRegion + "/comunas/" + codComuna);
				}else if(codRegion != ""){
					//Listado de las Comunas pertenecientes a una Región
					url = new URL("http://apis.modernizacion.cl/dpa/regiones/" + codRegion + "/comunas");
				}else if(codProvincia != ""){
					//Listado de las Comunas pertenecientes a una Provincia
					url = new URL("http://apis.modernizacion.cl/dpa/provincias/" + codProvincia + "/comunas");
				}else if(codComuna != ""){
					//Representación de una única Comuna
					url = new URL("http://apis.modernizacion.cl/dpa/comunas/" + codComuna);
				}else{
					//Listado de las Comunas.
					url = new URL("http://apis.modernizacion.cl/dpa/comunas");
				}
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
		 
				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Error : HTTP código error : "
							+ conn.getResponseCode());
				}
		 
				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
				
				Type listType = new TypeToken<ArrayList<Sector>>() {
                }.getType();
				comunas = gson.fromJson(br, listType);

				conn.disconnect();
				
			  } catch (MalformedURLException e) {
				e.printStackTrace();
		 
			  } catch (IOException e) {
		 
				e.printStackTrace();
		 
			  }
		 return comunas;
	}
	
	
	
}

