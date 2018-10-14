package org.plasma.flux.filter;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import org.plasma.flux.FluxRuntimeException;
import org.plasma.flux.consumer.SyncConsumer;
import org.plasma.flux.pipe.Pipe;
import org.plasma.flux.pipe.SyncDataPipe;
import org.plasma.flux.pipe.SyncPipe;

public abstract class SyncDataFilter<I, O> implements SyncFilter<I, O> {
    private SyncPipe<O> output;
    private SyncPipe<I> input;
    private FilterObservable<O> observable;
   
    public SyncDataFilter()
    {
    	this.input = new SyncDataPipe<I>(this);
    	this.observable = new FilterObservable<O>();
    }
    
	public SyncPipe<I> getInputPipe() {
		return this.input;
	}

	public SyncPipe<O> getOutputPipe() {
		return this.output;
	}

	public void connect(SyncConsumer<O> consumer) {
		this.output = consumer.getInputPipe();
		try {
			this.observable.subscribe(this.output);
		} catch (Exception e) {
			throw new FluxRuntimeException(e);
		}
	}

	public abstract void receive(I input);

	public void send(O output) {
		this.output.onNext(output);		
	}
 
	class FilterObservable<T> implements ObservableOnSubscribe<T> {
	    private ObservableEmitter<T> emitter;
	    private Observable<T> observable;
	    FilterObservable() {
	    	this.observable = Observable.create(this);
	    }

		public void subscribe(ObservableEmitter<T> emitter) throws Exception {
			this.emitter = emitter;		
		}

	}
}
