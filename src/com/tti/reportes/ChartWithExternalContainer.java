package com.tti.reportes;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.demoandtestapp.AbstractVaadinChartExample;
import com.vaadin.addon.charts.model.AbstractSeries;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ContainerDataSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.addon.charts.model.YAxis;
import com.tti.reportes.ExampleUtil;
import com.vaadin.data.Container;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;

public class ChartWithExternalContainer extends AbstractVaadinChartExample {

    @Override
    public String getDescription() {
        return "Grafico comparativo del avance de los alumnos";
    }

    @Override
    protected Component getChart() {
        HorizontalLayout lo = new HorizontalLayout();
        lo.setSpacing(true);
        Container vaadinContainer = ExampleUtil.getOrderContainer();

        ContainerDataSeries container1 = createContainerView1(vaadinContainer);
        ContainerDataSeries container2 = createContainerView2(vaadinContainer);
        Component table = createTable(vaadinContainer);
        Component chart1 = createChart1(container1);
        Component chart2 = createChart2(container2);

        table.setSizeFull();
        chart1.setSizeFull();
        chart2.setSizeFull();

        lo.setWidth("100%");
        lo.setHeight("450px");
        lo.addComponents(table);
        lo.addComponent(chart1);
        lo.addComponent(chart2);
        lo.setExpandRatio(table, 0.2f);
        lo.setExpandRatio(chart1, 0.4f);
        lo.setExpandRatio(chart2, 0.4f);
        return lo;
    }

    private ContainerDataSeries createContainerView1(Container vaadinContainer) {
        ContainerDataSeries container = new ContainerDataSeries(vaadinContainer);
        container.setName("Calificaciones");
        container.setPlotOptions(new PlotOptionsPie());
        container
                .setYPropertyId(ExampleUtil.CALIFICACIONES);
        container
                .setNamePropertyId(ExampleUtil.NOMBRE);
        return container;
    }

    private ContainerDataSeries createContainerView2(Container vaadinContainer) {
        ContainerDataSeries container = new ContainerDataSeries(vaadinContainer);
        container.setName("Promedio de Avance");
        container.setPlotOptions(new PlotOptionsColumn());
        container
                .setYPropertyId(ExampleUtil.PROMEDIO_NOTAS);
        container
                .setNamePropertyId(ExampleUtil.NOMBRE);
        return container;
    }

    private Component createTable(Container container) {
        Table t = new Table();
        t.setCaption("Listado de Alumnos");
        t.setContainerDataSource(container);
        t.setItemCaptionMode(ItemCaptionMode.ID);
        t.setImmediate(true);
        return t;
    }

    public static Chart createChart1(Series container) {
        final Chart chart = new Chart();

        final Configuration configuration = new Configuration();
        configuration.getChart().setType(ChartType.PIE);
        configuration.getTitle().setText("Comparativo Calificaciones");
        configuration.getLegend().setEnabled(false);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        configuration.setPlotOptions(plotOptions);

        configuration.setSeries(container);
//        System.out.println(configuration.toString());
        chart.drawChart(configuration);
        return chart;
    }

    public static Chart createChart2(AbstractSeries container) {
        final Chart chart = new Chart();

        final Configuration configuration = new Configuration();
        configuration.getChart().setType(ChartType.COLUMN);
        configuration.getTitle().setText("Comparativo Avance");
        configuration.getLegend().setEnabled(false);

        YAxis ax = new YAxis();
        ax.setTitle("");
        configuration.addyAxis(ax);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        configuration.setPlotOptions(plotOptions);

        configuration.setSeries(container);
//        System.out.println(configuration.toString());
        chart.drawChart(configuration);

        return chart;
    }
}

