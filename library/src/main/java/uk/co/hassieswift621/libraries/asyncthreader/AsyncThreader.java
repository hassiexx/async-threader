package uk.co.hassieswift621.libraries.asyncthreader;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
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
     * Creates the async threader instance with default options.
     */
    public AsyncThreader() {
        new Builder().build();
    }

    /**
     * Creates the async threader instance with custom options.
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
     * Submit a callable for execution and return a completable future.
     * @param callable The task to execute.
     * @param <T> The response data type.
     * @return A completable future.
     */
    public <T> CompletableFuture<T> execute(Callable<T> callable) {

        // Create completable future.
        CompletableFuture<T> future = new CompletableFuture<>();

        // Create request.
        Request<T> request = new Request<>(
                callable,
                future::complete,
                future::completeExceptionally
        );

        // Execute request.
        mExecutorService.submit(request);

        return future;
    }

    /**
     * Submit a fire and forget task for execution.
     * @param runnable A fire and forget task to execute.
     */
    public void execute(Runnable runnable) {
        // Execute request.
        mExecutorService.submit(runnable);
    }

    /**
     * Shutdown the async threader.
     */
    public void shutdown() {
        mExecutorService.shutdown();
    }

}