package com.tti.views;

import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.tti.reportes.AvanceChart;
import com.vaadin.client.ui.customcomponent.CustomComponentConnector;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
public class AvanceView extends CustomComponent implements View{
	
	public static final String NAME = "AvanceView";
	private PanelDeControl panelDeControl;
	private AvanceChart avanceChart;
	
	@Override
	public void enter(ViewChangeEvent event) {
		Rol userRol = getSession().getAttribute(Rol.class);
		if(userRol == Rol.ALUMNO){		
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			setCompositionRoot(new CssLayout(panelDeControl, avanceChart));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
		
	}public AvanceView() {
		panelDeControl = new PanelDeControl("username");
		avanceChart = new AvanceChart();
		setCompositionRoot(new CssLayout(panelDeControl, avanceChart));
	}
}
