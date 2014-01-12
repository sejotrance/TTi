package com.tti.componentes;

import java.util.Collection;
import java.util.List;

import org.drools.rule.builder.dialect.java.parser.JavaParser.formalParameter_return;

import ttiws.model.CarreraModel;
import ttiws.model.PersonaModel;
import ttiws.serviciosCarrera.WSCarreraListar;
import ttiws.serviciosPersona.WSPersonaListar;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

public class ComboCarreraComponent extends CustomComponent {
	private ComboBox comboCarrera;
	private static final String PROPERTY_NAME = "name";
	private static final String PROPERTY_VALUE = "value";
	private static String value = "";
	List<CarreraModel> listaCarreras;
	
	public ComboCarreraComponent(String caption, int codEscuela) {
		this.value = "";
		listaCarreras = WSCarreraListar.listarCarreras(codEscuela);
		//SETTING COMBO
		comboCarrera = new ComboBox(caption, this.getContainer());
		comboCarrera.setInputPrompt("Seleccione un valor");
		comboCarrera.setItemCaptionPropertyId(this.PROPERTY_NAME);
		comboCarrera.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		comboCarrera.setFilteringMode(FilteringMode.CONTAINS);
		comboCarrera.setImmediate(true);
		comboCarrera.setNullSelectionAllowed(false);
		addHandlers();
		addListeners();
		//SETTING LAYOUT
		VerticalLayout layout = new VerticalLayout(comboCarrera);
		setCompositionRoot(layout);
	}
	
	private void fillContainer(IndexedContainer container) {
        container.addContainerProperty(PROPERTY_NAME, String.class,
                null);
        container.addContainerProperty(PROPERTY_VALUE, String.class,
                null);
        
        for (CarreraModel carrera : listaCarreras) {
        	String name = carrera.getCar_Codigo() + " : " + carrera.getCar_Nombre();
            String id = Integer.toString(carrera.getCar_Id());
            Item item = container.addItem(id);
            item.getItemProperty(PROPERTY_NAME).setValue(name);
            item.getItemProperty(PROPERTY_VALUE).setValue(id);
		}
        container.sort(new Object[] { PROPERTY_NAME },
                new boolean[] { true });
    }
	
	 public IndexedContainer getContainer() {
	        IndexedContainer c = new IndexedContainer();
	        fillContainer(c);
	        return c;
	    }
	 
	 public String getValue(){
		 return this.value;
	 }
	 
	 private void addHandlers(){
		 comboCarrera.setNewItemHandler(new NewItemHandler() {
	            @Override
	            public void addNewItem(final String newItemCaption) {
	                boolean newItem = true;
	                for (final Object itemId : comboCarrera.getItemIds()) {
	                    if (newItemCaption.equalsIgnoreCase(comboCarrera
	                            .getItemCaption(itemId))) {
	                        newItem = false;
	                        break;
	                    }
	                }
	                if (newItem) {
	                    // Adds new option
	                    if (comboCarrera.addItem(newItemCaption) != null) {
	                        final Item item = comboCarrera.getItem(newItemCaption);
	                        item.getItemProperty(PROPERTY_NAME)
	                                .setValue(newItemCaption);
	                        comboCarrera.setValue(newItemCaption);
	                    }
	                }
	            }
	        });

	 }
	 
	 private void addListeners(){
		 comboCarrera.addValueChangeListener(new ValueChangeListener() {
	            @Override
	            public void valueChange(ValueChangeEvent event) {
	                final String valueString = String.valueOf(event.getProperty()
	                        .getValue());
	                Notification.show("Value changed:", valueString,
	                        Type.TRAY_NOTIFICATION);
	                value = valueString;
	            }
	        });
	 }
}

