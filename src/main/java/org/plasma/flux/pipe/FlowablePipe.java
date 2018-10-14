package org.plasma.flux.pipe;

import io.reactivex.FlowableEmitter;

public interface FlowablePipe<T> extends FlowableEmitter<T>, Pipe<T> {

}
