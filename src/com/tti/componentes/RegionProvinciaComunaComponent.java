package com.tti.componentes;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;

public class RegionProvinciaComunaComponent extends CustomComponent{

	private static final long serialVersionUID = -7034670941893640320L;
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
	
	private void setDataSource(){
		
	}
}
