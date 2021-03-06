package com.tti.views;

import java.util.List;

import com.tti.componentes.GestorUsuariosComponent;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class ListadoProfesoresView extends CustomComponent implements View{
	
	public static final String NAME = "ListadoProfesores";
	private PanelDeControl panelDeControl;
	private GestorUsuariosComponent gestorUsuarios;
	private static final String RUN = "RUN";
	private static final String NOMBRE = "Nombre";
    private static final String APELLIDO = "Apellido";
    private static final String EMAIL = "Email";
    private static final Label descripcionLabel = new Label("<h2> Listado de Docentes Vigentes </h2>" + 
			"<p> A continuacion se listan los Profesores vigentes en TTi, para buscar a los no-vigentes utilice el filtro. </p>", ContentMode.HTML);
	private static final String[] nombreCampo = new String[] { RUN, NOMBRE, APELLIDO,
        EMAIL, "Carrera", "Tel�fono", "Fecha de Nacimiento", "Direcci�n",
        "Comuna", "Regi�n"};
	
	@Override
	public void enter(ViewChangeEvent event) {
		List<Rol> userRol = (List<Rol>) getSession().getAttribute("roles");
		if(userRol.contains(Rol.SECRETARIA) || userRol.contains(Rol.DIRECTOR_DEPARTAMENTO)){			
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			gestorUsuarios = new GestorUsuariosComponent(nombreCampo, null, true);
			setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, gestorUsuarios));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
		
	}

}