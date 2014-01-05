package com.tti.views;

import com.processEngine.MotorProcesos;
import com.tti.componentes.PanelDeControl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class ReprogramarView extends CustomComponent implements View{
	private static final long serialVersionUID = 4754040605647657981L;
	public static final String NAME = "ReprogramarCita";
	private PanelDeControl panelDeControl;
	private Label definiciones;
	private MotorProcesos motor;
	
	public ReprogramarView() {
		panelDeControl = new PanelDeControl("username");
//		motor = new MotorProcesos();
//		Long def = motor.getDefiniciones();
//		definiciones = new Label("N° Definiciones: " + def);
//		setCompositionRoot(new CssLayout(panelDeControl, definiciones));
		setCompositionRoot(new CssLayout(panelDeControl));
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")));
		
	}
	
}
