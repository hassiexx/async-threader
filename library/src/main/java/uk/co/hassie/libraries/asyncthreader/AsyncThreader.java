/*
 * Copyright ©2018-2019 Hassie.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.hassie.libraries.asyncthreader;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncThreader {

    private ExecutorService executorService;

    public static class Builder {

        private int threadPoolSize;

        /**
         * Set a custom thread pool size.
         * By default, the thread pool size is set based on the available number of processors / processor cores.
         *
         * @param threadPoolSize The number of thread pools to create.
         * @return The builder instance.
         */
        public Builder setThreadPoolSize(int threadPoolSize) {
            this.threadPoolSize = threadPoolSize;
            return this;
        }

        /**
         * Build the async threader.
         *
         * @return The async threader instance.
         */
        public AsyncThreader build() {
            if (threadPoolSize > 0) {
                return new AsyncThreader(threadPoolSize);
            }

            return new AsyncThreader();
        }

    }

    /**
     * Creates the async threader instance with default options.
     */
    public AsyncThreader() {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    }

    /**
     * Creates the async threader instance with custom options.
     *
     * @param threadPoolSize The thread pool size.
     */
    private AsyncThreader(int threadPoolSize) {
        executorService = Executors.newFixedThreadPool(threadPoolSize);
    }


    /**
     * Submit a request for execution.
     *
     * @param request The request to execute.
     */
    public <T> void execute(Request<T> request) {
        executorService.submit(request);
    }

    /**
     * Submit a callable for execution and return a completable future.
     *
     * @param callable The task to execute.
     * @param <T>      The response data type.
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
        executorService.submit(request);

        return future;
    }

    /**
     * Submit a fire and forget task for execution.
     *
     * @param runnable A fire and forget task to execute.
     */
    public void execute(Runnable runnable) {
        // Execute request.
        executorService.submit(runnable);
    }

    /**
     * Shutdown the async threader's executor service.
     */
    public void shutdown() {
        executorService.shutdown();
    }

}