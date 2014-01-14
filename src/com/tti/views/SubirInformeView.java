package com.tti.views;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ttiws.model.PersonaModel;
import ttiws.serviciosPersona.WSPersonaConsultar;

import com.tti.componentes.PanelDeControl;
import com.tti.componentes.PerfilComponent;
import com.tti.data.UploadPB;
import com.tti.entidad.ArchivoDemo;
import com.tti.enums.Rol;
import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;

public class SubirInformeView extends CustomComponent implements View {

	private static final long serialVersionUID = -8126608915184448372L;
	public static final String NAME = "ActualizarInforme";
	private static final Label descripcionLabel = new Label("<h2> Subir informe </h2>", ContentMode.HTML);
	private PanelDeControl panelDeControl;
	private UploadPB upload;
	public static BeanItemContainer<ArchivoDemo> container;
	@Override
	public void enter(ViewChangeEvent event) {
		List<Rol> userRol = (List<Rol>) getSession().getAttribute("roles");
		if(userRol.contains(Rol.PROFESOR) || userRol.contains(Rol.ALUMNO)){
			String userName = String.valueOf(getSession().getAttribute("user"));
			panelDeControl = new PanelDeControl(userName, userRol);
			if(userRol.contains(Rol.PROFESOR)){
				upload = new UploadPB(false);
				setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, upload));
			}else{
				upload = new UploadPB(true);
				setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, upload));
			}
		}	
	}
	
	public SubirInformeView() {
		upload = new UploadPB(true);
		
		panelDeControl = new PanelDeControl("username");
		setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, upload));
	}
	
	

}
