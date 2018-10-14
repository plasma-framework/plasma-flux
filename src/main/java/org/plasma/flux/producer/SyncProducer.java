package org.plasma.flux.producer;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import org.plasma.flux.DataFlow;
import org.plasma.flux.FluxRuntimeException;
import org.plasma.flux.consumer.SyncConsumer;
import org.plasma.flux.pipe.Pipe;
import org.plasma.flux.pipe.SyncPipe;

public abstract class SyncProducer<O> implements Producer<O> {
    private SyncPipe<O> output;
    private ProducerObservable<O> observable;
    private DataFlow dataFlow;
    public SyncProducer(DataFlow dataFlow) {
    	this.dataFlow = dataFlow;
    	this.dataFlow.addProducer(this);
    	this.observable = new ProducerObservable<O>();
    }
    
    public void connect(SyncConsumer<O> consumer) {
    	 
    	this.output = consumer.getInputPipe();
    	try {
			this.observable.subscribe(this.output);
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
	
	class ProducerObservable<T> implements ObservableOnSubscribe<T> {
	    private ObservableEmitter<T> emitter;
	    private Observable<T> observable;
	    ProducerObservable() {
	    	this.observable = Observable.create(this);
	    }

		public void subscribe(ObservableEmitter<T> emitter) throws Exception {
			this.emitter = emitter;		
		}
	}
	
}
