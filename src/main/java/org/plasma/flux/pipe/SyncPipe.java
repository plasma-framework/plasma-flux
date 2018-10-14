package org.plasma.flux.pipe;

import io.reactivex.ObservableEmitter;

public interface SyncPipe<T> extends ObservableEmitter<T>, Pipe<T> {

}
