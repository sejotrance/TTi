package com.tti;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.processEngine.MotorProcesos;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
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
    private Label ttcual; //EN QUE TRABAJO DE TITULO ESTA ACTUALMENTE
    private static Label fechaFinSemestre;
    
    public SimpleLoginMainView() throws FileNotFoundException {
    	GregorianCalendar fechaHoy = new GregorianCalendar(Locale.getDefault());
    	int dia = fechaHoy.get(Calendar.DATE);
    	String mes = fechaHoy.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
    	int anno = fechaHoy.get(Calendar.YEAR);
    	textoHoy = new Label("<p>Fecha de Hoy: " + dia + " de " + mes + " de " + anno + "</p>", ContentMode.HTML);
    	diasFin = new Label("<p>Faltan 15 Dias para el termino del Segundo Semestre de 2013</p>", ContentMode.HTML);
    	fechaFinSemestre = new Label("<p>El semestre termina el 30 de Enero de 2013</p>", ContentMode.HTML);
    	panelDeControl = new PanelDeControl("Username");
    	ttcual = new Label("<h3>Actualmente estás cursando: Trabajo de Título II</h3>", ContentMode.HTML);
        setCompositionRoot(new CssLayout(panelDeControl, ttcual, textoHoy, diasFin, fechaFinSemestre));
    }

    @Override
    public void enter(ViewChangeEvent event) {
    	
    	List<Rol> userRol = (List<Rol>) getSession().getAttribute("roles");
		if(!userRol.isEmpty()){	
			username = String.valueOf(getSession().getAttribute("user"));
			panelDeControl = new PanelDeControl(username,userRol);
			if(userRol.contains(Rol.ALUMNO)){
				if(username == "alumno@utem.cl"){ //USUARIO DE PRUEBA
				}
				setCompositionRoot(new CssLayout(panelDeControl, ttcual, textoHoy, diasFin, fechaFinSemestre));
			}else{
				setCompositionRoot(new CssLayout(panelDeControl,textoHoy, diasFin, fechaFinSemestre));
			}
			Notification.show(username);
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
    }
}