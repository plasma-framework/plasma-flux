package org.plasma.flux.consumer;

import org.plasma.flux.pipe.Pipe;
import org.plasma.flux.pipe.SyncDataPipe;
import org.plasma.flux.pipe.SyncPipe;

public abstract class SyncDataConsumer<I> implements SyncConsumer<I> {
    private SyncPipe<I> input;
    public SyncDataConsumer() {
    	this.input = new SyncDataPipe<I>(this);
    }
    
	public SyncPipe<I> getInputPipe() {
    	return this.input;
	}

	public abstract void receive(I input);
	 

}
