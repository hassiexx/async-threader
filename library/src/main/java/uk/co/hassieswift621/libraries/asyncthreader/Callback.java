package uk.co.hassieswift621.libraries.asyncthreader;

/**
 * Created by Hassie on Thursday, 26 April, 2018 - 13:04.
 */
public interface Callback<T> {

    /**
     * The callback to run if the task completed successfully.
     * @param response The response from the task.
     */
    void onSuccess(T response);

    /**
     * The callback to run if the task failed / runs into an error.
     * @param throwable The throwable exception cause.
     */
    void onFailure(Throwable throwable);

}