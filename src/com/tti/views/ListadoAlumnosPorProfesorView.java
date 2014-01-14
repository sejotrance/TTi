package com.tti.views;

import java.util.List;

import ttiws.entidades.Persona;
import ttiws.entidades.ResultReporteAlumnoTT;
import ttiws.model.PersonaModel;
import ttiws.serviciosPersona.WSPersonaConsultar;
import ttiws.serviciosPersona.WSPersonaListar;
import ttiws.serviciosReportes.WSReporteAlumnoTT;

import com.tti.componentes.GestorPersonaComponent;
import com.tti.componentes.GestorUsuariosComponent;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class ListadoAlumnosPorProfesorView extends CustomComponent implements View{
	
	public static final String NAME = "MisAlumnos";
	private PanelDeControl panelDeControl;
	private GestorPersonaComponent gestorUsuarios;
	private static final String RUN = "per_Run";
	private static final String NOMBRE = "per_Nombre";
    private static final String APELLIDO = "per_Apellido_Paterno";
    private static final Label descripcionLabel = new Label("<h2> Listado de Alumnos Vigentes </h2>" + 
			"<p> A continuacion se listan los alumnos vigentes en TTi. </p>", ContentMode.HTML);
	private static final String[] nombreCampo = new String[] { RUN, NOMBRE, APELLIDO, "car_Nombre", "TTi_Nombre", "TTi_Tit1", "TTi_Tit2"};
	
	@Override
	public void enter(ViewChangeEvent event) {
		List<Rol> userRol = (List<Rol>) getSession().getAttribute("roles");
		if(userRol.contains(Rol.PROFESOR)){
			String userName = String.valueOf(getSession().getAttribute("user"));
			int codProfesor = 3; //TEST USER
			if(!userName.equals("profesor@utem.cl")){//TEST USER
				PersonaModel profesor = WSPersonaConsultar.consultarPorRun(userName);
				codProfesor = profesor.getPer_Id();
			}
			panelDeControl = new PanelDeControl(userName, userRol);
			List<ResultReporteAlumnoTT> resultadoReporte = WSReporteAlumnoTT.listarReporteAlumnoPorProfesor(codProfesor); //LISTAR ALUMNOS
			BeanItemContainer<ResultReporteAlumnoTT> container = new BeanItemContainer<ResultReporteAlumnoTT>(ResultReporteAlumnoTT.class);
			for (ResultReporteAlumnoTT alumno : resultadoReporte) {
				container.addBean(alumno);
		        container.sort(new Object[]{"per_Id"}, new boolean[]{true});
			};
			gestorUsuarios = new GestorPersonaComponent(nombreCampo, container, false);
			setCompositionRoot(new CssLayout(panelDeControl, descripcionLabel, gestorUsuarios));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
		
	}

}
