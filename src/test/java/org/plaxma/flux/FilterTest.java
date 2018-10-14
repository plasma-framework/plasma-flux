package org.plaxma.flux;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.flux.DataFlow;
import org.plasma.flux.consumer.SyncConsumer;
import org.plasma.flux.consumer.SyncDataConsumer;
import org.plasma.flux.filter.SyncDataFilter;
import org.plasma.flux.filter.SyncFilter;
import org.plasma.flux.producer.SyncProducer;
import org.testng.annotations.Test;

public class FilterTest {
	  private static Log log = LogFactory.getLog(FilterTest.class);

	
	@Test
	public void testSimpleFilter() {
		DataFlow dataFlow = new DataFlow();
		SyncProducer<Long> producer = new SyncProducer<Long>(dataFlow){
			@Override
			public void execute() {
				 this.send(new Long(1));
				 this.send(new Long(2));
				 this.send(new Long(3));				
			}			
		};	
		
		SyncFilter<Long, String> filter = new SyncDataFilter<Long, String>() {
			public void execute() {
			}

			@Override
			public void receive(Long input) {
				log.info(input);
				this.send(String.valueOf(input));
 			}
		};

		SyncConsumer<String> consumer = new SyncDataConsumer<String>() {
			@Override
			public void receive(String input) {
				log.info(input);
			}			
		};
		
		producer.connect(filter);
		filter.connect(consumer);
		dataFlow.execute();
	}
}
