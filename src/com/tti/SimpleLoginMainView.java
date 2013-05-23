package com.tti;

import com.tti.componentes.PanelDeControlAlumno;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;

public class SimpleLoginMainView extends CustomComponent implements View {

    public static final String NAME = "";
    public String username;
    public PanelDeControlAlumno panelDeControl;
    
    public SimpleLoginMainView() {
    	panelDeControl = new PanelDeControlAlumno("Mauro Castillo");
        setCompositionRoot(new CssLayout(panelDeControl));
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // Get the user name from the session
        username = String.valueOf(getSession().getAttribute("user"));
        Notification.show(username);
        
    }
}