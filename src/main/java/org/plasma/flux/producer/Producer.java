package org.plasma.flux.producer;

import org.plasma.flux.pipe.Pipe;

/**
 * A producer is the source for data within a data flow and 
 * in general implements the {@link org.plasma.flux.producer.Producer#execute execute} method
 * by sending data through its output {@link org.plasma.flux.pipe.Pipe pipe}. 
 * 
 * @author Scott Cinnamond
 * @param <O> the type of output produced
 */
public interface Producer<O> {
	/**
	 * The basic behavior of the producer which in general is generating data and 
	 * sending the data through the 
	 * output {@link org.plasma.flux.pipe.Pipe pipe} using the
	 * {@link org.plasma.flux.producer.Producer#send send} method and then 
	 * calling the {@link org.plasma.flux.producer.Producer#complete complete} method
	 * on completion. 
	 */
    public void execute();
   
    /**
     * Passes the given data into and through the output {@link org.plasma.flux.pipe.Pipe pipe}. 
     * @param output the data to send
     */
    public void send(O output);
    
    /**
     * Called when the sending activity has completed.   
     */
    public void complete();
    
    /**
     * Returns the output pipe.  
     * @return the output pipe.
     */
    public Pipe<O> getOutputPipe();
}
