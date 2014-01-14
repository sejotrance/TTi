package com.tti.componentes;

import java.util.List;

import ttiws.model.CalificacionModel;
import ttiws.serviciosAlumno.WSAlumnoCalificacionListar;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class CalificacionesAlumnoComponent extends CustomComponent{

	private static final long serialVersionUID = 1L;
	private Table tablaCalificaciones;
	
	public CalificacionesAlumnoComponent(int idAlumno, String caption) {
		
		 //LISTAMOS LAS CALIFICACIONES DEL ALUMNO
		 List<CalificacionModel> calificaciones = WSAlumnoCalificacionListar.listarCalificaciones(idAlumno);
		BeanItemContainer<CalificacionModel> container = new BeanItemContainer<CalificacionModel>(CalificacionModel.class);
		for (CalificacionModel calificacion : calificaciones) {
			container.addBean(calificacion);
	        container.sort(new Object[]{"cal_Fecha"}, new boolean[]{true});
		};
		tablaCalificaciones = new Table(caption, container);
		tablaCalificaciones.setSizeFull();
		tablaCalificaciones.setHeight("200px");
		tablaCalificaciones.setColumnHeader("cal_Calificacion", "Calificacion");
		tablaCalificaciones.setColumnHeader("cal_Porcentaje", "Porcentaje");
		tablaCalificaciones.setColumnHeader("cal_Descripcion", "Descripcion");
		tablaCalificaciones.setColumnHeader("cal_Fecha", "Fecha");
		tablaCalificaciones.setColumnHeader("cal_Es_Informe", "Es Informe");
		tablaCalificaciones.setVisibleColumns(new String[] { "cal_Calificacion", "cal_Porcentaje", "cal_Descripcion", "cal_Fecha", "cal_Es_Informe"});
		VerticalLayout layout = new VerticalLayout(tablaCalificaciones);
		setCompositionRoot(layout);
	}
}
