package com.tti.views;

import java.util.List;
import java.util.UUID;

import ttiws.entidades.StatusResult;
import ttiws.model.PersonaModel;
import ttiws.serviciosAlumno.WSAlumnoCarreraAgregar;
import ttiws.serviciosAlumno.WSAlumnoProfesorAgregar;
import ttiws.serviciosPersona.WSPersonaConsultar;
import ttiws.serviciosPersona.WSPersonaCrear;

import com.google.gwt.user.client.ui.ClickListener;
import com.tti.Email;
import com.tti.SimpleLoginMainView;
import com.tti.componentes.ComboCarreraComponent;
import com.tti.componentes.ComboPersonaComponent;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.vaadin.client.ui.Field;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.validator.EmailValidator;
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
	private Label textoServicioLabel;
	private  ComboPersonaComponent comboProfesor;
	private  ComboCarreraComponent comboCarrera;
	private FormLayout editorLayout = new FormLayout();
//	private FieldGroup camposAlumno = new FieldGroup();
	@Override
	public void enter(ViewChangeEvent event) {
		List<Rol> userRol = (List<Rol>) getSession().getAttribute("roles");
		if(userRol.contains(Rol.SECRETARIA)){		
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			textoServicioLabel = new Label();
			initEditor();
			setCompositionRoot(new CssLayout(panelDeControl, descripcion, textoServicioLabel, editorLayout));
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
		  //editorLayout.addComponent(binder.buildAndBind("Usuario","per_Usuario"));
		  //editorLayout.addComponent(binder.buildAndBind("Contraseña", "per_Password"));
		  comboProfesor = new ComboPersonaComponent("Profesor Guia", "4");
		  comboCarrera = new ComboCarreraComponent("Carrera", 1);
		  com.vaadin.ui.Field<?> emailField = binder.buildAndBind("E-Mail", "per_Email");
		  emailField.addValidator(new EmailValidator("Ingrese un email válido"));
		  emailField.setInvalidAllowed(false);
		  editorLayout.addComponent(comboCarrera);
		  editorLayout.addComponent(emailField);
		  editorLayout.addComponent(binder.buildAndBind("RUN", "per_Run"));
		  editorLayout.addComponent(binder.buildAndBind("Nombre", "per_Nombre"));
		  editorLayout.addComponent(binder.buildAndBind("Apellido Paterno", "per_Apellido_Paterno"));
		  editorLayout.addComponent(binder.buildAndBind("Apellido Materno", "per_Apellido_Materno"));
		  editorLayout.addComponent(comboProfesor);
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
		          }catch (InvalidValueException ive){
		        	  
		          }
		          crearPersonaWS = new WSPersonaCrear();
		          //String password = bean.getPer_Password();
		          String password = UUID.randomUUID().toString();
		          String email = bean.getPer_Email();
		          String run = bean.getPer_Run();
		          String username = run;
		          String nombre = bean.getPer_Nombre();
		          String apP = bean.getPer_Apellido_Paterno();
		          String apM = bean.getPer_Apellido_Materno();
		          String direccion = bean.getPer_Dirección();
		          String telCelular = bean.getPer_Telefono_Celular();
		          int valComboCarrera = Integer.parseInt(comboCarrera.getValue());
		          StatusResult status = crearPersonaWS.crearPersona("6", username, password, email, run, nombre, apP, apM, direccion, telCelular,valComboCarrera , 1);
		          //Si hubo una excepcion de servicio
		          if(status.getCode() != 0){
		        	  Notification.show("Error al registrar usuario:" + status.getMessage(), Notification.Type.ERROR_MESSAGE);
		          }else{
		        	  int valCombo = Integer.parseInt(comboProfesor.getValue());
		        	  WSAlumnoProfesorAgregar ws1 = new WSAlumnoProfesorAgregar();
		        	  PersonaModel alumnoAux = WSPersonaConsultar.consultarPorRun(run);
		        	  StatusResult statusAgregarProfesor = ws1.agregarProfesor(alumnoAux.getPer_Id(), valCombo);
		        	  if(statusAgregarProfesor.getCode() == 0){
			        	  Notification.show("Usuario registrado exitosamente", Notification.Type.HUMANIZED_MESSAGE);
			        	  textoServicioLabel.setCaption("El usuario ya ha sido registrado. Revisa tu correo con la información para poder ingresar a TTi");
		  				  textoServicioLabel.setContentMode(ContentMode.HTML);
			        	  editorLayout.setVisible(false);
			        	  Email mailRegistro = new Email("tti@utem.cl", email, "", "");
			        	  mailRegistro.sendRegistroMail(username, password);
		        	  }else{
		        		  Notification.show("Error al registrar usuario: " + statusAgregarProfesor.getMessage(), Notification.Type.ERROR_MESSAGE);
		        	  }
		        	  
		          }
		  }}));
		  
		  
//		  //SOLO PARA TESTING
//		  editorLayout.addComponent(new Button("TEST", new Button.ClickListener() {
//			
//			@Override
//			public void buttonClick(ClickEvent event) {
//				Notification.show("Valor: " + comboProfesor.getValue());
//			}
//		}));	
  }

}
