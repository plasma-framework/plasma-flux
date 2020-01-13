package org.plasma.flux.pipe;

import io.reactivex.FlowableEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.flux.consumer.Consumer;

public class FlowableDataPipe<T> implements FlowablePipe<T> {
	private static Log log = LogFactory.getLog(FlowableDataPipe.class);
	private Consumer<T> consumer;
	private Disposable disposable;
	private Cancellable cancellable;

	public FlowableDataPipe(Consumer<T> consumer) {
		this.consumer = consumer;
	}

	public void setDisposable(Disposable d) {
		this.disposable = d;
	}

	public void setCancellable(Cancellable c) {
		this.cancellable = c;
	}

	public boolean isDisposed() {
		return this.disposable != null && this.disposable.isDisposed();
	}

	public void onNext(T value) {
		if (log.isDebugEnabled())
			log.debug(value);
		this.consumer.receive(value);
	}

	public void onError(Throwable error) {
		if (log.isDebugEnabled())
			log.debug(error.getMessage(), error);
	}

	public void onComplete() {
		if (log.isDebugEnabled())
			log.debug("onComplete()");
	}

	@Override
	public long requested() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FlowableEmitter<T> serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
