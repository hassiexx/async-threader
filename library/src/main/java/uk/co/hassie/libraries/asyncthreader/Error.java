package uk.co.hassie.libraries.asyncthreader;

/**
 * Error callback.
 */
@FunctionalInterface
public interface Error {

    /**
     * On error callback.
     *
     * @param error A throwable exception.
     */
    void onError(Throwable error);

}