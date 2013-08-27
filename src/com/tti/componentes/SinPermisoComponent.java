package com.tti.componentes;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class SinPermisoComponent extends CustomComponent {
	
	private static final long serialVersionUID = 8951300332438126221L;
	private Label mensaje;
	
	public SinPermisoComponent() {
	mensaje = new Label("<h1>Error: Sitio no encontrado</h1>", ContentMode.HTML);
	setCompositionRoot(mensaje);
	}

}
