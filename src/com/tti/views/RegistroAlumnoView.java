package com.tti.views;

import java.util.UUID;

import ttiws.model.PersonaModel;
import ttiws.serviciosPersona.WSPersonaCrear;

import com.google.gwt.user.client.ui.ClickListener;
import com.tti.SimpleLoginMainView;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

public class RegistroAlumnoView extends CustomComponent implements View{
	
	private static final long serialVersionUID = -8559029417440049381L;
	public static final String NAME = "RegistrarAlumno";
	private static final String RUN = "RUN";
	private static final String NOMBRE = "Nombre";
    private static final String APELLIDO = "Apellido";
    private WSPersonaCrear crearPersonaWS;
	//private static final String[] nombreCampo = new String[] {"Usuario", "Contraseña","Email", RUN, NOMBRE, APELLIDO , 
	//	"Teléfono", "Dirección"};
	
	private PanelDeControl panelDeControl;
	private static final Label descripcion = new Label("<h2> Registrar un nuevo Alumno </h2>" + 
			"<p> Ingrese los datos en el formulario y presione el botón Registrar para guardar los cambios. </p>", ContentMode.HTML);
	private FormLayout editorLayout = new FormLayout();
//	private FieldGroup camposAlumno = new FieldGroup();
	private Button botonGuardar;
	
	@Override
	public void enter(ViewChangeEvent event) {
		Rol userRol = getSession().getAttribute(Rol.class);
		if(userRol == Rol.SECRETARIA){		
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			initEditor();
			botonGuardar = new Button("Registrar Alumno", new Button.ClickListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					String identificador = UUID.randomUUID().toString();
					
				}
			});
			setCompositionRoot(new CssLayout(panelDeControl, descripcion, editorLayout, botonGuardar));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
	}
	
	  private void initEditor() {
		// Have a bean
		  final PersonaModel bean = new PersonaModel();
		  bean.setPer_Usuario("");
		  bean.setPer_Password("");
		  bean.setPer_Email("");
		  bean.setPer_Run("");
		  bean.setPer_Nombre("");
		  bean.setPer_Apellido_Paterno("");
		  bean.setPer_Apellido_Materno("");
		  bean.setPer_Dirección("");
		  bean.setPer_Telefono_Celular("");
		          
		  // Form for editing the bean
		  final BeanFieldGroup<PersonaModel> binder =
		          new BeanFieldGroup<PersonaModel>(PersonaModel.class);
		  binder.setItemDataSource(bean);
		  editorLayout.addComponent(binder.buildAndBind("Usuario","per_Usuario"));
		  editorLayout.addComponent(binder.buildAndBind("Contraseña", "per_Password"));
		  editorLayout.addComponent(binder.buildAndBind("E-Mail", "per_Email"));
		  editorLayout.addComponent(binder.buildAndBind("RUN", "per_Run"));
		  editorLayout.addComponent(binder.buildAndBind("Nombre", "per_Nombre"));
		  editorLayout.addComponent(binder.buildAndBind("Apellido Paterno", "per_Apellido_Paterno"));
		  editorLayout.addComponent(binder.buildAndBind("Apellido Materno", "per_Apellido_Materno"));
		  editorLayout.addComponent(binder.buildAndBind("Dirección", "per_Dirección"));
		  editorLayout.addComponent(binder.buildAndBind("Teléfono", "per_Telefono_Celular"));
		  
		// Buffer the form content
		  binder.setBuffered(true);
		  editorLayout.addComponent(new Button("Registrar", new Button.ClickListener() {
		      @Override
		      public void buttonClick(ClickEvent event) {
		          try {
		              binder.commit();
		          } catch (CommitException e) {
		          }
		          crearPersonaWS = new WSPersonaCrear();
		          String username = bean.getPer_Usuario();
		          String password = bean.getPer_Password();
		          String email = bean.getPer_Email();
		          String run = bean.getPer_Run();
		          String nombre = bean.getPer_Nombre();
		          String apP = bean.getPer_Apellido_Paterno();
		          String apM = bean.getPer_Apellido_Materno();
		          String direccion = bean.getPer_Dirección();
		          String telCelular = bean.getPer_Telefono_Celular();
		          crearPersonaWS.crearPersona("", username, password, email, run, nombre, apP, apM, direccion, telCelular, "", 1);
		      
		  }}));
//          for (String fieldName : nombreCampo) {
//                  TextField field = new TextField(fieldName);
//                  editorLayout.addComponent(field);
//                  //field.setWidth("100%");
//
//                  camposAlumno.bind(field, fieldName);
//          }
//          editorLayout.addComponent(removeContactButton);

//          camposAlumno.setBuffered(false);
  }

}
