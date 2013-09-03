package com.tti.test;

import java.util.List;

import com.tti.data.ApiDPA;
import com.tti.entidad.Sector;

public class probarClase {
	private static ApiDPA apiDpaConnection;
	public static void main(String[] args) {
		apiDpaConnection = new ApiDPA();
		List<Sector> listaComunas = apiDpaConnection.getComunas("", "131", "");
//		for (Sector comuna : listaComunas) {
//			System.out.println(comuna.getNombre()+ " - " + comuna.getCodigo() + " - " + comuna.getCodigo_padre());
//		}
		
		for (Sector comuna : listaComunas) {
			System.out.println(comuna.getNombre()+ " - " + comuna.getCodigo() + " - " + comuna.getCodigo_padre());
		}
	}
}
