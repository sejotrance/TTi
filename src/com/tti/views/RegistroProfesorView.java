package com.tti.views;

import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

public class RegistroProfesorView extends CustomComponent implements View{
	
	private static final long serialVersionUID = -8559029417440049381L;
	public static final String NAME = "RegistrarProfesor";
	private static final String RUN = "RUN";
	private static final String NOMBRE = "Nombre";
    private static final String APELLIDO = "Apellido";
	private static final String[] nombreCampo = new String[] {RUN, NOMBRE, APELLIDO,
												"Email", "Teléfono", "Fecha de Nacimiento", "Dirección",
												"Comuna", "Región"};
	private PanelDeControl panelDeControl;
	private static final Label descripcion = new Label("<h2> Registrar un nuevo Profesor </h2>" + 
														"<p> Ingrese los datos en el formulario y presione el botón Registrar para guardar los cambios. </p>", ContentMode.HTML);
	private FormLayout editorLayout = new FormLayout();
	private FieldGroup camposProfesor = new FieldGroup();
	private Button botonGuardar;
	
	@Override
	public void enter(ViewChangeEvent event) {
		Rol userRol = getSession().getAttribute(Rol.class);
		if(userRol == Rol.SECRETARIA){		
			panelDeControl = new PanelDeControl(String.valueOf(getSession().getAttribute("user")), userRol);
			initEditor();
			botonGuardar = new Button("Registrar Profesor", new Button.ClickListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					
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

          for (String fieldName : nombreCampo) {
                  TextField field = new TextField(fieldName);
                  editorLayout.addComponent(field);
                  field.setWidth("100%");

                  camposProfesor.bind(field, fieldName);
          }
//          editorLayout.addComponent(removeContactButton);

          camposProfesor.setBuffered(false);
  }

}
