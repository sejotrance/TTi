package com.tti.views;

import com.tti.TtiUI;
import com.tti.componentes.PanelDeControl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;


public class ListadoReunionesView extends CustomComponent implements View
{
	private static final long serialVersionUID = 334994619924447166L;
	public static final String NAME = "ListadoReuniones";
	private PanelDeControl panelDeControl;
	private Table listadoReuniones;
	@Override
	public void enter(ViewChangeEvent event) {
		panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")));
		
	}
	
	public ListadoReunionesView() {
		listadoReuniones = new Table("Histórico de Reuniones");
		listadoReuniones.setContainerDataSource(TtiUI.container);
		panelDeControl = new PanelDeControl("username");
		setCompositionRoot(new CssLayout(panelDeControl, listadoReuniones));
	}
	
    
}
