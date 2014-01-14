package com.tti.views;

import java.util.List;

import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.tti.reportes.ChartWithExternalContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class ReportesView extends CustomComponent implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3563773809359819689L;
	public static final String NAME = "reportes";
	private PanelDeControl panelDeControl;
	private static final Label descripcion = new Label("<h2> Reportes </h2>" + 
														"<p> Seleccione alguna de las opciones siguientes: </p>", ContentMode.HTML);
	@Override
	public void enter(ViewChangeEvent event) {
		List<Rol> userRol = (List<Rol>) getSession().getAttribute("roles");
		if(userRol.contains(Rol.SECRETARIA) || userRol.contains(Rol.DIRECTOR_DEPARTAMENTO)){		
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			Component ret = new ChartWithExternalContainer();
			setCompositionRoot(new CssLayout(panelDeControl, descripcion, ret));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
	}
}
