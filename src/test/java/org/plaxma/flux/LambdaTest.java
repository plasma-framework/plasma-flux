package org.plaxma.flux;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

public class LambdaTest {
	  private static Log log = LogFactory.getLog(LambdaTest.class);
	  private Callable<Integer> thatReturnsNumberOne() {
		    return () -> {
		        System.out.println("Observable thread: " + Thread.currentThread().getName());
		        return 1;
		    };
		}

		private Function<Integer, String> numberToString() {
		    return number -> {
		        System.out.println("Operator thread: " + Thread.currentThread().getName());
		        return String.valueOf(number);
		    };
		}

		private Action printResult() {
		    return () -> {
		        System.out.println("Subscriber thread: " + Thread.currentThread().getName());
		        System.out.println("Result: " + "");
		    };
		}

	
	@Test
	public void testSingleThreaded() {
		 int threadCount = Runtime.getRuntime().availableProcessors();
		 ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(threadCount);
		 Scheduler scheduler = Schedulers.from(threadPoolExecutor);

		 Observable.fromCallable(thatReturnsNumberOne()) // return Observable<Integer>
            .subscribeOn(scheduler) // return Observable<Integer>
            .map(numberToString()) // return Observable<String> 
            .subscribe();
	}
	 
	@Test
	public void testMultiThreaded()
	 {
		 Flowable.create(emitter -> {
			  emitter.onNext(1);
			  emitter.onNext(2);
			  emitter.onNext(3);
			  emitter.onComplete();
			}, BackpressureStrategy.BUFFER).subscribe(
			    System.out::println,
			    e -> e.printStackTrace(), 
			    () -> System.out.println("done"));		
		 
	 }
	
	@Test
	public void testMultiThreaded2()
	{
		 Flowable<Object> flowable = Flowable.create(emitter -> {
			  emitter.onNext(1);
			  emitter.onNext(2);
			  emitter.onNext(3);
			  emitter.onComplete();
			}, BackpressureStrategy.BUFFER);
		 
		 
		 
		 int threadCount = Runtime.getRuntime().availableProcessors();
		 ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(threadCount);
		 Scheduler scheduler = Schedulers.from(threadPoolExecutor);
		 
		 flowable.subscribeOn(scheduler)
		    .subscribe(new io.reactivex.functions.Consumer<Object>(){
			@Override
			public void accept(Object t) throws Exception {
				System.out.println("Subscriber thread: " + Thread.currentThread().getName() + ": " + t);
			}
		 }
		 );
		 
//		 flowable.subscribe(System.out::println,
//			    e -> e.printStackTrace(), 
//			    () -> System.out.println("done"));
	}
		
	{//		 
//		  
//		 Flowable.create(new FlowableOnSubscribe<String>(){
//
//			@Override
//			public void subscribe(FlowableEmitter<String> e) throws Exception {
//				// TODO Auto-generated method stub
//				
//			}}, BackpressureStrategy.BUFFER);
//		 
	 }
}
