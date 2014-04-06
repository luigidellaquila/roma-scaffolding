package org.romaframework.scaffolding.view.domain.header;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HeaderMenuElementComparator implements Comparator<HeaderMenuElement> {

	private List<String> order = new ArrayList<String>();
	
	public int compare(HeaderMenuElement arg0, HeaderMenuElement arg1) {
		
		String argzero = arg0.getClass().getSimpleName();
		String arguno = arg1.getClass().getSimpleName();
		if(order.contains(argzero) && !(order.contains(arguno)))
			return -1;
		if(!(order.contains(argzero)) && (order.contains(arguno)))
			return 1;
		if(!(order.contains(argzero)) && !(order.contains(arguno))){
			return 0;
		}
		if(order.contains(argzero) && order.contains(arguno)){
			if (order.indexOf(argzero)<order.indexOf(arguno))
				return -1;
			else 
				return 1;
		}
		return 0;

	}
	public List<String> getOrder() {
		return order;
	}
	public void setOrder(List<String> orderedList) {
		this.order = orderedList;
	}

}
