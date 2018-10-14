package org.plasma.flux;

import java.util.ArrayList;
import java.util.List;

import org.plasma.flux.producer.Producer;

public class DataFlow {
    private List<Producer<?>> producers;
	public DataFlow() {		
	}
    public void addProducer(Producer<?> producer) {
    	if (producers == null)
    		this.producers = new ArrayList<Producer<?>>();
    	this.producers.add(producer);
    }
    
    public void execute() {
        for (Producer<?> producer : this.producers)
        	producer.execute();   	
    }
      
}
