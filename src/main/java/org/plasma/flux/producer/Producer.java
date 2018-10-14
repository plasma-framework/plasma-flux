package org.plasma.flux.producer;

import org.plasma.flux.pipe.Pipe;

public interface Producer<O> {
    public Pipe<O> getOutputPipe();
    public void send(O output);
    public void execute();
}
