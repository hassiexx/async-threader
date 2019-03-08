package uk.co.hassie.libraries.asyncthreader;

/**
 * Response callback.
 *
 * @param <T> The response type.
 */
@FunctionalInterface
public interface Response<T> {

    /**
     * On response callback.
     *
     * @param response The response.
     */
    void onResponse(T response);

}