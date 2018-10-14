package org.plasma.flux.consumer;

import org.plasma.flux.pipe.SyncPipe;

public interface SyncConsumer<I> extends Consumer<I> {
    public SyncPipe<I> getInputPipe();
}
