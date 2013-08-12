package com.tti.views;

import com.tti.componentes.PanelDeControlAlumno;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Upload;

public class SubirInformeView extends CustomComponent implements View {

	private static final long serialVersionUID = -8126608915184448372L;
	public static final String NAME = "ActualizarInforme";
	private PanelDeControlAlumno panelDeControl;
	private Upload uploadForm;
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	public SubirInformeView() {
		uploadForm = new Upload();
		uploadForm.setButtonCaption("Subir");
		panelDeControl = new PanelDeControlAlumno("Karin Acuña");
		setCompositionRoot(new CssLayout(panelDeControl, uploadForm));
	}

}
