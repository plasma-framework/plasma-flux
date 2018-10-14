package org.plasma.flux.consumer;


public interface Consumer<I> {
    public void receive(I input);
}
