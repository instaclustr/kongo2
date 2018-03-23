package com.instaclustr.kongo2;

import java.util.Random;
import java.util.UUID;

/*
 * Warehouses have an x, y location, an id, and may be temperature controlled.
 */
public class Warehouses
{
	final int x;
	final int y;
	final String prefix = "warehouses_"; // what am i?
    final String id;
    boolean tempControlled = false;
    int tempRange = -1;
    Temp temp = null;

    // create new warehouse object
	public Warehouses(int x, int y)
	{
	    this.id = this.prefix + randomUUID();
	    this.x = x;
	    this.y = y;
	    
	    Random rand = new Random();
	    
	    // temp controlled warehouse?
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
            s += "Warehouse Id=" + id;
            s += ", x loc= " + x;
            s += ", y loc= " + y;
            s += ", temp controlled=" + tempControlled;
            if (tempControlled) s += ", temp category=" + tempRange;
            return s;
    }
}