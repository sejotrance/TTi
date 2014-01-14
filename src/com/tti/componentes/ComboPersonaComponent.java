package com.tti.componentes;

import java.util.Collection;
import java.util.List;

import org.drools.rule.builder.dialect.java.parser.JavaParser.formalParameter_return;

import ttiws.model.PersonaModel;
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

public class ComboPersonaComponent extends CustomComponent {
	private ComboBox comboProfesor;
	private static final String PROPERTY_NAME = "name";
	private static final String PROPERTY_VALUE = "value";
	private static String value = "";
	List<PersonaModel> listaPersonas;
	
	public ComboPersonaComponent(String caption, String codTipoPersona) {
		this.value = "";
		listaPersonas = WSPersonaListar.listarPersonas(codTipoPersona);
		//SETTING COMBO
		comboProfesor = new ComboBox(caption, this.getContainer());
		comboProfesor.setInputPrompt("Seleccione un valor");
		comboProfesor.setItemCaptionPropertyId(this.PROPERTY_NAME);
		comboProfesor.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		comboProfesor.setFilteringMode(FilteringMode.CONTAINS);
		comboProfesor.setImmediate(true);
		comboProfesor.setNullSelectionAllowed(false);
		addHandlers();
		addListeners();
		//SETTING LAYOUT
		VerticalLayout layout = new VerticalLayout(comboProfesor);
		setCompositionRoot(layout);
	}
	
	private void fillContainer(IndexedContainer container) {
        container.addContainerProperty(PROPERTY_NAME, String.class,
                null);
        container.addContainerProperty(PROPERTY_VALUE, String.class,
                null);
        
        for (PersonaModel persona : listaPersonas) {
        	String name = persona.getPer_Nombre() + " " +  persona.getPer_Apellido_Paterno() + " " + persona.getPer_Apellido_Materno();
            String id = Integer.toString(persona.getPer_Id());
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
		 comboProfesor.setNewItemHandler(new NewItemHandler() {
	            @Override
	            public void addNewItem(final String newItemCaption) {
	                boolean newItem = true;
	                for (final Object itemId : comboProfesor.getItemIds()) {
	                    if (newItemCaption.equalsIgnoreCase(comboProfesor
	                            .getItemCaption(itemId))) {
	                        newItem = false;
	                        break;
	                    }
	                }
	                if (newItem) {
	                    // Adds new option
	                    if (comboProfesor.addItem(newItemCaption) != null) {
	                        final Item item = comboProfesor.getItem(newItemCaption);
	                        item.getItemProperty(PROPERTY_NAME)
	                                .setValue(newItemCaption);
	                        comboProfesor.setValue(newItemCaption);
	                    }
	                }
	            }
	        });

	 }
	 
	 private void addListeners(){
		 comboProfesor.addValueChangeListener(new ValueChangeListener() {
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

