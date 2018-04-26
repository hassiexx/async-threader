package uk.co.hassieswift621.libraries.asyncthreader;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Hassie on Thursday, 26 April, 2018 - 13:03.
 */
public class Request<T> extends FutureTask<T> {

    private final Callback<T> mCallback;

    /**
     * Create the request.
     * @param callable The task to execute.
     * @param callback The callback to run after the task is executed.
     */
    public Request(Callable<T> callable, Callback<T> callback) {
        super(callable);
        mCallback = callback;
    }

    /**
     * Run the callback after the task is completed.
     */
    @Override
    protected void done() {

        try {
            mCallback.onSuccess(this.get());
        } catch (InterruptedException e) {
            mCallback.onFailure(e);
        } catch (ExecutionException e) {
            mCallback.onFailure(e.getCause());
        }
    }
}