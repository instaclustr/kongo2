package com.instaclustr.kongo2;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

// at time LOAD goodsKey at warehouseKey onto truckKey
public class RFIDLoadEvent
{
	static boolean debug = false;
	
	long time; 
	String warehouseKey;
	String goodsKey;
	String truckKey;
	
	public RFIDLoadEvent(long time, String goodsKey, String warehouseKey, String truckKey)
	{
		this.time = time;
		this.goodsKey = goodsKey;
		this.warehouseKey = warehouseKey;
		this.truckKey = truckKey;
	}
	
	
	public RFIDLoadEvent()
	{
	}
	
	// event handler
	@Subscribe
	public void rfidLoadEvent(RFIDLoadEvent event)
	{
		// at time LOAD goodsKey at warehouseKey onto truckKey
		if (debug) System.out.println("RFID Load " + event);
		
		String locFrom = event.warehouseKey;
		String locTo = event.truckKey;
		
		// to move the goods need to get the Goods object itself
		// where is Goods now? claims to be at warehouseKey
		EventBus topicFrom = Simulate.topics.get(locFrom);
		
		// This requires access to the global list of allGoods - nasty?!
		Goods goods = Simulate.allGoods.get(event.goodsKey);
		
		// unregister goods from warehouse topic location
		if (debug) System.out.println("unregister from warehouse " + topicFrom.identifier());

		try
		{
			topicFrom.unregister(goods);
		}
		catch (Exception e)
		{
			// TODO Produce violation event rather than print this out
			System.out.println("LOAD EVENT Violation: Goods= " + event.goodsKey + " could not be loaded from warehouse location " + locFrom);
		}
		
		// change location
		EventBus topicTo = Simulate.topics.get(locTo);
		if (debug) System.out.println("register with truck " + topicTo.identifier());
		
		// TODO Should also check that truck is really at same location as warehouse and produce violation if not, this is a business rule.
		
		topicTo.register(goods);
			
		if (debug) System.out.println("post co-location event to " + topicTo.identifier());
		
		ColocatedCheckEvent ce = new ColocatedCheckEvent(time, event.goodsKey, event.truckKey);
		
		// Whoops, Goods have already been moved to the truck so will receive the check event themselves, better to move after checking!?
		
		// send this event to the location topics as all goods at the truck location will need to be check their rules.
		topicTo.post(ce);			
		
	}
}
