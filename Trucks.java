package com.instaclustr.kongo2;


import java.util.Random;
import java.util.UUID;

/*
 * Trucks move from one warehouse location to another warehouse location and transport Goods.
 * They can have a temperature controlled environment.
 */
public class Trucks
{
	final String prefix = "trucks_"; // what am i?
    final String id;
    boolean tempControlled = false;
    int tempRange = -1;
    Temp temp = null;	// new temp object
    
    // Hack: keep track of what Goods categories are on board by using a dummy Goods object
    Goods categoriesOnBoard = new Goods();

    // create new Truck object
	public Trucks()
	{
	    this.id = this.prefix + randomUUID();
	    
	    Random rand = new Random();
	    if (rand.nextDouble() < 0.8)
        {
	    		tempControlled = true;
      	  	int r = rand.nextInt(5);
      	  	tempRange = r;
      	  	temp = new Temp(r);
        }
	    else temp = new Temp(-1);
	}

	
	public static String randomUUID()
    {
		UUID uuid = UUID.randomUUID();
		String r = uuid.toString();
		return r;
    }
	 
	 public String toStr()
     {
		 String s = "";
         s += "Truck Id=" + id;
         s += ", temp controlled " + tempControlled;
         if (tempControlled)
        	 	s += ", temp range " + tempRange;
         return s;
     }
	 
	 // when unloaded reset cats
	 // Note that we don't have dry and temp cats here yet as not used to check if loading is ok
	 public void resetCats()
	 {
		 categoriesOnBoard.categoryBulky = false;
		 categoriesOnBoard.categoryEdible = false;
		 categoriesOnBoard.categoryFragile = false;
		 categoriesOnBoard.categoryHazardous = false;
		 categoriesOnBoard.categoryMedicinal = false;
		 categoriesOnBoard.categoryPerishable = false;
	 }
	 
	 // Logical OR new goods loaded onto truck with existing categories
	 public void updateCategories(Goods g)
	 {
		 categoriesOnBoard.categoryBulky |= g.categoryBulky;
		 categoriesOnBoard.categoryEdible |= g.categoryEdible;
		 categoriesOnBoard.categoryFragile |= g.categoryFragile;
		 categoriesOnBoard.categoryHazardous |= g.categoryHazardous;
		 categoriesOnBoard.categoryMedicinal |= g.categoryMedicinal;
		 categoriesOnBoard.categoryPerishable |= g.categoryPerishable;
	 }
	 
	 // check temp rules for delivery to warehouse
	 public boolean canDeliverToWarehouse(Warehouses w)
	 {
		 // trucks not temp controlled can unload anywhere
		if (!tempControlled)
	  		return true;
	  	
		// identical temp ranges can unload
		if (tempRange == w.tempRange)
			return true;
			
		// if truck is ambient temp can unload to any except freezing
		if (tempRange == 4)
			return (w.tempRange == 1 || w.tempRange == 2 || w.tempRange == 3 || w.tempRange == 4);
			
		return false;
	 }
}
