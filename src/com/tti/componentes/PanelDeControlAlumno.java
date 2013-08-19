package com.tti.componentes;

//import com.vaadin.server.ExternalResource;
import com.tti.SimpleLoginMainView;
import com.tti.SimpleLoginView;
import com.tti.views.AvanceView;
import com.tti.views.CitaView;
import com.tti.views.ListadoReunionesView;
import com.tti.views.Perfil;
import com.tti.views.ReprogramarView;
import com.tti.views.SubirInformeView;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("serial")
public class PanelDeControlAlumno extends CustomComponent{
	
	private HorizontalLayout navbar; 
	public static final String NAME = "panelDeControl";
    private MenuBar menubar;
    private MenuBar menubarUser;
    private Button mensaje;
    private String username;
    
    @SuppressWarnings("unused")
	public PanelDeControlAlumno(String username) {
    	menubar = new MenuBar();
    	menubarUser = new MenuBar();
    	this.username = username;
    	mensaje = new Button("Nuevo Mensaje");
        // Save reference to individual items so we can add sub-menu items to
        // them
    	final MenuBar.MenuItem inicio = menubar.addItem("TTi", goHome);
        final MenuBar.MenuItem reunion = menubar.addItem("Reunión", null);
        final MenuBar.MenuItem agendar = reunion.addItem("Agendar", agendarCita);

        inicio.setStyleName("h1");
        reunion.addItem("Reprogramar", reprogramar);
        reunion.addSeparator();

        reunion.addItem("Ver Listado", verListado);

        final MenuBar.MenuItem avance = menubar.addItem("Mi Avance", null);
        avance.addItem("Revisar Mi Avance", miAvance);
        avance.addSeparator();

        avance.addItem("Actualizar mi Informe", subirInforme);

        final MenuBar.MenuItem profesorGuia = menubar.addItem("Profesor Guía", null);
        profesorGuia.addItem("Perfil", menuCommand);
        profesorGuia.addItem("Enviar un mensaje", menuCommand);
        profesorGuia.addSeparator();

        profesorGuia.addItem("Cambio Profesor", menuCommand);
        
        final MenuBar.MenuItem perfil = menubarUser.addItem(this.username, null);
        perfil.setIcon(new ThemeResource("../../imagenes/1369309745_user.png"));
        perfil.addItem("Editar Perfil", goProfile);
        perfil.addSeparator();
        perfil.addItem("Cerrar Sesión", logout);
        
        menubar.setHtmlContentAllowed(true);
        
        menubar.setSizeUndefined();
        menubarUser.setSizeUndefined();
        mensaje.setSizeUndefined();
        menubar.setWidth("100%");
        menubarUser.setWidth("100%");
        mensaje.setWidth("100%");
        navbar = new HorizontalLayout(menubar, menubarUser, mensaje);
        
        navbar.setComponentAlignment(menubar, Alignment.MIDDLE_LEFT);
        navbar.setComponentAlignment(menubarUser, Alignment.MIDDLE_RIGHT);
        navbar.setComponentAlignment(mensaje, Alignment.MIDDLE_RIGHT);
        navbar.setWidth("100%");
        navbar.setExpandRatio(menubar, 3.0f);
        navbar.setExpandRatio(menubarUser, 1.0f);
        navbar.setExpandRatio(mensaje, 1.0f);
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
    
    private Command verListado = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(ListadoReunionesView.NAME);
        }
    };
    
    private Command subirInforme = new Command() {
        public void menuSelected(MenuItem selectedItem) {
        	getUI().getNavigator().navigateTo(SubirInformeView.NAME);
        }
    };
    


	

}

