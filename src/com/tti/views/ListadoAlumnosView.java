package com.tti.views;

import com.tti.componentes.GestorUsuariosComponent;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;

public class ListadoAlumnosView extends CustomComponent implements View{
	
	public static final String NAME = "ListadoAlumnos";
	private PanelDeControl panelDeControl;
	private GestorUsuariosComponent gestorUsuarios;
	private static final String NOMBRE = "Nombre";
    private static final String APELLIDO = "Apellido";
    private static final String EMAIL = "Email";
	private static final String[] nombreCampo = new String[] { NOMBRE, APELLIDO,
        EMAIL, "Carrera", "Teléfono", "Fecha de Nacimiento", "Dirección",
        "Comuna", "Región"};
	
	@Override
	public void enter(ViewChangeEvent event) {
		Rol userRol = getSession().getAttribute(Rol.class);
		if(userRol == Rol.SECRETARIA){		
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			gestorUsuarios = new GestorUsuariosComponent(nombreCampo, null, true);
			setCompositionRoot(new CssLayout(panelDeControl, gestorUsuarios));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
		
	}

}
