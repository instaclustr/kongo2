package com.instaclustr.kongo2;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

// request check if goodsKey allowed on trucksKey given Goods already loaded
// Note that in theory we only need one Goods already on truck to be unhappy with new Goods so could stop checking remaining goods.
public class ColocatedCheckEvent
{
	long time; 
	String goodsKey;
	String truckKey;
	
	public ColocatedCheckEvent(long t, String goods, String truck)
	{
		this.time = t;
		this.goodsKey = goods;
		this.truckKey = truck;
	}
}
