package com.tti.enums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.ArrayType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tti.data.Region;

public class Regiones {

	private Gson gson = new Gson();
	
	public Regiones() {
		getDatos();
	}
	
	private void getDatos(){
		 try {
			 
				URL url = new URL("http://apis.modernizacion.cl/dpa/regiones");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
		 
				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}
		 
				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
		 
				String output;
				
				System.out.println("Output from Server .... \n");
				//convert the json string back to object
				Type listType = new TypeToken<ArrayList<Region>>() {
                }.getType();
				List<Region> obj = gson.fromJson(br, listType);
//				while ((output = br.readLine()) != null) {
//					System.out.println(output);
//				}
				for (Region region : obj) {
					System.out.println(region.getNombre());
				}
				
		 		
				conn.disconnect();
		 
			  } catch (MalformedURLException e) {
		 
				e.printStackTrace();
		 
			  } catch (IOException e) {
		 
				e.printStackTrace();
		 
			  }
	}
}

