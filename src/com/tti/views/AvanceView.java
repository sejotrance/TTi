package com.tti.views;

import com.tti.componentes.PanelDeControl;
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
		panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")));
		setCompositionRoot(new CssLayout(panelDeControl, avanceChart));
		
	}public AvanceView() {
		panelDeControl = new PanelDeControl("username");
		avanceChart = new AvanceChart();
		setCompositionRoot(new CssLayout(panelDeControl, avanceChart));
	}
}
