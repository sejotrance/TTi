package com.tti.componentes;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PerfilComponent extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	private String[] campos;
	private RegionProvinciaComunaComponent comboRPC;
	private FormLayout formulario;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public PerfilComponent(String[] campos) {
		this.campos = campos;
		buildMainLayout(this.campos, false);
		setCompositionRoot(mainLayout);
	}
	
	public PerfilComponent(String[] campos, boolean esLabel) {
		this.campos = campos;
		buildMainLayout(this.campos, esLabel);
		setCompositionRoot(mainLayout);
	}
	
	public String[] getCampos(){
		return campos;
	}
	

	@AutoGenerated
	private VerticalLayout buildMainLayout(String [] campos, boolean esLabel) {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		comboRPC = new RegionProvinciaComunaComponent(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		formulario = new FormLayout();
		for (String fieldName : campos) {
			if(esLabel){
				Label label = new Label(fieldName,ContentMode.HTML);
				formulario.addComponent(label);
			}else{
				TextField field = new TextField(fieldName);
				formulario.addComponent(field);
				field.setWidth("100%");
			}
            
            if(fieldName == "Direcci�n"){
            	formulario.addComponent(comboRPC);
            }
            
    }
		mainLayout.addComponent(formulario);
		
		return mainLayout;
	}

}
