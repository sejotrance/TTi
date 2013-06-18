package com.tti.views;

import com.processEngine.MotorProcesos;
import com.tti.componentes.PanelDeControlAlumno;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class ReprogramarView extends CustomComponent implements View{
	private static final long serialVersionUID = 4754040605647657981L;
	public static final String NAME = "ReprogramarCita";
	private PanelDeControlAlumno panelDeControl;
	private Label definiciones;
	private MotorProcesos motor;
	
	public ReprogramarView() {
		panelDeControl = new PanelDeControlAlumno("Karin Acuña");
//		motor = new MotorProcesos();
//		Long def = motor.getDefiniciones();
//		definiciones = new Label("N° Definiciones: " + def);
//		setCompositionRoot(new CssLayout(panelDeControl, definiciones));
		setCompositionRoot(new CssLayout(panelDeControl));
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
