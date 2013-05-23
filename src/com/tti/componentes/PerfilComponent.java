package com.tti.componentes;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PerfilComponent extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private NativeSelect nativeSelect_1;
	@AutoGenerated
	private TextField textField_3;
	@AutoGenerated
	private TextField textField_2;
	@AutoGenerated
	private TextField textField_1;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public PerfilComponent() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// textField_1
		textField_1 = new TextField();
		textField_1.setCaption("Nombre de Usuario");
		textField_1.setImmediate(false);
		textField_1.setWidth("-1px");
		textField_1.setHeight("-1px");
		mainLayout.addComponent(textField_1);
		
		// textField_2
		textField_2 = new TextField();
		textField_2.setCaption("Nombre");
		textField_2.setImmediate(false);
		textField_2.setWidth("-1px");
		textField_2.setHeight("-1px");
		mainLayout.addComponent(textField_2);
		
		// textField_3
		textField_3 = new TextField();
		textField_3.setCaption("Apellido");
		textField_3.setImmediate(false);
		textField_3.setWidth("-1px");
		textField_3.setHeight("-1px");
		mainLayout.addComponent(textField_3);
		
		// nativeSelect_1
		nativeSelect_1 = new NativeSelect();
		nativeSelect_1.setCaption("Facultad");
		nativeSelect_1.setImmediate(false);
		nativeSelect_1.setWidth("-1px");
		nativeSelect_1.setHeight("-1px");
		mainLayout.addComponent(nativeSelect_1);
		
		return mainLayout;
	}

}
