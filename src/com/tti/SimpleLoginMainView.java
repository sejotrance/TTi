package com.tti;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.processEngine.MotorProcesos;
import com.tti.componentes.PanelDeControl;
import com.tti.enums.Rol;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
public class SimpleLoginMainView extends CustomComponent implements View {

    public static final String NAME = "";
    public static String username;
    public PanelDeControl panelDeControl;
    private static Label textoHoy;
    private static Label diasFin;
    private static Label fechaFinSemestre;
    
    public SimpleLoginMainView() throws FileNotFoundException {
    	GregorianCalendar fechaHoy = new GregorianCalendar(Locale.getDefault());
    	int dia = fechaHoy.get(Calendar.DATE);
    	String mes = fechaHoy.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
    	int anno = fechaHoy.get(Calendar.YEAR);
//    	textoHoy = new Label("<p>Fecha de Hoy: " + fechaHoy.get(Calendar.MONTH) + "</p>", ContentMode.HTML);
    	textoHoy = new Label("<p>Fecha de Hoy: " + dia + " de " + mes + " de " + anno + "</p>", ContentMode.HTML);
    	diasFin = new Label("<p>Faltan 30 Dias para el termino del Segundo Semestre de 2013</p>", ContentMode.HTML);
    	fechaFinSemestre = new Label("<p>El semestre termina el 20 de Septiembre de 2013</p>", ContentMode.HTML);
    	panelDeControl = new PanelDeControl("Username");
        setCompositionRoot(new CssLayout(panelDeControl, textoHoy, diasFin, fechaFinSemestre));
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // Get the user name from the session
        username = String.valueOf(getSession().getAttribute("user"));
        panelDeControl = new PanelDeControl(username,getSession().getAttribute(Rol.class));
        setCompositionRoot(new CssLayout(panelDeControl, textoHoy, diasFin, fechaFinSemestre));
        Notification.show(username);
//        new Notification("Caption", String.valueOf(getSession().getAttribute("rol")), Notification.Type.TRAY_NOTIFICATION).show(Page.getCurrent());
        
    }
}