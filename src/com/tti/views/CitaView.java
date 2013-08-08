package com.tti.views;

import java.io.FileNotFoundException;
import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.tti.TtiUI;
import com.tti.componentes.PanelDeControlAlumno;
import com.tti.windows.CitaWindow;
import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.addon.calendar.event.BasicEventProvider;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.DateClickEvent;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.DateClickHandler;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventClick;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventClickHandler;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.MoveEvent;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.WeekClick;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.WeekClickHandler;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

@SuppressWarnings("serial")
public class CitaView extends CustomComponent implements View{
	
	public static final String NAME = "AgendarCita";
	private enum Mode {
        MONTH, WEEK, DAY;
    }
	private Mode viewMode;
	public Label mesLabel;
	private CitaWindow cita;
	
	private BasicEventProvider dataSource;
    public Calendar calendario;
	private GregorianCalendar calendar;
	public Button botonMes = new Button("Mes");
	public Button botonSem = new Button("Semana");
	public Button botonSig = new Button("Siguiente");
	public Button botonPrev = new Button("Atrás");
	public Button nuevaReunion = new Button("Nueva reunión");
	private PanelDeControlAlumno panelDeControl;
	private Date currentMonthsFirstDate;
	
	public CitaView() throws FileNotFoundException {
		panelDeControl = new PanelDeControlAlumno("Karin Acuña");
		viewMode = Mode.WEEK;
		
		botonMes.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		        setVistaMes();
		    }
		});
		
		botonSem.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
                // simulate week click
                WeekClickHandler handler = (WeekClickHandler) calendario
                        .getHandler(WeekClick.EVENT_ID);
                handler.weekClick(new WeekClick(calendario, calendar
                        .get(GregorianCalendar.WEEK_OF_YEAR), calendar
                        .get(GregorianCalendar.YEAR)));
            }
		});
		
		botonSig.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		        nextMonth();
		    }
		});
		
		botonPrev.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		        previousMonth();
		    }
		});
		
		
		nuevaReunion.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				BasicEvent nuevaReunion = new BasicEvent();
				nuevaReunion.setStart(new Date());
				nuevaReunion.setEnd(new Date());
				creaPopupCita(nuevaReunion);
				 
            }
		});
		
			
		mesLabel = new Label();
		dataSource = new BasicEventProvider();
		calendario = new Calendar();
		TtiUI.container.sort(new Object[]{"start"}, new boolean[]{true});

		calendario.setContainerDataSource(TtiUI.container, "caption",
		    "description", "start", "end", "styleName");
		calendario.setSizeFull();
		calendario.setLocale(Locale.getDefault());
		calendar = new GregorianCalendar();
		GregorianCalendar start = calendar;
		GregorianCalendar end   = calendar;
		
		end.add(java.util.Calendar.HOUR, 2);
		
		calendario.setHandler(new DateClickHandler() {
			
			@Override
			public void dateClick(DateClickEvent event) {
				Date fechaDiaSelec = event.getDate();
				BasicEvent nuevaReunion = new BasicEvent();
				nuevaReunion.setStart(fechaDiaSelec);
				nuevaReunion.setEnd(fechaDiaSelec);
				creaPopupCita(nuevaReunion);
				
			}
		});
		
		
		calendario.setHandler(new EventClickHandler() {
		    public void eventClick(EventClick event) {
		        BasicEvent e = (BasicEvent) event.getCalendarEvent();
	        	
		        creaPopupCita(e);
		    }
		});
		
		calendario.setHandler(new RangeSelectHandler() {
		
			public void rangeSelect(RangeSelectEvent event) {
				 BasicEvent calendarEvent = new BasicEvent();
				 calendarEvent.setStart(event.getStart());
				 calendarEvent.setEnd(event.getEnd());
				 creaPopupCita(calendarEvent);
				
			}
		});
		
		setCompositionRoot(new CssLayout(panelDeControl, mesLabel, nuevaReunion, botonMes, botonSem, botonPrev, botonSig, calendario));

        updateMesLabel();
        
        botonMes.setVisible(viewMode == Mode.WEEK);
        botonSem.setVisible(viewMode == Mode.DAY);
        
        
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
		
	}
	
	private void setVistaMes(){
		calendar = new GregorianCalendar(getLocale());
        calendar.setTime(new Date());
        
		int rollAmount = calendar.get(GregorianCalendar.DAY_OF_MONTH) - 1;
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -rollAmount);
        currentMonthsFirstDate = calendar.getTime();
        calendario.setStartDate(currentMonthsFirstDate);
        calendar.add(GregorianCalendar.MONTH, 1);
        calendar.add(GregorianCalendar.DATE, -1);
        calendario.setEndDate(calendar.getTime());
        updateMesLabel();
	}
		
	
	private void updateMesLabel() {
        DateFormatSymbols s = new DateFormatSymbols(calendario.getLocale());
        String month = s.getMonths()[calendar.get(GregorianCalendar.MONTH)];
        mesLabel.setValue(month + " "
                + calendar.get(GregorianCalendar.YEAR));
    }
	
	private void nextMonth() {
        rollMonth(1);
    }

    private void previousMonth() {
        rollMonth(-1);
    }

    private void nextWeek() {
        rollWeek(1);
    }

    private void previousWeek() {
        rollWeek(-1);
    }

    private void nextDay() {
        rollDate(1);
    }

    private void previousDay() {
        rollDate(-1);
    }

    private void rollMonth(int direction) {
        calendar.setTime(currentMonthsFirstDate);
        calendar.add(GregorianCalendar.MONTH, direction);
        resetTime(false);
        currentMonthsFirstDate = calendar.getTime();
        calendario.setStartDate(currentMonthsFirstDate);

        updateMesLabel();

        calendar.add(GregorianCalendar.MONTH, 1);
        calendar.add(GregorianCalendar.DATE, -1);
        resetCalendarTime(true);
    }
    
    private void rollWeek(int direction) {
        calendar.add(GregorianCalendar.WEEK_OF_YEAR, direction);
        calendar.set(GregorianCalendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek());
        resetCalendarTime(false);
        resetTime(true);
        calendar.add(GregorianCalendar.DATE, 6);
        calendario.setEndDate(calendar.getTime());
    }

    private void rollDate(int direction) {
        calendar.add(GregorianCalendar.DATE, direction);
        resetCalendarTime(false);
        resetCalendarTime(true);
    }
    
    private void resetCalendarTime(boolean resetEndTime) {
        resetTime(resetEndTime);
        if (resetEndTime) {
            calendario.setEndDate(calendar.getTime());
        } else {
            calendario.setStartDate(calendar.getTime());
            updateMesLabel();
        }
    }
    
    

    /*
     * Resets the calendar time (hour, minute second and millisecond) either to
     * zero or maximum value.
     */
    private void resetTime(boolean max) {
        if (max) {
            calendar.set(GregorianCalendar.HOUR_OF_DAY,
                    calendar.getMaximum(GregorianCalendar.HOUR_OF_DAY));
            calendar.set(GregorianCalendar.MINUTE,
                    calendar.getMaximum(GregorianCalendar.MINUTE));
            calendar.set(GregorianCalendar.SECOND,
                    calendar.getMaximum(GregorianCalendar.SECOND));
            calendar.set(GregorianCalendar.MILLISECOND,
                    calendar.getMaximum(GregorianCalendar.MILLISECOND));
        } else {
            calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
            calendar.set(GregorianCalendar.MINUTE, 0);
            calendar.set(GregorianCalendar.SECOND, 0);
            calendar.set(GregorianCalendar.MILLISECOND, 0);
        }
    }
    
    private void handleNextButtonClick() {
        switch (viewMode) {
        case MONTH:
            nextMonth();
            break;
        case WEEK:
            nextWeek();
            break;
        case DAY:
            nextDay();
            break;
        }
    }

    private void handlePreviousButtonClick() {
        switch (viewMode) {
        case MONTH:
            previousMonth();
            break;
        case WEEK:
            previousWeek();
            break;
        case DAY:
            previousDay();
            break;
        }
    }
    
    private void creaPopupCita(BasicEvent e){
		 cita = new CitaWindow();
		 cita.setFechaDesde(e.getStart());
		 cita.setFechaHasta(e.getEnd());
		 if(e.getCaption() != null){
			 cita.setAsunto(e.getCaption());
		 }else cita.setAsunto("");
		 if(e.getDescription() != null){
			 cita.setDescripcion(e.getDescription());
		 }else{
			 cita.setDescripcion("");
		 }
		 cita.setDiaCompleto(e.isAllDay());
		 cita.setModal(true);
		 cita.setWidth("300px");
		 cita.setHeight("400px");
		 cita.setResizable(false);
		 
		 cita.addCloseListener(new CloseListener() {
			
			@Override
			public void windowClose(CloseEvent e) {
				BasicEvent miCita = new BasicEvent();
				GregorianCalendar fechaDesde = cita.getFechaDesde();
				GregorianCalendar fechaHasta = cita.getFechaHasta();
				miCita.setCaption(cita.getAsunto());
				miCita.setDescription(cita.getDescripcion());
				miCita.setStart(fechaDesde.getTime());
				miCita.setEnd(fechaHasta.getTime());
				miCita.setAllDay(cita.getDiaCompleto());
				if(cita.AGENDAR){
					TtiUI.container.addBean(miCita);
					TtiUI.container.sort(new Object[]{"start"}, new boolean[]{true});
				}else if(cita.ELIMINAR){
					TtiUI.container.removeItem(miCita);
				}
				
			}
		});
		 
		 UI.getCurrent().addWindow(cita);
    }
    
    



}
