package org.plasma.flux.consumer;

import org.plasma.flux.pipe.FlowableDataPipe;
import org.plasma.flux.pipe.FlowablePipe;

public abstract class FlowableDataConsumer<I> implements FlowableConsumer<I> {
    private FlowablePipe<I> input;
    public FlowableDataConsumer() {
    	this.input = new FlowableDataPipe<I>(this);
    }
    
	public FlowablePipe<I> getInputPipe() {
    	return this.input;
	}

	public abstract void receive(I input);
	 

}
