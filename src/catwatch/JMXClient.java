package catwatch;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class JMXClient {
	public static int getAvailableThreads() {
		Integer availableThreads = null;
		try{
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName objectName = new ObjectName(
					"Catalina:name=\"http-bio-8080\",type=ThreadPool");
			Integer maxThreads = Integer.valueOf(mbs.getAttribute(objectName,
					"maxThreads").toString());
			Integer currentThreads = Integer.valueOf(mbs.getAttribute(objectName,
					"currentThreadsBusy").toString());
			availableThreads = maxThreads - currentThreads;
		}
		finally{
			return availableThreads;
		}
		
	}
}
