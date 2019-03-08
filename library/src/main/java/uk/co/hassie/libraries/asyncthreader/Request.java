package uk.co.hassie.libraries.asyncthreader;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Request<T> extends FutureTask<T> {

    private final Response<T> mResponse;
    private final Error mError;

    /**
     * Create a request.
     *
     * @param callable The callable to execute.
     * @param response The response callback.
     * @param error    The error callback.
     */
    public Request(Callable<T> callable, Response<T> response, Error error) {
        super(callable);
        mResponse = response;
        mError = error;
    }

    /**
     * Run the callback after the task is completed.
     */
    @Override
    protected void done() {
        try {
            mResponse.onResponse(this.get());
        } catch (InterruptedException e) {
            mError.onError(e);
        } catch (ExecutionException e) {
            mError.onError(e.getCause());
        }
    }
}