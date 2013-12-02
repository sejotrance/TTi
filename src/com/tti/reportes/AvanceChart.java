package com.tti.reportes;

	import com.vaadin.addon.charts.Chart;
	import com.vaadin.addon.charts.demoandtestapp.AbstractVaadinChartExample;
	import com.vaadin.addon.charts.model.ChartType;
	import com.vaadin.addon.charts.model.Configuration;
	import com.vaadin.addon.charts.model.HorizontalAlign;
	import com.vaadin.addon.charts.model.LayoutDirection;
	import com.vaadin.addon.charts.model.Legend;
	import com.vaadin.addon.charts.model.ListSeries;
	import com.vaadin.addon.charts.model.PlotOptionsColumn;
	import com.vaadin.addon.charts.model.Tooltip;
	import com.vaadin.addon.charts.model.VerticalAlign;
	import com.vaadin.addon.charts.model.XAxis;
	import com.vaadin.addon.charts.model.YAxis;
	import com.vaadin.ui.Component;

	@SuppressWarnings("serial")
	public class AvanceChart extends AbstractVaadinChartExample {

	    @Override
	    public String getDescription() {
	        return "Basic column";
	    }

	    @Override
	    protected Component getChart() {
	        Chart chart = new Chart(ChartType.AREA);

	        Configuration conf = chart.getConfiguration();

	        conf.setTitle("Progreso durante el periodo");
//	        conf.setSubTitle("");

	        XAxis x = new XAxis();
	        x.setCategories("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago",
	                "Sep", "Oct", "Nov", "Dic");
	        conf.addxAxis(x);

	        YAxis y = new YAxis();
	        y.setMin(0);
	        y.setTitle("Porcentaje de avance según el profesor guía");
	        conf.addyAxis(y);

	        Legend legend = new Legend();
	        legend.setLayout(LayoutDirection.VERTICAL);
	        legend.setBackgroundColor("#FFFFFF");
	        legend.setHorizontalAlign(HorizontalAlign.LEFT);
	        legend.setVerticalAlign(VerticalAlign.TOP);
	        legend.setX(100);
	        legend.setY(70);
	        legend.setFloating(true);
	        legend.setShadow(true);
	        conf.setLegend(legend);

	        Tooltip tooltip = new Tooltip();
	        tooltip.setFormatter("this.x +': '+ this.y +' '");
	        conf.setTooltip(tooltip);

	        PlotOptionsColumn plot = new PlotOptionsColumn();
	        plot.setPointPadding(0.2);
	        plot.setBorderWidth(0);

	        conf.addSeries(new ListSeries("Informe", 5, 6, 15, 25, 40,
	                55, 70, 70, 70, 85, 90, 98));
	        conf.addSeries(new ListSeries("Aplicación", 1, 3, 10, 15,
	                15, 30, 35, 50, 60, 80, 90, 95));
//	        conf.addSeries(new ListSeries("London", 48.9, 38.8, 39.3, 41.4, 47.0,
//	                48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2));
//	        conf.addSeries(new ListSeries("Berlin", 42.4, 33.2, 34.5, 39.7, 52.6,
//	                75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1));

	        chart.drawChart(conf);
	        return chart;
	    }
	}
