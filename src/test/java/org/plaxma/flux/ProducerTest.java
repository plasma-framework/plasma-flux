package org.plaxma.flux;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.flux.DataFlow;
import org.plasma.flux.consumer.SyncConsumer;
import org.plasma.flux.consumer.SyncDataConsumer;
import org.plasma.flux.producer.SyncProducer;
import org.testng.annotations.Test;

public class ProducerTest {
	  private static Log log = LogFactory.getLog(ProducerTest.class);
	//@Test
	public void testSimple() {
		DataFlow dataFlow = new DataFlow();
		SyncProducer<Long> producer = new SyncProducer<Long>(dataFlow){
			@Override
			public void execute() {
				 this.send(new Long(1));
				 this.send(new Long(2));
				 this.send(new Long(3));				 
				 this.complete(); 
			}

		};	
		
		SyncConsumer<Long> consumer = new SyncDataConsumer<Long>() {
			@Override
			public void receive(Long input) {
				log.info(input);
			}			
		};
		
		producer.connect(consumer);
		dataFlow.execute();
	}
	
	@Test
	public void testMultiProducerSingleConsumer() {
		DataFlow dataFlow = new DataFlow();
		SyncProducer<Long> producer1 = new SyncProducer<Long>(dataFlow){
			@Override
			public void execute() {
				 this.send(new Long(1));
				 this.send(new Long(2));
				 this.send(new Long(3));				
				 this.complete(); 
			}			
		};			
		SyncProducer<Long> producer2 = new SyncProducer<Long>(dataFlow){
			@Override
			public void execute() {
				 this.send(new Long(4));
				 this.send(new Long(5));
				 this.send(new Long(6));				
				 this.complete(); 
			}			
		};			

		SyncConsumer<Long> consumer = new SyncDataConsumer<Long>() {
			@Override
			public void receive(Long input) {
				log.info(input);
			}			
		};
		
		producer1.connect(consumer);
		producer2.connect(consumer);
		dataFlow.execute();
	}
	
}
