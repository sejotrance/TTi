package com.tti.views;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ttiws.entidades.StatusResult;
import ttiws.model.CalificacionModel;
import ttiws.model.PersonaModel;
import ttiws.serviciosAlumno.WSAlumnoCalificacionCrear;
import ttiws.serviciosAlumno.WSAlumnoProfesorAgregar;
import ttiws.serviciosPersona.WSPersonaConsultar;
import ttiws.serviciosPersona.WSPersonaCrear;

import com.google.gwt.user.client.ui.TextBox;
import com.tti.Email;
import com.tti.componentes.ComboAlumnoComponent;
import com.tti.componentes.ComboCarreraComponent;
import com.tti.componentes.ComboPersonaComponent;
import com.tti.componentes.GestorUsuariosComponent;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

public class CalificacionAlumnoView extends CustomComponent implements View{

	private static final long serialVersionUID = 1L;
	public static final String NAME = "CalificarAlumno";
	private PanelDeControl  panelDeControl;
	private Label descripcion;
	private ComboAlumnoComponent comboAlumno;
	private FormLayout editorLayout = new FormLayout();
	private Label textoServicioLabel;

	@Override
	public void enter(ViewChangeEvent event) {
		List<Rol> userRol = (List<Rol>) getSession().getAttribute("roles");
		if(userRol.contains(Rol.PROFESOR)){
			String caption = "Mis Alumnos";
			String userName = String.valueOf(getSession().getAttribute("user"));
			int codProfesor = 3; //TEST USER
			if(!userName.equals("profesor@utem.cl")){//TEST USER
				PersonaModel profesor = WSPersonaConsultar.consultarPorRun(userName);
				codProfesor = profesor.getPer_Id();
			}
			textoServicioLabel = new Label();
			comboAlumno = new ComboAlumnoComponent(caption, codProfesor);
			initEditor();
			panelDeControl = new PanelDeControl(userName, userRol);
			descripcion = new Label("Seleccione un alumno e ingrese una calificacion");
			setCompositionRoot(new CssLayout(panelDeControl, textoServicioLabel, editorLayout));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
		
		
	}
	
  private void initEditor() {
			// Have a bean
			  final CalificacionModel bean = new CalificacionModel();
			  bean.setCal_Calificacion("");
			  bean.setCal_Descripcion("");
			  bean.setCal_Es_Informe(false);
			  bean.setCal_Fecha(null);
			  bean.setCal_Porcentaje("");
			          
			  // Form for editing the bean
			  final BeanFieldGroup<CalificacionModel> binder =
			          new BeanFieldGroup<CalificacionModel>(CalificacionModel.class);
			  binder.setItemDataSource(bean);
			  editorLayout.addComponent(comboAlumno);
			  editorLayout.addComponent(binder.buildAndBind("Calificacion", "cal_Calificacion"));
			  editorLayout.addComponent(binder.buildAndBind("Ponderación", "cal_Porcentaje"));
			  editorLayout.addComponent(binder.buildAndBind("Descripcion", "cal_Descripcion"));
			  editorLayout.addComponent(binder.buildAndBind("Es Informe", "cal_Es_Informe"));
			  
			// Buffer the form content
			  binder.setBuffered(true);
			  editorLayout.addComponent(new Button("Ingresar Calificación", new Button.ClickListener() {
			      @Override
			      public void buttonClick(ClickEvent event) {
			          try {
			              binder.commit();
			          } catch (CommitException e) {
			          }catch (InvalidValueException ive){
			        	  
			          }
			        int idAlumno = Integer.parseInt(comboAlumno.getValue());
					String Cal_Porcentaje = bean.getCal_Porcentaje();
					String Cal_Calificacion = bean.getCal_Calificacion();
					String Cal_Descripcion = bean.getCal_Descripcion();
					boolean Cal_Es_Informe = bean.getCal_Es_Informe();
					StatusResult status = WSAlumnoCalificacionCrear.crearCalificacion(idAlumno, Cal_Porcentaje, new Date(), Cal_Calificacion, Cal_Descripcion, Cal_Es_Informe);
			         
			          
			          //Si hubo una excepcion de servicio
			          if(status.getCode() != 0){
			        	  Notification.show("Error al registrar calificacion:" + status.getMessage(), Notification.Type.ERROR_MESSAGE);
			          }else{
			        	  Notification.show("Calificacion ingresada exitosamente", Notification.Type.HUMANIZED_MESSAGE);
			        	  textoServicioLabel.setCaption("La calificacion fue ingresada. Muchas gracias!");
		  				  textoServicioLabel.setContentMode(ContentMode.HTML);
			        	  editorLayout.setVisible(false);        	 
			          }
			  }}));
  	}
  }