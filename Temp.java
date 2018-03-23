package com.instaclustr.kongo2;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Temperature objects have a Goods temperature category number (0-4) and associated range.
 */

public class Temp
{
	double min = -273;
	double max = 1000;
	int subCatNum = -1;

	// given a category number create a temp object with correct range
	public Temp(int subCatNum)
	{
		this.subCatNum = subCatNum;
		switch (subCatNum)
		{
			case -1: break; // any temp allowed
			case 0: max = -20; break;
			case 1: min = 2; max = 8; break;
			case 2: min = 8; max = 15; break;
			case 3: min = 15; max = 25; break;
			case 4: min = 1; max = 30; break;
			default: break;
		}
	}
	
	// check if the value provided is in range of this temp
	boolean tempInRange(double t)
	{
		return (t >= min && t <= max);
	}
	
	// return a random temp which is always in range
	double randomTempInRange()
	{
		Random r = new Random();
		double d = min + (max - min) * r.nextDouble();
		return d;
	}
	
	// return a random temp which has a probability of being out of range
	double randomTemp(double prob)
	{
		Random r = new Random();
		if (r.nextDouble() >= prob)
			return randomTempInRange();
		else
		{
			if (r.nextBoolean())
				return max + 1;
			else return min - 1;
		}		
	}
}
