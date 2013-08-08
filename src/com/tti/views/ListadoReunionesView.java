package com.tti.views;

import com.tti.TtiUI;
import com.tti.componentes.PanelDeControlAlumno;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;


public class ListadoReunionesView extends CustomComponent implements View
{
	private static final long serialVersionUID = 334994619924447166L;
	public static final String NAME = "ListadoReuniones";
	private PanelDeControlAlumno panelDeControl;
	private Table listadoReuniones;
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public ListadoReunionesView() {
		listadoReuniones = new Table("Histórico de Reuniones");
		listadoReuniones.setContainerDataSource(TtiUI.container);
		panelDeControl = new PanelDeControlAlumno("Karin Acuña");
		setCompositionRoot(new CssLayout(panelDeControl, listadoReuniones));
	}
	
    
}
