package service;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple CDI service which is able to say hello to someone after a timeout in seconds
 * 
 * 
 */
public class HelloService {

	public String createHelloMessage(String name, final int timeout) {
		final CountDownLatch done = new CountDownLatch(timeout);
		final ScheduledExecutorService schedule = Executors
				.newSingleThreadScheduledExecutor();
		final AtomicInteger i = new AtomicInteger(timeout);
		schedule.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println(i.getAndDecrement());
				done.countDown();

			}
		}, 0, 1, SECONDS);

		try {
			done.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			schedule.shutdown();
		}
		return "Hello " + name + "!";
	}

}
