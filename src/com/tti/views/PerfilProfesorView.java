package com.tti.views;

import java.io.FileNotFoundException;
import java.util.List;

import com.processEngine.MotorProcesos;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.PerfilComponent;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class PerfilProfesorView extends CustomComponent implements View {
	
	public static final String NAME = "miprofesor";
	private PanelDeControl panelDeControl;
	private static final Label descripcionLabel = new Label("<h2> Mi Profesor Guía </h2>", ContentMode.HTML);
	private String[] nombreCampos;
	private PerfilComponent datosUsuario;
	public MotorProcesos motor;
	public PerfilProfesorView() throws FileNotFoundException {
		panelDeControl = new  PanelDeControl("username");
	}
	@Override
	public void enter(ViewChangeEvent event) {
		List<Rol> userRol = (List<Rol>) getSession().getAttribute("roles");
		if(userRol.contains(Rol.ALUMNO)){	
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			nombreCampos = new String[] {"<b>Nombre:</b> Mauro", "<b>Apellido:</b> Castillo Valdés",
					"<b>Email:</b> mcast@utem.cl", "<b>Teléfono:</b> 02-21234567"};
			datosUsuario = new PerfilComponent(nombreCampos, true);
			datosUsuario.setWidth("");
			setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, datosUsuario));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
		
			
	}

}

