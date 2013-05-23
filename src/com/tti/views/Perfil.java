package com.tti.views;

import com.tti.componentes.PanelDeControlAlumno;
import com.tti.componentes.PerfilComponent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class Perfil extends CustomComponent implements View {
	
	public static final String NAME = "perfil";
	private PanelDeControlAlumno panelDeControl;
	private Label texto;
	private PerfilComponent datos;
	
	public Perfil() {
		texto = new Label("MIS DATOS DE PERFIL");
		panelDeControl = new  PanelDeControlAlumno("Karin Acuña");
		datos = new PerfilComponent();
		setCompositionRoot(new CssLayout(panelDeControl, texto, datos));
	}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
