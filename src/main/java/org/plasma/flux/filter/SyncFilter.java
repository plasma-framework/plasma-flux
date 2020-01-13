package org.plasma.flux.filter;

import org.plasma.flux.consumer.SyncConsumer;
import org.plasma.flux.pipe.SyncPipe;

public interface SyncFilter<I, O> extends SyncConsumer<I>{
    public SyncPipe<I> getInputPipe();
    public SyncPipe<O> getOutputPipe();
    public void connect(SyncConsumer<O> consumer);
	public abstract void receive(I input);
}
