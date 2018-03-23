/*
 * Returns String of categories not allowed in truck with the Goods.
 */

package com.instaclustr.kongo2;

public class Rules {
	
	public static String goodsNotAllowedInTruck(String Me)
	{
		String s = "";
		// Fragile and Bulky
		if (Me.contains("F"))
			s += "B";
		if (Me.contains("B"))
			s += "F";
		 
		// Hazardous and (edible or medicinal)
		if (Me.contains("H"))
			s += "EM";
		if (Me.contains("E"))
			s += "H";
		if (Me.contains("M"))
			s += "H";
		
		if (Me.contains("E"))
			s += "M";
		if (Me.contains("M"))
			s += "E";
		
		return s;
	}
	
	public static String goodsNotAllowedInWarehouse(String Me)
	{
		return "";  // everything allowed in warehouses
	}
	

}
