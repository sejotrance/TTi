package com.tti.componentes;

//import com.vaadin.server.ExternalResource;
import java.util.List;

import com.tti.SimpleLoginMainView;
import com.tti.SimpleLoginView;
import com.tti.enums.Rol;
import com.tti.views.AvanceAlumnosView;
import com.tti.views.AvanceView;
import com.tti.views.BitacoraView;
import com.tti.views.CalificacionAlumnoView;
import com.tti.views.CitaView;
import com.tti.views.ListadoAlumnosView;
import com.tti.views.ListadoProfesoresView;
import com.tti.views.ListadoReunionesView;
import com.tti.views.Perfil;
import com.tti.views.PerfilProfesorView;
import com.tti.views.RegistroAlumnoView;
import com.tti.views.RegistroProfesorView;
import com.tti.views.ReportesView;
import com.tti.views.ReprogramarView;
import com.tti.views.SubirInformeView;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("serial")
public class PanelDeControl extends CustomComponent{
	
	private HorizontalLayout navbar; 
	public static final String NAME = "panelDeControl";
    private MenuBar menubar;
    private MenuBar menubarUser;
    private String username;
    
    public PanelDeControl(String username){
    	menubar = new MenuBar();
    	menubarUser = new MenuBar();
    	this.username = username;
    }
	public PanelDeControl(String username, List<Rol> userRol) {
    	
    	menubar = new MenuBar();
    	menubarUser = new MenuBar();
    	this.username = username;
    	setPaneldeControl(userRol);
        }
    
	
	private void setPaneldeControl(List<Rol> userRol){
		//PANEL DE CONTROL
		
		//INICIO
    	final MenuBar.MenuItem inicio = menubar.addItem("TTi", goHome);
    	inicio.setStyleName("h1");
        
    	//REUNION
    	if((userRol.contains(Rol.PROFESOR) && userRol.contains(Rol.DIRECTOR_DEPARTAMENTO)) || userRol.contains(Rol.ALUMNO) || userRol.contains(Rol.PROFESOR)){
	    	final MenuBar.MenuItem reunion = menubar.addItem("Reunión", null);
	    	reunion.addItem("Agendar / Reprogramar", agendarCita);
	        reunion.addItem("Ver Listado", verListadoReuAlumnoCommand);
	        if((userRol.contains(Rol.PROFESOR) && userRol.contains(Rol.DIRECTOR_DEPARTAMENTO)) || userRol.contains(Rol.PROFESOR)){
		        reunion.addSeparator();
		        reunion.addItem("Bitácora", bitacoraCommand);
	        }
    	}
        
        //PERFIL
        final MenuBar.MenuItem perfil = menubarUser.addItem(this.username, null);
        perfil.setIcon(new ThemeResource("../../imagenes/1369309745_user.png"));
        perfil.addItem("Editar Perfil", goProfile);
        perfil.addSeparator();
        perfil.addItem("Cerrar Sesión", logout);
        
        //AVANCE
        if(userRol.contains(Rol.ALUMNO)){
	        final MenuBar.MenuItem avance = menubar.addItem("Mi Avance", null);
	        avance.addItem("Revisar Mi Avance", miAvance);
	        avance.addSeparator();
	        avance.addItem("Actualizar mi Informe", subirInforme);
        }
        
        //PROFESOR GUIA
        if(userRol.contains(Rol.ALUMNO)){
	        final MenuBar.MenuItem profesorGuia = menubar.addItem("Profesor Guía", null);
	        profesorGuia.addItem("Perfil", perfilProfesorCommand);
	        profesorGuia.addItem("Enviar un mensaje", menuCommand);
	        profesorGuia.addSeparator();
	        profesorGuia.addItem("Cambio Profesor", menuCommand);
	        }
        
        //ALUMNO
        if(userRol.contains(Rol.SECRETARIA) || userRol.contains(Rol.DIRECTOR_DEPARTAMENTO)){
	        final MenuBar.MenuItem alumno = menubar.addItem("Alumno", null);
	        alumno.addItem("Ver Listado", verListadoAlumnoCommand);
	        if(userRol.contains(Rol.SECRETARIA)){
		        alumno.addSeparator();
		        alumno.addItem("Registrar", registrarAlumno);
	        }
        }
        
        //DOCENTE
        if(userRol.contains(Rol.SECRETARIA) || userRol.contains(Rol.DIRECTOR_DEPARTAMENTO)){
	        final MenuBar.MenuItem docente = menubar.addItem("Docente", null);
	        docente.addItem("Ver Listado", verListadoProfesorCommand);
	        if(userRol.contains(Rol.SECRETARIA)){
		        docente.addSeparator();
		        docente.addItem("Registrar", registrarProfesorCommand);
	        }
        }
        //REPORTES
        if(userRol.contains(Rol.SECRETARIA) || userRol.contains(Rol.DIRECTOR_DEPARTAMENTO)){
        	final MenuBar.MenuItem reportes = menubar.addItem("Reportes", reportesCommand);
        }
        //ALUMNOS
        if(userRol.contains(Rol.PROFESOR)){
        final MenuBar.MenuItem alumnos = menubar.addItem("Mis Alumnos", null);
	        alumnos.addItem("Revisar Avance", avanceAlumnosCommand);
	        alumnos.addSeparator();
	        alumnos.addItem("Revisar Informe", subirInforme);
	        alumnos.addItem("Calificar", calificarAlumnosCommand);
        }
        menubar.setHtmlContentAllowed(true);
        
        menubar.setSizeUndefined();
        menubarUser.setSizeUndefined();
        navbar = new HorizontalLayout(menubar, menubarUser);
        
        navbar.setComponentAlignment(menubar, Alignment.MIDDLE_LEFT);
        navbar.setComponentAlignment(menubarUser, Alignment.MIDDLE_RIGHT);
        navbar.setWidth("100%");
        navbar.setExpandRatio(menubar, 3.0f);
        navbar.setExpandRatio(menubarUser, 1.0f);
        navbar.setPrimaryStyleName("navbar navbar-default navbar-fixed-top");
        navbar.setSpacing(false);
        navbar.setStyleName("nav navbar-nav");
        setCompositionRoot(new CssLayout(navbar));
	}
    
    private Command menuCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
            
        }
    };
    
    private Command goHome = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(SimpleLoginMainView.NAME);
        }
    };
    
    private Command goProfile = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(Perfil.NAME);
        }
    };
    
    private Command logout = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	 // "Logout" the user
            getSession().setAttribute("user", null);
        	getUI().getNavigator().navigateTo(SimpleLoginView.NAME);
        }
    };
    
    private Command agendarCita = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(CitaView.NAME);
        }
    };
    
    private Command reprogramar = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(ReprogramarView.NAME);
        }
    };
    
    private Command miAvance = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(AvanceView.NAME);
        }
    };
    
    private Command verListadoReuAlumnoCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(ListadoReunionesView.NAME);
        }
    };
    
    private Command subirInforme = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(SubirInformeView.NAME);
        }
    };
    
    private Command registrarAlumno = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(RegistroAlumnoView.NAME);
        }
    };
    
    private Command verListadoAlumnoCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(ListadoAlumnosView.NAME);
        }
    };
    
    private Command verListadoProfesorCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(ListadoProfesoresView.NAME);
        }
    };
    
    private Command avanceAlumnosCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(AvanceAlumnosView.NAME);
        }
    };
    
    private Command registrarProfesorCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(RegistroProfesorView.NAME);
        }
    };
    
    private Command calificarAlumnosCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(CalificacionAlumnoView.NAME);
        }
    };
    
    private Command bitacoraCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(BitacoraView.NAME);
        }
    };
    
    private Command perfilProfesorCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(PerfilProfesorView.NAME);
        }
    };
    
    private Command reportesCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(ReportesView.NAME);
        }
    };

	

}

