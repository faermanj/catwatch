
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class AvailableThreads {
	public static void main(String[] args) throws Exception {
		String host = "localhost";
		if(args.length > 0) host = args[0];
		String port = "9010";
		if(args.length > 1) port = args[1];
		JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://" +
				host +
				":" +
				port +
				"/jmxrmi");
		JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
		ObjectName objectName = new ObjectName("Catalina:name=\"http-bio-8080\",type=ThreadPool");
		Integer maxThreads = 
				Integer.valueOf(mbsc.getAttribute(objectName,"maxThreads").toString());
		Integer currentThreads = 
				Integer.valueOf(mbsc.getAttribute(objectName,"currentThreadsBusy").toString());		
		System.out.println(maxThreads - currentThreads);
		jmxc.close();
	}
}
