package org.plasma.flux.producer;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import org.plasma.flux.DataFlow;
import org.plasma.flux.FluxRuntimeException;
import org.plasma.flux.consumer.FlowableConsumer;
import org.plasma.flux.pipe.FlowablePipe;
import org.plasma.flux.pipe.Pipe;

public abstract class FlowableProducer<O> implements Producer<O> {
    private FlowablePipe<O> output;
    private ProducerFlowable<O> flowable;
    private DataFlow dataFlow;
    public FlowableProducer(DataFlow dataFlow) {
    	this.dataFlow = dataFlow;
    	this.dataFlow.addProducer(this);
    	this.flowable = new ProducerFlowable<O>();
    }
    
    public void connect(FlowableConsumer<O> consumer) {
    	this.output = consumer.getInputPipe();
    	try {
 			this.flowable.subscribe(this.output);
		} catch (Exception e) {
			throw new FluxRuntimeException(e);
		}
    }
	
	public Pipe<O> getOutputPipe() {		 
		return this.output;
	}
	
 	public void send(O output) {
		this.output.onNext(output);		
	}
 	
 	public void complete()
 	{
 		this.output.onComplete();
 	}
 	
 	public abstract void execute();
	
	class ProducerFlowable<O> implements FlowableOnSubscribe<O> {
	    private Flowable<O> flowable;
	    private FlowableEmitter<O> emitter;
	    ProducerFlowable() {
	    	this.flowable = Flowable.create(this, BackpressureStrategy.BUFFER);
	    	this.flowable.observeOn(Schedulers.newThread());
	    }

		public void subscribe(FlowableEmitter<O> emitter) throws Exception {
			this.emitter = emitter;		
		}
	}
}
