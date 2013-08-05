package com.tti.componentes;

//import com.vaadin.server.ExternalResource;
import com.tti.SimpleLoginMainView;
import com.tti.SimpleLoginView;
import com.tti.views.AvanceView;
import com.tti.views.CitaView;
import com.tti.views.ListadoReunionesView;
import com.tti.views.Perfil;
import com.tti.views.ReprogramarView;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("serial")
public class PanelDeControlAlumno extends CustomComponent{
public PanelDeControlAlumno() {
	// TODO Auto-generated constructor stub
}
	public static final String NAME = "panelDeControl";
    private MenuBar menubar = new MenuBar();
//    private String username;
    
    @SuppressWarnings("unused")
	public PanelDeControlAlumno(String username) {
//    	username = "ASDAS";
        // Save reference to individual items so we can add sub-menu items to
        // them
    	final MenuBar.MenuItem inicio = menubar.addItem("TTi", goHome);
        final MenuBar.MenuItem reunion = menubar.addItem("Reunión", null);
        final MenuBar.MenuItem agendar = reunion.addItem("Agendar", agendarCita);
        
        reunion.addItem("Reprogramar", reprogramar);
        reunion.addSeparator();

        reunion.addItem("Ver Listado", verListado);

        final MenuBar.MenuItem avance = menubar.addItem("Mi Avance", null);
        avance.addItem("Revisar Mi Avance", miAvance);
        avance.addSeparator();

        avance.addItem("Actualizar mi Informe", menuCommand);

        final MenuBar.MenuItem profesorGuia = menubar.addItem("Profesor Guía", null);
        profesorGuia.addItem("Perfil", menuCommand);
        profesorGuia.addItem("Enviar un mensaje", menuCommand);
        profesorGuia.addSeparator();

        profesorGuia.addItem("Cambio Profesor", menuCommand);
        
        final MenuBar.MenuItem perfil = menubar.addItem(username, null);
        perfil.setIcon(new ThemeResource("../../imagenes/1369309745_user.png"));
        perfil.addItem("Editar Perfil", goProfile);
        perfil.addSeparator();
        perfil.addItem("Cerrar Sesión", logout);
        
        menubar.setHtmlContentAllowed(true);

        setCompositionRoot(new CssLayout(menubar));
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
    


	

}

