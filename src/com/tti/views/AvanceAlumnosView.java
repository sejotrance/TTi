package com.tti.views;

import java.util.List;

import ttiws.entidades.ResultReporteAlumnoTT;
import ttiws.model.PersonaModel;
import ttiws.serviciosPersona.WSPersonaConsultar;
import ttiws.serviciosPersona.WSPersonaListar;
import ttiws.serviciosReportes.WSReporteAlumnoTT;

import com.tti.componentes.CalificacionesAlumnoComponent;
import com.tti.componentes.PanelDeControl;
import com.tti.componentes.SinPermisoComponent;
import com.tti.enums.Rol;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class AvanceAlumnosView extends CustomComponent implements View{

	private static final long serialVersionUID = 6387026692191058910L;
	public static final String NAME = "AvanceAlumnos";
	
	private PanelDeControl panelDeControl;
	private CalificacionesAlumnoComponent calificacionesAlumno;
	private VerticalLayout verticalLayout;

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
			setLayout(codProfesor);
			Component ret = createChart();
	        ret.setWidth("100%");
	        ret.setHeight("450px");
	        setCompositionRoot(new CssLayout(panelDeControl, ret ,verticalLayout));
		}else{
			//Mostrar que no tiene los permisos
			SinPermisoComponent sinPermiso = new SinPermisoComponent();
		    setCompositionRoot(new CssLayout(sinPermiso));
		}
		
	}
	
	private void setLayout(int codProfesor){
		List<PersonaModel> alumnosProfesor = WSPersonaListar.listarPersonasPorProfesor(codProfesor);
		verticalLayout = new VerticalLayout();
		if(alumnosProfesor.size() > 0){
			for (PersonaModel alumno : alumnosProfesor) {
				int idAlumno = alumno.getPer_Id();
				String caption = alumno.getPer_Nombre() + " " + alumno.getPer_Apellido_Paterno() + alumno.getPer_Apellido_Materno();
				CalificacionesAlumnoComponent calificacionesAlumno = new CalificacionesAlumnoComponent(idAlumno, caption);
				verticalLayout.addComponent(calificacionesAlumno);
			}
		}
	}
	
	 public static Chart createChart() {
	        Chart chart = new Chart(ChartType.PIE);

	        Configuration conf = chart.getConfiguration();

	        conf.setTitle("Resultados de mis alumnos si el semestre terminase hoy.");

	        PlotOptionsPie plotOptions = new PlotOptionsPie();
	        plotOptions.setCursor(Cursor.POINTER);
	        Labels dataLabels = new Labels();
	        dataLabels.setEnabled(true);
	        dataLabels.setColor(new SolidColor(0, 0, 0));
	        dataLabels.setConnectorColor(new SolidColor(0, 0, 0));
	        dataLabels
	                .setFormatter("''+ this.point.name +': '+ this.percentage +' %'");
	        plotOptions.setDataLabels(dataLabels);
	        conf.setPlotOptions(plotOptions);

	        DataSeries series = new DataSeries();
	        series.add(new DataSeriesItem("Reprobado", 45.0, new SolidColor("#A60000")));
	        series.add(new DataSeriesItem("Pendiente", 42.2, new SolidColor("#FFE700")));
	        DataSeriesItem aprobado = new DataSeriesItem("Aprobado", 12.8, new SolidColor("#008500"));
	        aprobado.setSliced(true);
	        aprobado.setSelected(true);
	        series.add(aprobado);
//	        series.add(new DataSeriesItem("Por aprobar", 8.5,new SolidColor("#00A480")));
//	        series.add(new DataSeriesItem("Indiferente", 6.9, new SolidColor("#2B4181")));
	        conf.setSeries(series);

	        chart.drawChart(conf);

	        return chart;
	    }
}
