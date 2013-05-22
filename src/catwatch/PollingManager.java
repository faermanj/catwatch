package catwatch;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PollingManager implements ServletContextListener{
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1) ;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		executor.shutdownNow();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		executor.scheduleAtFixedRate(new AvailableThreadsPoller(), 15, 15, TimeUnit.SECONDS);		
	}

}
