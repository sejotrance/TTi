package com.tti.views;

import java.util.List;

import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.tti.reportes.AvanceChart;
import com.vaadin.client.ui.customcomponent.CustomComponentConnector;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
public class AvanceView extends CustomComponent implements View{
	
	public static final String NAME = "miavance";
	private PanelDeControl panelDeControl;
	private AvanceChart avanceChart;
	private static Label descripcionLabel;
	private String nombreProyecto;
	
	@Override
	public void enter(ViewChangeEvent event) {
		List<Rol> userRol = (List<Rol>) getSession().getAttribute("roles");
		if(userRol.contains(Rol.ALUMNO)){	
			nombreProyecto = "Sistema de Seguimiento al Trabajo de Título";
			descripcionLabel = new Label("<h2>" + nombreProyecto + "</h2>", ContentMode.HTML);
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, avanceChart));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
		
	}public AvanceView() {
		nombreProyecto = "SIN NOMBRE";
		descripcionLabel = new Label("<h2>" + nombreProyecto + "</h2>", ContentMode.HTML);
		panelDeControl = new PanelDeControl("username");
		avanceChart = new AvanceChart();
		setCompositionRoot(new CssLayout(panelDeControl,descripcionLabel, avanceChart));
	}
}
