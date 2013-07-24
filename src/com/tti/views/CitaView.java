package com.tti.views;

import java.io.FileNotFoundException;
import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.processEngine.MotorProcesos;
import com.tti.TtiUI;
import com.tti.componentes.PanelDeControlAlumno;
import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.addon.calendar.event.BasicEventProvider;
import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventClick;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.WeekClick;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.WeekClickHandler;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.Action;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
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
	
	private final FieldGroup scheduleEventForm = new FieldGroup();
	private Window scheduleEventPopup;
	private Button deleteEventButton;
    private Button applyEventButton;
    private BasicEventProvider dataSource;
    private boolean useSecondResolution;
	
	public Calendar calendario;
	private GregorianCalendar calendar;
	public Button botonMes = new Button("Mes");
	public Button botonSem = new Button("Semana");
	public Button botonSig = new Button("Siguiente");
	public Button botonPrev = new Button("Atrás");
	public Button nuevaReunion = new Button("Nueva reunión");
	private PanelDeControlAlumno panelDeControl;
	private MotorProcesos motor;
	
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
                Date start = new Date();
                start.setTime(0);

                Date end = Calendar.getEndOfDay(calendar, start);

                showEventPopup(createNewEvent(start, end), true);
            }
		});
		
			
		mesLabel = new Label();
		dataSource = new BasicEventProvider();
		calendario = new Calendar(dataSource);
		calendario.setSizeFull();
		calendario.setLocale(Locale.getDefault());
		calendar = new GregorianCalendar();
		GregorianCalendar start = calendar;
		GregorianCalendar end   = calendar;
		
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
		
		setCompositionRoot(new CssLayout(panelDeControl, mesLabel, nuevaReunion, botonMes, botonSem, botonPrev, botonSig, calendario));

        updateMesLabel();
        
        botonMes.setVisible(viewMode == Mode.WEEK);
        botonSem.setVisible(viewMode == Mode.DAY);
        addInitialEvents();
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
    
    private void handleRangeSelect(RangeSelectEvent event) {
        Date start = event.getStart();
        Date end = event.getEnd();

        /*
         * If a range of dates is selected in monthly mode, we want it to end at
         * the end of the last day.
         */
        if (event.isMonthlyMode()) {
            end = Calendar.getEndOfDay(calendar, end);
        }

        showEventPopup(createNewEvent(start, end), true);
    }
    
    private CalendarEvent createNewEvent(Date startDate, Date endDate) {

        BasicEvent event = new BasicEvent();
        event.setCaption("");
        event.setStart(startDate);
        event.setEnd(endDate);
        event.setStyleName("color1");
        return event;
    }
    
    private void showEventPopup(CalendarEvent event, boolean newEvent) {
        if (event == null) {
            return;
        }

        updateCalendarEventPopup(newEvent);
        updateCalendarEventForm(event);

        if (!TtiUI.getCurrent().getWindows().contains(scheduleEventPopup)) {
        	TtiUI.getCurrent().addWindow(scheduleEventPopup);
        }
    }
    
    private void updateCalendarEventPopup(boolean newEvent) {
        if (scheduleEventPopup == null) {
            createCalendarEventPopup();
        }

        if (newEvent) {
            scheduleEventPopup.setCaption("New event");
        } else {
            scheduleEventPopup.setCaption("Edit event");
        }

        deleteEventButton.setVisible(!newEvent);
        deleteEventButton.setEnabled(!calendario.isReadOnly());
        applyEventButton.setEnabled(!calendario.isReadOnly());
    }
    
    /* Initializes a modal window to edit schedule event. */
    private void createCalendarEventPopup() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        scheduleEventPopup = new Window(null, layout);
        scheduleEventPopup.setWidth("400px");
        scheduleEventPopup.setModal(true);
        scheduleEventPopup.center();

        layout.addComponent((Component) scheduleEventForm);

        applyEventButton = new Button("Apply");
        applyEventButton.addClickListener(new Button.ClickListener() {

            private static final long serialVersionUID = 1L;

            public void buttonClick(ClickEvent event) {
                try {
					commitCalendarEvent();
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        Button cancel = new Button("Cancel");
        cancel.addClickListener(new Button.ClickListener() {

            private static final long serialVersionUID = 1L;

            public void buttonClick(ClickEvent event) {
                discardCalendarEvent();
            }
        });
        deleteEventButton = new Button("Delete", new Button.ClickListener() {

            private static final long serialVersionUID = 1L;

            public void buttonClick(ClickEvent event) {
                deleteCalendarEvent();
            }
        });
        scheduleEventPopup.addCloseListener(new CloseListener() {

            private static final long serialVersionUID = 1L;

            public void windowClose(CloseEvent e) {
                discardCalendarEvent();
            }
        });

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.addComponent(deleteEventButton);
        buttons.addComponent(applyEventButton);
        buttons.addComponent(cancel);
        layout.addComponent(buttons);
        layout.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
    }
    
    /* Removes the event from the data source and fires change event. */
    private void deleteCalendarEvent() {
        BasicEvent event = getFormCalendarEvent();
        if (dataSource.containsEvent(event)) {
            dataSource.removeEvent(event);
        }
        TtiUI.getCurrent().removeWindow(scheduleEventPopup);
    }

    /* Adds/updates the event in the data source and fires change event. */
    private void commitCalendarEvent() throws CommitException {
        scheduleEventForm.commit();
        BasicEvent event = getFormCalendarEvent();
        if (!dataSource.containsEvent(event)) {
            dataSource.addEvent(event);
        }

        TtiUI.getCurrent().getWindows().remove(scheduleEventPopup);
    }

    private void discardCalendarEvent() {
        scheduleEventForm.discard();
        TtiUI.getCurrent().getWindows().remove(scheduleEventPopup);
    }

    @SuppressWarnings("unchecked")
    private BasicEvent getFormCalendarEvent() {
        BeanItem<CalendarEvent> item = (BeanItem<CalendarEvent>) scheduleEventForm
                .getItemDataSource();
        CalendarEvent event = item.getBean();
        return (BasicEvent) event;
    }
    
    private void addInitialEvents() {
        Date originalDate = calendar.getTime();
        Date today = new Date();
        // Add a event that last a whole week
        Date start = calendario.getFirstDateForWeek(today);
        Date end = calendario.getLastDateForWeek(today);
        BasicEvent event = getNewEvent("Evento de Semana completa", start, end);
        event.setAllDay(true);
        event.setStyleName("color4");
        event.setDescription("Este evento tiene una duración que abarca la semana completa.");
        dataSource.addEvent(event);

        // Add a allday event
        calendar.setTime(start);
        calendar.add(GregorianCalendar.DATE, 3);
        start = calendar.getTime();
        end = start;
        event = getNewEvent("Reuniones de Acreditación", start, end);
        event.setAllDay(true);
        event.setDescription("Evento de día completo");
        event.setStyleName("color3");
        dataSource.addEvent(event);

        // Add a second allday event
        calendar.add(GregorianCalendar.DATE, 1);
        start = calendar.getTime();
        end = start;
        event = getNewEvent("Día de la ingenieria", start, end);
        event.setAllDay(true);
        event.setDescription("Otro evento de día completo.");
        event.setStyleName("color2");
        dataSource.addEvent(event);

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
        dataSource.addEvent(event);

        calendar.add(GregorianCalendar.DATE, 1);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, 11);
        calendar.set(GregorianCalendar.MINUTE, 0);
        start = calendar.getTime();
        calendar.add(GregorianCalendar.HOUR_OF_DAY, 8);
        end = calendar.getTime();
        event = getNewEvent("Pequeña reunion", start, end);
        event.setStyleName("color2");
        dataSource.addEvent(event);

        calendar.add(GregorianCalendar.DATE, 4);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, 9);
        calendar.set(GregorianCalendar.MINUTE, 0);
        start = calendar.getTime();
        calendar.add(GregorianCalendar.HOUR_OF_DAY, 9);
        end = calendar.getTime();
        event = getNewEvent("Sólo para ver una duda de mi informe", start, end);
        dataSource.addEvent(event);

        calendar.setTime(originalDate);
    }
    private BasicEvent getNewEvent(String caption, Date start, Date end) {
    	BasicEvent event = new BasicEvent();
        event.setCaption(caption);
        event.setStart(start);
        event.setEnd(end);

        return event;
    }
    
    private void updateCalendarEventForm(CalendarEvent event) {
        // Lets create a CalendarEvent BeanItem and pass it to the form's data
        // source.
        BeanItem<CalendarEvent> item = new BeanItem<CalendarEvent>(event);
        scheduleEventForm.setBuffered(true);
        scheduleEventForm.setItemDataSource(item);
        scheduleEventForm.setFieldFactory(new FieldGroupFieldFactory() {

            private static final long serialVersionUID = 1L;

            @SuppressWarnings("rawtypes")
			public Field createField(Item item, Object propertyId,
                    Component uiContext) {
                if (propertyId.equals("caption")) {
                	TextArea f = createTextField("Caption");
                    f.focus();
                    return f;

                } else if (propertyId.equals("where")) {
                    return createTextField("Where");

                } else if (propertyId.equals("description")) {
                    TextArea f = createTextField("Description");
                    f.setRows(3);
                    return f;

                } else if (propertyId.equals("styleName")) {
                    return createStyleNameSelect();

                } else if (propertyId.equals("start")) {
                    return createDateField("Start date");

                } else if (propertyId.equals("end")) {
                    return createDateField("End date");
                } else if (propertyId.equals("allDay")) {
                    CheckBox cb = createCheckBox("All-day");

                    cb.addValueChangeListener(new Property.ValueChangeListener() {

                        private static final long serialVersionUID = -7104996493482558021L;

                        public void valueChange(ValueChangeEvent event) {
                            Object value = event.getProperty().getValue();
                            if (value instanceof Boolean
                                    && Boolean.TRUE.equals(value)) {
                                setFormDateResolution(Resolution.DAY);

                            } else {
                                setFormDateResolution(Resolution.MINUTE);
                            }
                        }

                    });

                    return cb;
                }
                return null;
            }

            private CheckBox createCheckBox(String caption) {
                CheckBox cb = new CheckBox(caption);
                cb.setImmediate(true);
                return cb;
            }

            private TextArea createTextField(String caption) {
            	TextArea f = new TextArea(caption);
                f.setNullRepresentation("");
                return f;
            }

            private DateField createDateField(String caption) {
                DateField f = new DateField(caption);
                if (useSecondResolution) {
                    f.setResolution(Resolution.SECOND);
                } else {
                    f.setResolution(Resolution.MINUTE);
                }
                return f;
            }

            @SuppressWarnings("unchecked")
			private ComboBox createStyleNameSelect() {
            	ComboBox s = new ComboBox("Color");
                s.addContainerProperty("c", String.class, "");
                s.setItemCaptionPropertyId("c");
                Item i = s.addItem("color1");
                i.getItemProperty("c").setValue("Green");
                i = s.addItem("color2");
                i.getItemProperty("c").setValue("Blue");
                i = s.addItem("color3");
                i.getItemProperty("c").setValue("Red");
                i = s.addItem("color4");
                i.getItemProperty("c").setValue("Orange");
                return s;
            }
        });

//        scheduleEventForm
//        .setVisibleItemProperties(new Object[] { "start", "end",
//                "allDay", "caption", "where", "description",
//        "styleName" });
    }
    
    private void setFormDateResolution(Resolution resolution) {
        if (scheduleEventForm.getField("start") != null
                && scheduleEventForm.getField("end") != null) {
            ((DateField) scheduleEventForm.getField("start"))
            .setResolution(resolution);
            ((DateField) scheduleEventForm.getField("start")).markAsDirty();
            ((DateField) scheduleEventForm.getField("end"))
            .setResolution(resolution);
            ((DateField) scheduleEventForm.getField("end")).markAsDirty();
        }
    }
    
    



}
