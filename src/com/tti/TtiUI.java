package com.tti;

import java.util.Date;
import java.util.GregorianCalendar;

import com.tti.views.AvanceView;
import com.tti.views.CitaView;
import com.tti.views.ListadoAlumnosView;
import com.tti.views.ListadoReunionesView;
import com.tti.views.Perfil;
import com.tti.views.RegistroAlumnoView;
import com.tti.views.ReprogramarView;
import com.tti.views.SubirInformeView;
import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Main UI class
 */
@Theme("bootstrap")
@SuppressWarnings("serial")
public class TtiUI extends UI {
	private Calendar calendario;
	private GregorianCalendar calendar;
	public static BeanItemContainer<BasicEvent> container;
	
	
	 	@Override
	    protected void init(VaadinRequest request) {

	        //
	        // Create a new instance of the navigator. The navigator will attach
	        // itself automatically to this view.
	        //
	        new Navigator(this, this);

	        //
	        calendario = new Calendar();
	        calendar = new GregorianCalendar();
	        container = new BeanItemContainer<BasicEvent>(BasicEvent.class);
	        addInitialEvents();
	        
	        // The initial log view where the user can login to the application
	        //
	        getNavigator().addView(SimpleLoginView.NAME, SimpleLoginView.class);

	        //
	        // Add the main view of the application
	        //
	        getNavigator().addView(SimpleLoginMainView.NAME, SimpleLoginMainView.class);
	        getNavigator().addView(Perfil.NAME, Perfil.class);
	        getNavigator().addView(CitaView.NAME, CitaView.class);
	        getNavigator().addView(ReprogramarView.NAME, ReprogramarView.class);
	        getNavigator().addView(AvanceView.NAME, AvanceView.class);
	        getNavigator().addView(ListadoReunionesView.NAME, ListadoReunionesView.class);
	        getNavigator().addView(SubirInformeView.NAME, SubirInformeView.class);
	        getNavigator().addView(RegistroAlumnoView.NAME, RegistroAlumnoView.class);
	        getNavigator().addView(ListadoAlumnosView.NAME, ListadoAlumnosView.class);
	                       
	        //
	        // We use a view change handler to ensure the user is always redirected
	        // to the login view if the user is not logged in.
	        //
	        getNavigator().addViewChangeListener(new ViewChangeListener() {
	            
	            @Override
	            public boolean beforeViewChange(ViewChangeEvent event) {
	                
	                // Check if a user has logged in
	                boolean isLoggedIn = getSession().getAttribute("user") != null;
	                boolean isLoginView = event.getNewView() instanceof SimpleLoginView;

	                if (!isLoggedIn && !isLoginView) {
	                    // Redirect to login view always if a user has not yet
	                    // logged in
	                    getNavigator().navigateTo(SimpleLoginView.NAME);
	                    return false;

	                } else if (isLoggedIn && isLoginView) {
	                    // If someone tries to access to login view while logged in,
	                    // then cancel
	                    return false;
	                }

	                return true;
	            }
	            
	            @Override
	            public void afterViewChange(ViewChangeEvent event) {
	                
	            }
	        });
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
	        container.sort(new Object[]{"start"}, new boolean[]{true});

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
	        container.sort(new Object[]{"start"}, new boolean[]{true});

	        // Add a second allday event
	        calendar.add(GregorianCalendar.DATE, 1);
	        start = calendar.getTime();
	        end = start;
	        event = getNewEvent("Día de la ingenieria", start, end);
	        event.setAllDay(true);
	        event.setDescription("Otro evento de día completo.");
	        event.setStyleName("color2");
	        container.addBean(event);
	        container.sort(new Object[]{"start"}, new boolean[]{true});

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
	        container.sort(new Object[]{"start"}, new boolean[]{true});

	        calendar.add(GregorianCalendar.DATE, 1);
	        calendar.set(GregorianCalendar.HOUR_OF_DAY, 11);
	        calendar.set(GregorianCalendar.MINUTE, 0);
	        start = calendar.getTime();
	        calendar.add(GregorianCalendar.HOUR_OF_DAY, 8);
	        end = calendar.getTime();
	        event = getNewEvent("Pequeña reunion", start, end);
	        event.setStyleName("color2");
	        container.addBean(event);
	        container.sort(new Object[]{"start"}, new boolean[]{true});

	        calendar.add(GregorianCalendar.DATE, 4);
	        calendar.set(GregorianCalendar.HOUR_OF_DAY, 9);
	        calendar.set(GregorianCalendar.MINUTE, 0);
	        start = calendar.getTime();
	        calendar.add(GregorianCalendar.HOUR_OF_DAY, 9);
	        end = calendar.getTime();
	        event = getNewEvent("Sólo para ver una duda de mi informe", start, end);
	        container.addBean(event);
	        container.sort(new Object[]{"start"}, new boolean[]{true});
	    }
		private BasicEvent getNewEvent(String caption, Date start, Date end) {
			BasicEvent event = new BasicEvent();
		    event.setCaption(caption);
		    event.setStart(start);
		    event.setEnd(end);
		
		    return event;
		}

}