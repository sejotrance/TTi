package com.tti.views;

import java.io.FileNotFoundException;

import com.processEngine.MotorProcesos;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.PerfilComponent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class Perfil extends CustomComponent implements View {
	
	public static final String NAME = "perfil";
	private PanelDeControl panelDeControl;
	private Label texto;
//	private Label listaProc;
	private PerfilComponent datos;
	public MotorProcesos motor;
	public Perfil() throws FileNotFoundException {
//		motor = new MotorProcesos();
//		motor.Crear();
		texto = new Label("MIS DATOS DE PERFIL");
//		listaProc = new Label(motor.getInstancias());
		panelDeControl = new  PanelDeControl("username");
		datos = new PerfilComponent();
		setCompositionRoot(new CssLayout(panelDeControl, texto, datos));
	}
	@Override
	public void enter(ViewChangeEvent event) {
		panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")));
		
	}

}
