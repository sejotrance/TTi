package com.tti.componentes;

import java.util.List;

import com.tti.data.ApiDPA;
import com.tti.entidad.Sector;
import com.vaadin.data.Container.PropertySetChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.Container.PropertySetChangeEvent;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class RegionProvinciaComunaComponent extends CustomComponent{

	private static final long serialVersionUID = -7034670941893640320L;
	private static ApiDPA apiDpaConnection;
	private NativeSelect comboRegion;
	private NativeSelect comboProvincia;
	private NativeSelect comboComuna;
	private HorizontalLayout horizontalLayout;
	private VerticalLayout verticalLayout;
	
	public RegionProvinciaComunaComponent(boolean horizontal) {
		initLayout(horizontal);
		setDataSource();
	}
	
	private void initLayout(boolean horizontal){
		comboRegion = new NativeSelect("Región");
		comboProvincia = new NativeSelect("Provincia");
		comboComuna = new NativeSelect("Comuna");
		
		if(horizontal){
			horizontalLayout = new HorizontalLayout(comboRegion, comboProvincia, comboComuna);
			setCompositionRoot(horizontalLayout);
		}else{
			verticalLayout = new VerticalLayout(comboRegion, comboProvincia, comboComuna);
			setCompositionRoot(verticalLayout);
		}
				
	}
	
	@SuppressWarnings("unchecked")
	private void setDataSource(){
		apiDpaConnection = new ApiDPA();
		List<Sector> listaComunas = apiDpaConnection.getComunas("", "", "");
		List<Sector> listaProvincias = apiDpaConnection.getProvincias("", "");
		List<Sector> listaRegiones = apiDpaConnection.getRegiones("");
		
		BeanItemContainer<Sector> containerRegiones = new BeanItemContainer(Sector.class, listaRegiones);
		comboRegion.setContainerDataSource(containerRegiones);
		comboRegion.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		comboRegion.setItemCaptionPropertyId("nombre");
//		for (Sector region : listaRegiones) {
//			Item item = comboRegion.addItem(region.getCodigo());
//			item.getItemProperty("name").setValue(region.getNombre());
//		}
		
		for (Sector provincia : listaProvincias) {
			comboProvincia.addItem(provincia.getNombre());
		}
		
		for (Sector comuna : listaComunas) {
			comboComuna.addItem(comuna.getNombre());			
		}
	}
	
	private void setListeners(){
		
		comboRegion.addPropertySetChangeListener(new PropertySetChangeListener() {
			
			@Override
			public void containerPropertySetChange(PropertySetChangeEvent event) {
				Notification.show("Cambio");
				
			}
		});
	}
}
