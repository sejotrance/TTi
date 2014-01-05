package com.tti.views;

import com.tti.TtiUI;
import com.tti.componentes.GestorUsuariosComponent;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
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
		Rol userRol = getSession().getAttribute(Rol.class);
		if(userRol == Rol.ALUMNO || userRol == Rol.PROFESOR){		
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			setCompositionRoot(new CssLayout(panelDeControl, listadoReuniones));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
	}
	
	public ListadoReunionesView() {
		listadoReuniones = new Table("Histórico de Reuniones");
		listadoReuniones.setContainerDataSource(TtiUI.container);
		panelDeControl = new PanelDeControl("username");
		setCompositionRoot(new CssLayout(panelDeControl, listadoReuniones));
	}
	
    
}
