package com.tti.componentes;

import com.vaadin.client.metadata.Property;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class GestorUsuariosComponent extends CustomComponent{

	private static final long serialVersionUID = 8014497738893322335L;
	private Table contactList = new Table();
    private TextField searchField = new TextField();
    private Button nuevoButton = new Button("Nuevo");
    private Button eliminarButton = new Button("Eliminar");
    private FormLayout editorLayout = new FormLayout();
    private FieldGroup editorFields = new FieldGroup();
    private static String[] fieldNames;
    private IndexedContainer personaContainer;
    
    private static final String NOMBRE = "Nombre";
    private static final String APELLIDO = "Apellido";
    private static final String EMAIL = "Email";


	public GestorUsuariosComponent(String[] fieldNames, IndexedContainer personaContainer, boolean dummy) {
		
		GestorUsuariosComponent.fieldNames = fieldNames;
		if(dummy){
			this.personaContainer = createDummyDatasource();
		}else{
			this.personaContainer = personaContainer;
		}
		initLayout();
		initContactList();
		initEditor();
		initSearch();
		initAddRemoveButtons();
	}
	
	private void initLayout() {

        HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
        VerticalLayout leftLayout = new VerticalLayout();
        splitPanel.addComponent(leftLayout);
        splitPanel.addComponent(editorLayout);
        leftLayout.addComponent(contactList);
        HorizontalLayout bottomLeftLayout = new HorizontalLayout();
        leftLayout.addComponent(bottomLeftLayout);
        bottomLeftLayout.addComponent(searchField);
        bottomLeftLayout.addComponent(nuevoButton);

        leftLayout.setSizeFull();

        leftLayout.setExpandRatio(contactList, 1);
        contactList.setSizeFull();

        bottomLeftLayout.setWidth("100%");
        searchField.setWidth("100%");
        bottomLeftLayout.setExpandRatio(searchField, 1);

        editorLayout.setMargin(true);
        editorLayout.setVisible(false);
        
        setCompositionRoot(splitPanel);
	}
	
	private void initEditor() {

        for (String fieldName : fieldNames) {
                TextField field = new TextField(fieldName);
                editorLayout.addComponent(field);
                field.setWidth("100%");

                editorFields.bind(field, fieldName);
        }
        editorLayout.addComponent(eliminarButton);

        editorFields.setBuffered(false);
	}
	
	private void initSearch() {

		/*
		 * We want to show a subtle prompt in the search field. We could also
		 * set a caption that would be shown above the field or description to
		 * be shown in a tooltip.
		 */
		searchField.setInputPrompt("Search contacts");

		/*
		 * Granularity for sending events over the wire can be controlled. By
		 * default simple changes like writing a text in TextField are sent to
		 * server with the next Ajax call. You can set your component to be
		 * immediate to send the changes to server immediately after focus
		 * leaves the field. Here we choose to send the text over the wire as
		 * soon as user stops writing for a moment.
		 */
		searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);

		/*
		 * When the event happens, we handle it in the anonymous inner class.
		 * You may choose to use separate controllers (in MVC) or presenters (in
		 * MVP) instead. In the end, the preferred application architecture is
		 * up to you.
		 */
		searchField.addTextChangeListener(new TextChangeListener() {
			public void textChange(final TextChangeEvent event) {

				/* Reset the filter for the contactContainer. */
				personaContainer.removeAllContainerFilters();
				personaContainer.addContainerFilter(new ContactFilter(event
						.getText()));
			}
		});
	}

	/*
	 * A custom filter for searching names and companies in the
	 * contactContainer.
	 */
	private class ContactFilter implements Filter {
		private String needle;

		public ContactFilter(String needle) {
			this.needle = needle.toLowerCase();
		}

		public boolean passesFilter(Object itemId, Item item) {
			String haystack = ("" + item.getItemProperty(NOMBRE).getValue()
					+ item.getItemProperty(APELLIDO).getValue() + item
					.getItemProperty(EMAIL).getValue()).toLowerCase();
			return haystack.contains(needle);
		}

		public boolean appliesToProperty(Object id) {
			return true;
		}
	}

	private void initAddRemoveButtons() {
		nuevoButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {

				/*
				 * Rows in the Container data model are called Item. Here we add
				 * a new row in the beginning of the list.
				 */
				personaContainer.removeAllContainerFilters();
				Object contactId = personaContainer.addItemAt(0);

				/*
				 * Each Item has a set of Properties that hold values. Here we
				 * set a couple of those.
				 */
				contactList.getContainerProperty(contactId, NOMBRE).setValue(
						"New");
				contactList.getContainerProperty(contactId, APELLIDO).setValue(
						"Contact");

				/* Lets choose the newly created contact to edit it. */
				contactList.select(contactId);
			}
		});

		eliminarButton.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				Object contactId = contactList.getValue();
				contactList.removeItem(contactId);
			}
		});
	}
	
	/*
	 * Generate some in-memory example data to play with. In a real application
	 * we could be using SQLContainer, JPAContainer or some other to persist the
	 * data.
	 */
	@SuppressWarnings("unchecked")
	private static IndexedContainer createDummyDatasource() {
		IndexedContainer ic = new IndexedContainer();

		for (String p : fieldNames) {
			ic.addContainerProperty(p, String.class, "");
		}

		/* Create dummy data by randomly combining first and last names */
		String[] fnames = { "Pedro", "Alicia", "Jose", "Miguel", "Olivia",
				"Nina", "Alex", "Rita", "Daniel", "Humberto", "Enrique", "René",
				"Lisa", "Mariela", "Karin", "José", "Martina" };
		String[] lnames = { "Hurtado", "Acuña", "Alarcón", "Vejar", "Castillo",
				"Gutierrez", "Gonzalez", "Pérez", "Henriquez", "Aguayo", "Paredes",
				"Jacobi", "Gárate", "Godoy", "López" };
		for (int i = 0; i < 300; i++) {
			Object id = ic.addItem();
			ic.getContainerProperty(id, NOMBRE).setValue(
					fnames[(int) (fnames.length * Math.random())]);
			ic.getContainerProperty(id, APELLIDO).setValue(
					lnames[(int) (lnames.length * Math.random())]);
		}

		return ic;
	}
	
	private void initContactList() {
		contactList.setContainerDataSource(personaContainer);
		contactList.setVisibleColumns(new String[] { NOMBRE, APELLIDO, EMAIL });
		contactList.setSelectable(true);
		contactList.setImmediate(true);

		contactList.addValueChangeListener(new ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				Object contactId = contactList.getValue();

				/*
				 * When a contact is selected from the list, we want to show
				 * that in our editor on the right. This is nicely done by the
				 * FieldGroup that binds all the fields to the corresponding
				 * Properties in our contact at once.
				 */
				if (contactId != null)
					editorFields.setItemDataSource(contactList
							.getItem(contactId));

				editorLayout.setVisible(contactId != null);
			}
		});
	}
}
