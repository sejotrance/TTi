package com.tti.componentes;

//import com.vaadin.server.ExternalResource;
import com.tti.SimpleLoginMainView;
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
    
    public PanelDeControlAlumno(String username) {
//    	username = "ASDAS";
        // Save reference to individual items so we can add sub-menu items to
        // them
    	final MenuBar.MenuItem inicio = menubar.addItem("TTi", goHome);
        final MenuBar.MenuItem reunion = menubar.addItem("Reuni�n", null);
        final MenuBar.MenuItem agendar = reunion.addItem("Agendar", null);
        
        reunion.addItem("Reprogramar", menuCommand);
        reunion.addSeparator();

        reunion.addItem("Cancelar", menuCommand);

        final MenuBar.MenuItem avance = menubar.addItem("Mi Avance", null);
        avance.addItem("Revisar Mi Avance", menuCommand);
        avance.addSeparator();

        avance.addItem("Actualizar mi Informe", menuCommand);

        final MenuBar.MenuItem profesorGuia = menubar.addItem("Profesor Gu�a", null);
        profesorGuia.addItem("Perfil", menuCommand);
        profesorGuia.addItem("Enviar un mensaje", menuCommand);
        profesorGuia.addSeparator();

        profesorGuia.addItem("Cambio Profesor", menuCommand);
        profesorGuia.addItem("Registrar Profesor Gu�a", menuCommand);
        
        final MenuBar.MenuItem perfil = menubar.addItem(username, null);
        perfil.addItem("Editar Perfil", menuCommand);
        perfil.addSeparator();
        perfil.addItem("Cerrar Sesi�n", menuCommand);
        
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

	

}

