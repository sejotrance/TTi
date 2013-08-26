package com.tti.views;

import com.tti.componentes.PanelDeControl;
import com.tti.enums.Rol;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

public class RegistroAlumnoView extends CustomComponent implements View{
	
	private static final long serialVersionUID = -8559029417440049381L;
	public static final String NAME = "RegistrarAlumno";
	private PanelDeControl panelDeControl;
	
	@Override
	public void enter(ViewChangeEvent event) {
		panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")),getSession().getAttribute(Rol.class));
	}

}
