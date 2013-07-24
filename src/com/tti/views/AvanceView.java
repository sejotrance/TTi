package com.tti.views;

import com.tti.componentes.PanelDeControlAlumno;
import com.tti.reportes.AvanceChart;
import com.vaadin.client.ui.customcomponent.CustomComponentConnector;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
public class AvanceView extends CustomComponent implements View{
	
	public static final String NAME = "AvanceView";
	private PanelDeControlAlumno panelDeControl;
	private AvanceChart avanceChart;
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}public AvanceView() {
		panelDeControl = new PanelDeControlAlumno("Karin Acuña");
		avanceChart = new AvanceChart();
		setCompositionRoot(new CssLayout(panelDeControl, avanceChart));
	}
}
