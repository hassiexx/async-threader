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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Request<T> extends FutureTask<T> {

    private final Response<T> response;
    private final Error error;

    /**
     * Create a request.
     *
     * @param callable The callable to execute.
     * @param response The response callback.
     * @param error    The error callback.
     */
    public Request(Callable<T> callable, Response<T> response, Error error) {
        super(callable);
        this.response = response;
        this.error = error;
    }

    /**
     * Executes the callbacks after task has completed.
     */
    @Override
    protected void done() {
        try {
            this.response.onResponse(this.get());
        } catch (InterruptedException e) {
            this.error.onError(e);
        } catch (ExecutionException e) {
            this.error.onError(e.getCause());
        }
    }

}