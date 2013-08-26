package com.tti.views;

import com.tti.componentes.PanelDeControl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Upload;

public class SubirInformeView extends CustomComponent implements View {

	private static final long serialVersionUID = -8126608915184448372L;
	public static final String NAME = "ActualizarInforme";
	private PanelDeControl panelDeControl;
	private Upload uploadForm;
	@Override
	public void enter(ViewChangeEvent event) {
		panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")));

	}
	
	public SubirInformeView() {
		uploadForm = new Upload();
		uploadForm.setButtonCaption("Subir");
		panelDeControl = new PanelDeControl("username");
		setCompositionRoot(new CssLayout(panelDeControl, uploadForm));
	}

}
