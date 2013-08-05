package com.tti.views;

import java.util.Date;
import java.util.GregorianCalendar;

import com.tti.componentes.PanelDeControlAlumno;
import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;


public class ListadoReunionesView extends CustomComponent implements View
{
	private static final long serialVersionUID = 334994619924447166L;
	public static final String NAME = "ListadoReuniones";
	private PanelDeControlAlumno panelDeControl;
	public Calendar calendario;
	private GregorianCalendar calendar;
	private Table listadoReuniones;
	final BeanItemContainer<BasicEvent> container;
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public ListadoReunionesView() {
		calendario = new Calendar();
		container =	new BeanItemContainer<BasicEvent>(BasicEvent.class);
		listadoReuniones = new Table("Histórico de Reuniones", container);
		calendar = new GregorianCalendar();
		addInitialEvents();
		panelDeControl = new PanelDeControlAlumno("Karin Acuña");
		setCompositionRoot(new CssLayout(panelDeControl, listadoReuniones));
	}
	
    private void addInitialEvents() {
    	Date today = new Date();
        // Add a event that last a whole week
        Date start = calendario.getFirstDateForWeek(today);
        Date end = calendario.getLastDateForWeek(today);
        BasicEvent event = getNewEvent("Evento de Semana completa", start, end);
        event.setAllDay(true);
        event.setStyleName("color4");
        event.setDescription("Este evento tiene una duración que abarca la semana completa.");
        container.addBean(event);

        // Add a allday event
        calendar.setTime(start);
        calendar.add(GregorianCalendar.DATE, 3);
        start = calendar.getTime();
        end = start;
        event = getNewEvent("Reuniones de Acreditación", start, end);
        event.setAllDay(true);
        event.setDescription("Evento de día completo");
        event.setStyleName("color3");
        container.addBean(event);

        // Add a second allday event
        calendar.add(GregorianCalendar.DATE, 1);
        start = calendar.getTime();
        end = start;
        event = getNewEvent("Día de la ingenieria", start, end);
        event.setAllDay(true);
        event.setDescription("Otro evento de día completo.");
        event.setStyleName("color2");
        container.addBean(event);

        calendar.add(GregorianCalendar.DATE, -3);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, 9);
        calendar.set(GregorianCalendar.MINUTE, 30);
        start = calendar.getTime();
        calendar.add(GregorianCalendar.HOUR_OF_DAY, 5);
        calendar.set(GregorianCalendar.MINUTE, 0);
        end = calendar.getTime();
        event = getNewEvent("Reunión de Avance", start, end);
        event.setStyleName("color1");
        event.setDescription("Quisiera que revisaramos si estoy cumpliendo con los alcances del proyecto.");
        container.addBean(event);

        calendar.add(GregorianCalendar.DATE, 1);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, 11);
        calendar.set(GregorianCalendar.MINUTE, 0);
        start = calendar.getTime();
        calendar.add(GregorianCalendar.HOUR_OF_DAY, 8);
        end = calendar.getTime();
        event = getNewEvent("Pequeña reunion", start, end);
        event.setStyleName("color2");
        container.addBean(event);

        calendar.add(GregorianCalendar.DATE, 4);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, 9);
        calendar.set(GregorianCalendar.MINUTE, 0);
        start = calendar.getTime();
        calendar.add(GregorianCalendar.HOUR_OF_DAY, 9);
        end = calendar.getTime();
        event = getNewEvent("Sólo para ver una duda de mi informe", start, end);
        container.addBean(event);
    }
	private BasicEvent getNewEvent(String caption, Date start, Date end) {
		BasicEvent event = new BasicEvent();
	    event.setCaption(caption);
	    event.setStart(start);
	    event.setEnd(end);
	
	    return event;
	}
}
