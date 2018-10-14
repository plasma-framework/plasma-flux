package org.plasma.flux.consumer;

import org.plasma.flux.pipe.FlowablePipe;

public interface FlowableConsumer<I> extends Consumer<I> {
    public FlowablePipe<I> getInputPipe();
}
