package catwatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;

public class AvailableThreadsPoller implements Runnable {
	
	
	@Override
	public void run() {
		Integer availableThreads = JMXClient.getAvailableThreads();		
		if(availableThreads == null) return;
		send(availableThreads);		
	}

	//TODO: Enrich datum
	//TODO: Aggregate before send
	//TODO: Externalize credentials
	public static void send(Integer availableThreads) {
		AWSCredentials credentials = Credentials.faermanj;
		AmazonCloudWatchClient cwClient = new AmazonCloudWatchClient(credentials);
		PutMetricDataRequest request = new PutMetricDataRequest();
		
		Collection<MetricDatum> metricData = new ArrayList<MetricDatum>();
		MetricDatum datum = new MetricDatum();
		datum.setMetricName("AvailableThreads");
		datum.setTimestamp(new Date());
		datum.setValue(availableThreads.doubleValue());
		metricData.add(datum);		
		request.setMetricData(metricData );
		String namespace = "faermanj";
		request.setNamespace(namespace);

		cwClient.putMetricData(request);
	}
	

}
