package uk.co.hassieswift621.libraries.asyncthreader;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Hassie on Thursday, 26 April, 2018 - 13:03.
 */
public class Request<T> extends FutureTask<T> {

    private final IResponse<T> mResponse;
    private final IError mError;

    /**
     * Create a request.
     * @param callable The callable to execute.
     * @param response The response callback.
     * @param error The error callback.
     */
    public Request(Callable<T> callable, IResponse<T> response, IError error) {
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