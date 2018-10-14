package org.plasma.flux.pipe;

import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.flux.FluxRuntimeException;
import org.plasma.flux.consumer.Consumer;

public class SyncDataPipe<T> implements SyncPipe<T> {
	private static Log log = LogFactory.getLog(SyncDataPipe.class);
	private Consumer<T> consumer;
	private Disposable disposable;
	private Cancellable cancellable;

	public SyncDataPipe(Consumer<T> consumer) {
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

	// @Override
	// public long requested() {
	// return 10; // pass in ?? can be infinite ?
	// }
	//
	// @Override
	// public boolean isCancelled() {
	// return false;
	// }

	// public ObservableEmitter<T> serialize() {
	public ObservableEmitter<T> serialize() {
		throw new FluxRuntimeException("not supported");
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

}
