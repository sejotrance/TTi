package com.tti.views;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.GregorianCalendar;

import com.processEngine.MotorProcesos;
import com.tti.componentes.PanelDeControlAlumno;
import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventClick;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.WeekClick;
import com.vaadin.event.Action;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;

@SuppressWarnings("serial")
public class CitaView extends CustomComponent implements View{
	
	public static final String NAME = "AgendarCita";
	public Calendar calendario;
	private PanelDeControlAlumno panelDeControl;
	private MotorProcesos motor;
	public CitaView() throws FileNotFoundException {
		panelDeControl = new PanelDeControlAlumno("Karin Acuña");
		calendario = new Calendar();
		calendario.setSizeFull();
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end   = new GregorianCalendar();
		
		end.add(java.util.Calendar.HOUR, 2);
		calendario.addEvent(new BasicEvent("Reunión definición TT",
		        "El objetivo es definir el tema del trabajo de título",
		        start.getTime(), end.getTime()));
		start.add(java.util.Calendar.DATE, 2);	
		start.add(java.util.Calendar.HOUR, 1);
		end.add(java.util.Calendar.DATE, 2);
		end.add(java.util.Calendar.HOUR, 2);
		calendario.addEvent(new BasicEvent("Revisión Avance",
		        "Primera reunión evaluada",
		        start.getTime(), end.getTime()));
//		motor = new MotorProcesos();
//		motor.makeDeployment();
//		calendario.setStartDate(new Date());
//		calendario.setEndDate(new Date());
		setCompositionRoot(new CssLayout(panelDeControl, calendario));
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
