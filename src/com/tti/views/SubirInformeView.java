package com.tti.views;

import java.util.Date;
import java.util.UUID;

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
	private Table tablaArchivos;
	public static BeanItemContainer<ArchivoDemo> container;
	@Override
	public void enter(ViewChangeEvent event) {
		upload = new UploadPB();
		container = new BeanItemContainer<ArchivoDemo>(ArchivoDemo.class);
		initDemoContainer(5);
		tablaArchivos = new Table("Mis archivos", container);
		Rol userRol = getSession().getAttribute(Rol.class);
		panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
		setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, upload, tablaArchivos));
			
	}
	
	public SubirInformeView() {
		upload = new UploadPB();
		container = new BeanItemContainer<ArchivoDemo>(ArchivoDemo.class);
		tablaArchivos = new Table("Mis archivos");
		
		panelDeControl = new PanelDeControl("username");
		setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, upload, tablaArchivos));
	}
	
	private void initDemoContainer(int cantidad){
		if(cantidad > 0){
			for(int i = 0; i < cantidad; i++){
				String identificador = UUID.randomUUID().toString();
				String nombre = ("Archivo_" + identificador);
				ArchivoDemo archivo = new ArchivoDemo(i, nombre, "/tmp/archivos", "PDF", new Date());
				container.addBean(archivo);
				container.sort(new Object[]{"id"}, new boolean[]{true});
			}
		}
	}

}
