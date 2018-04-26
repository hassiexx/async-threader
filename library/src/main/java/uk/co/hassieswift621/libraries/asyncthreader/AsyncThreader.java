package uk.co.hassieswift621.libraries.asyncthreader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Hassie on Wednesday, 25 April, 2018 - 12:01.
 */
public class AsyncThreader {

    private ExecutorService mExecutorService;

    public static class Builder {

        private int threadPoolSize = Runtime.getRuntime().availableProcessors() + 1;

        /**
         * Set a custom thread pool size.
         * By default, the thread pool size is set based on the available number of processors / processor cores.
         *
         * @param threadPoolSize The number of thread pools to create.
         * @return The builder instance.
         */
        public Builder setThreadPoolSize(int threadPoolSize) {
            if (threadPoolSize <= 0)
                return this;
            this.threadPoolSize = threadPoolSize;
            return this;
        }

        /**
         * Build the async threader.
         *
         * @return The async threader instance.
         */
        public AsyncThreader build() {
            return new AsyncThreader(threadPoolSize);
        }

    }

    /**
     * Creates the async threader instance.
     * @param threadPoolSize The thread pool size.
     */
    private AsyncThreader(int threadPoolSize) {
        mExecutorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    /**
     * Submit a request for execution.
     * @param request The request to execute.
     */
    public <T> void execute(Request<T> request) {
        mExecutorService.submit(request);
    }

    /**
     * Shutdown the async threader.
     */
    public void shutdown() {
        mExecutorService.shutdown();
    }

}