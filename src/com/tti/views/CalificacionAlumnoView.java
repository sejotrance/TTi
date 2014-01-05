package com.tti.views;

import com.google.gwt.user.client.ui.TextBox;
import com.tti.componentes.GestorUsuariosComponent;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class CalificacionAlumnoView extends CustomComponent implements View{

	private static final long serialVersionUID = 1L;
	public static final String NAME = "CalificarAlumno";
	private PanelDeControl  panelDeControl;
	private Table tablaCalificaciones;
	private TextField cantidad;

	@Override
	public void enter(ViewChangeEvent event) {
		Rol userRol = getSession().getAttribute(Rol.class);
		if(userRol == Rol.PROFESOR){		
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			cantidad = new TextField("Seleccione cantidad de calificaciones a ingresar para el alumno:");
			tablaCalificaciones = new Table("Calificaciones seleccionadas");
			initListeners();
			setCompositionRoot(new CssLayout(panelDeControl, cantidad, tablaCalificaciones));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
		
		
	}
	
	private void initListeners(){
		cantidad.addTextChangeListener(new TextChangeListener() {
			
			@Override
			public void textChange(TextChangeEvent event) {
				Notification.show(event.getText());
				
			}
		});
	}

}
