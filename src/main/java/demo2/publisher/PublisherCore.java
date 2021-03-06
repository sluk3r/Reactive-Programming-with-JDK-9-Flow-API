package demo2.publisher;

import java.util.concurrent.*;

public class PublisherCore  {

    private final ScheduledFuture<?> periodicTask;
    private final ScheduledExecutorService scheduler;

    public static PublisherCore create(int corePoolSize, long period, TimeUnit unit, Runnable runnable) {
        return new PublisherCore(corePoolSize,  period, unit, runnable);
    }

    PublisherCore( int corePoolSize, long period, TimeUnit unit, Runnable runnable) {
        scheduler = new ScheduledThreadPoolExecutor(corePoolSize);
        periodicTask = scheduler.scheduleAtFixedRate(runnable, 0, period, unit);
    }

    public void close() {

        Boolean shutdown = false;
        if(periodicTask.isDone())
            shutdown = true;

        periodicTask.cancel(shutdown);
        scheduler.shutdown();

    }

}
