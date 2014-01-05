package com.tti.views;

import java.io.FileNotFoundException;

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
public class Perfil extends CustomComponent implements View {
	
	public static final String NAME = "perfil";
	private PanelDeControl panelDeControl;
	private static final Label descripcionLabel = new Label("<h2> Mis Datos </h2>", ContentMode.HTML);
	private String[] nombreCampos;
	private PerfilComponent datosUsuario;
	public MotorProcesos motor;
	public Perfil() throws FileNotFoundException {
//		motor = new MotorProcesos();
//		motor.Crear();
//		listaProc = new Label(motor.getInstancias());
		panelDeControl = new  PanelDeControl("username");
	}
	@Override
	public void enter(ViewChangeEvent event) {
		Rol userRol = getSession().getAttribute(Rol.class);
		panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
		nombreCampos = new String[] {"RUN", "Nombre", "Apellido",
				"Email", "Teléfono", "Fecha de Nacimiento", "Dirección",
				"Comuna", "Región"};
		datosUsuario = new PerfilComponent(nombreCampos);
		setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, datosUsuario));
			
	}

}
