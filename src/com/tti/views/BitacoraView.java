package com.tti.views;

import java.util.List;

import com.tti.TtiUI;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class BitacoraView extends CustomComponent implements View{
	
	private static final long serialVersionUID = 1879144049248715609L;
	public static final String NAME = "BitacoraReunion";
	private PanelDeControl panelDeControl;
	private Table listadoReuniones;
	private BeanItemContainer<BasicEvent> containerAUX;
	private static final Label descripcionLabel = new Label("<h2> Reuniones del día de hoy </h2>" + 
			"<p> Del siguiente listado seleccione una reunión para realizar anotaciones </p>", ContentMode.HTML);
	@Override
	public void enter(ViewChangeEvent event) {
		List<Rol> userRol = (List<Rol>) getSession().getAttribute("roles");
		if(userRol.contains(Rol.PROFESOR)){			
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, listadoReuniones));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
	}
	
	public BitacoraView() {
		panelDeControl = new PanelDeControl("username");
		listadoReuniones = new Table("Histórico de Reuniones");
		listadoReuniones.setContainerDataSource(TtiUI.container);
		listadoReuniones.setVisibleColumns(new String[]{"caption", "description", "start", "end"});
		listadoReuniones.setColumnHeader("caption", "Asunto");
		listadoReuniones.setColumnHeader("description", "Descripcion");
		listadoReuniones.setColumnHeader("start", "Fecha Inicio");
		listadoReuniones.setColumnHeader("end", "Fecha Fin");
		setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, listadoReuniones));
	}
}
