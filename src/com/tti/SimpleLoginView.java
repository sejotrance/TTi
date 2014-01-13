package com.tti;

import java.util.ArrayList;
import java.util.List;

import ttiws.model.PersonaHasRolModel;
import ttiws.model.PersonaModel;
import ttiws.model.RolModel;
import ttiws.serviciosPersona.WSPersonaConsultar;

import com.tti.data.UserLogin;
import com.tti.entidad.Usuario;
import com.tti.enums.Rol;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.Button;

@SuppressWarnings("serial")
public class SimpleLoginView extends CustomComponent implements View,
Button.ClickListener {
	
	public static final String NAME = "login";
	
	public static Usuario usuario;
	
	private final Label texto;
	
	private final TextField user;
	
	private final PasswordField password;
	
	private final Button loginButton;
	
	public SimpleLoginView() {
	setSizeFull();
	
	texto = new Label("<div class=\"well\">Por favor ingrese usuario y contraseña para acceder a la aplicación.</div>",ContentMode.HTML);
	
	// Create the user input field
	user = new TextField("Usuario:");
	user.setWidth("300px");
	user.setRequired(true);
	user.setInputPrompt("Tu nombre de usuario (rut sin guion y con digito verificador)");
	//user.addValidator(new EmailValidator("El nombre de usuario debe ser una dirección email"));
	user.setInvalidAllowed(false);
	user.setStyleName("form-control");
	
	// Create the password input field
	password = new PasswordField("Contraseña:");
	password.setWidth("300px");
	password.addValidator(new PasswordValidator());
	password.setRequired(true);
	password.setValue("");
	password.setNullRepresentation("");
	password.setStyleName("form-control");
	
	// Create login button
	loginButton = new Button("Login", this);
	loginButton.setClickShortcut(KeyCode.ENTER);
	// Add both to a panel
	VerticalLayout fields = new VerticalLayout(texto, user, password, loginButton);
	fields.addStyleName("input-group");
	//fields.setCaption("Por favor ingrese usuario y contraseña para acceder a la aplicación. (alumno@utem.cl/passw0rd)");
	fields.setSpacing(true);
	fields.setMargin(new MarginInfo(true, true, true, false));
	fields.setSizeUndefined();
	
	// The view root layout
	VerticalLayout viewLayout = new VerticalLayout(fields);
	viewLayout.setSizeFull();
	viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
	viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
	setCompositionRoot(viewLayout);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	// focus the username field when user arrives to the login view
	user.focus();
	}
	
	//
	// Validator for validating the passwords
	//
	private static final class PasswordValidator extends
	    AbstractValidator<String> {
	
	public PasswordValidator() {
	    super("La contraseña no es valida.");
	}
	
	@Override
	protected boolean isValidValue(String value) {
	    //
	    // Password must be at least 8 characters long and contain at least
	    // one number
	    //
	    if (value != null
	            && (value.length() < 8 || !value.matches(".*\\d.*"))) {
	        return false;
	    }
	    return true;
	}
	
	@Override
	public Class<String> getType() {
	    return String.class;
	}
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
	
		 //
		 // Validate the fields using the navigator. By using validors for the
		 // fields we reduce the amount of queries we have to use to the database
		 // for wrongly entered passwords
		 //
		if (!user.isValid() || !password.isValid()) {
		    return;
		}
		
		String username = user.getValue();
		String password = this.password.getValue();
		
		 //
		 // Validate username and password with database here. For examples sake
		 // I use a dummy username and password.
		 //
		boolean isValid = checkLogin(username, password);
		
		if(isValid){
		    // Store the current user in the service session
		    getSession().setAttribute("user", username);
		    getSession().setAttribute("roles", usuario.getRolesUsuario());
		
		    // Navigate to main view
		    getUI().getNavigator().navigateTo(SimpleLoginMainView.NAME);
		
		} else {
		
		    // Wrong password clear the password field and refocuses it
		    this.password.setValue(null);
		    this.password.focus();
		}
	}
	
	boolean checkLogin(String username, String password){
		List<Rol> rolesAux = new ArrayList<Rol>();
		//ALUMNO
		if(username.equals("alumno@utem.cl") && password.equals("passw0rd")){
			rolesAux.add(Rol.ALUMNO);
			usuario = new Usuario(username, password, rolesAux);
			return true;
		//PROFESOR
		}else if(username.equals("profesor@utem.cl") && password.equals("passw0rd")){
			rolesAux.add(Rol.PROFESOR);
			usuario = new Usuario(username, password, rolesAux);
			return true;
		//JEFE DE CARRERA
		}else if(username.equals("jc@utem.cl") && password.equals("passw0rd")){
			rolesAux.add(Rol.JEFE_CARRERA);
			usuario = new Usuario(username, password, rolesAux);
			return true;
		//DIRECTOR DE ESCUELA
		}else if(username.equals("de@utem.cl") && password.equals("passw0rd")){
			rolesAux.add(Rol.DIRECTOR_ESCUELA);
			usuario = new Usuario(username, password, rolesAux);
			return true;
		//DIRECTOR DE DEPARTAMENTO
		}else if(username.equals("dp@utem.cl") && password.equals("passw0rd")){
			rolesAux.add(Rol.DIRECTOR_DEPARTAMENTO);
			usuario = new Usuario(username, password, rolesAux); 
			return true;
		//SECRETARIA
		}else if(username.equals("secretaria@utem.cl") && password.equals("passw0rd")){
			rolesAux.add(Rol.SECRETARIA);
			usuario = new Usuario(username, password, rolesAux); 
			return true;
		//ADMINISTRADOR
		}else if(username.equals("administrador@utem.cl") && password.equals("passw0rd")){
			rolesAux.add(Rol.ADMINISTRADOR);
			usuario = new Usuario(username, password, rolesAux); 
			return true;
		// DESDE LA BASE DE DATOS
		}else if(UserLogin.login(username, password)){
			PersonaModel personaLogueada = WSPersonaConsultar.consultarPorRun(username);
			List<PersonaHasRolModel> roles = personaLogueada.getPersonaHasRols();
			for (PersonaHasRolModel rol : roles) {
				if(rol.getPer_Rol_Es_Vigente()){
					switch (rol.getRol().getRol_Id()) {
					case 1:
						rolesAux.add(Rol.DIRECTOR_DEPARTAMENTO); break;
					case 2:
						rolesAux.add(Rol.DIRECTOR_ESCUELA); break;
					case 3:
						rolesAux.add(Rol.JEFE_CARRERA); break;
					case 4:
						rolesAux.add(Rol.PROFESOR); break;
					case 5:
						rolesAux.add(Rol.SECRETARIA); break;
					case 6:
						rolesAux.add(Rol.ALUMNO); break;
					default:
						rolesAux.add(Rol.ADMINISTRADOR); break;
					}
					
				}
			}
			
			usuario = new Usuario(username, password, rolesAux);
			return true;
		}else{
			rolesAux.add(Rol.UNDEFINED);
			usuario = new Usuario(username, password, rolesAux);
			return false;
		}
	}
	
}