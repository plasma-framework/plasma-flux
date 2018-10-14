package org.plaxma.flux;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.flux.DataFlow;
import org.plasma.flux.consumer.FlowableConsumer;
import org.plasma.flux.consumer.FlowableDataConsumer;
import org.plasma.flux.consumer.SyncConsumer;
import org.plasma.flux.consumer.SyncDataConsumer;
import org.plasma.flux.producer.FlowableProducer;
import org.plasma.flux.producer.SyncProducer;
import org.testng.annotations.Test;

public class FlowableProducerTest {
	  private static Log log = LogFactory.getLog(FlowableProducerTest.class);
	
	@Test
	public void testSimple() {
		DataFlow dataFlow = new DataFlow();
		FlowableProducer<Long> producer = new FlowableProducer<Long>(dataFlow){
			@Override
			public void execute() {
				 this.send(new Long(1));
				 this.send(new Long(2));
				 this.send(new Long(3));				 
				 this.complete(); 
			}

		};	
		
		FlowableConsumer<Long> consumer = new FlowableDataConsumer<Long>() {
			@Override
			public void receive(Long input) {
				log.info(input);
			}			
		};
		
		producer.connect(consumer);
		dataFlow.execute();
	}
	
}
