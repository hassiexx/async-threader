package uk.co.hassieswift621.libraries.asyncthreader;

/**
 * Created by Hassie on Wednesday, 09 May, 2018 - 11:07.
 */
@FunctionalInterface
public interface IResponse<T> {

    void onResponse(T response);

}