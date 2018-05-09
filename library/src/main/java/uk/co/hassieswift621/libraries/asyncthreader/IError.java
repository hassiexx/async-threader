package uk.co.hassieswift621.libraries.asyncthreader;

/**
 * Created by Hassie on Wednesday, 09 May, 2018 - 11:14.
 */
@FunctionalInterface
public interface IError {

    void onError(Throwable error);

}
