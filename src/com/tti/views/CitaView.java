package com.tti.views;

import java.util.Date;

import com.tti.componentes.PanelDeControlAlumno;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;

@SuppressWarnings("serial")
public class CitaView extends CustomComponent implements View{
	
	public static final String NAME = "AgendarCita";
	public Calendar calendario;
	private PanelDeControlAlumno panelDeControl;
	public CitaView() {
		panelDeControl = new PanelDeControlAlumno("Karin Acuña");
		calendario = new Calendar();
		calendario.setSizeFull();
//		calendario.setStartDate(new Date());
//		calendario.setEndDate(new Date());
		setCompositionRoot(new CssLayout(panelDeControl, calendario));
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
