package uk.co.hassieswift621.libraries.asyncthreader.sample;

import uk.co.hassieswift621.libraries.asyncthreader.AsyncThreader;
import uk.co.hassieswift621.libraries.asyncthreader.Request;

/**
 * Created by Hassie on Thursday, 26 April, 2018 - 13:42.
 */
public class AsyncThreaderSample {

    public static void main(String[] args) {

        // Build the async threader.
        // Use the default number of thread pools = number of CPUs/CPU cores + 1.
        AsyncThreader asyncThreader = new AsyncThreader.Builder()
                .build();

        // Build request which will return an integer from the callable.
        Request<Integer> request = new Request<>(
                () -> {

                    // Run the loop 10 times and sleep for 200ms.
                    int i;
                    for (i = 0; i < 10; i++) {
                        Thread.sleep(200);
                    }

                    // Return the response, expected response is 10.
                    return i;

                },
                response -> {
                    // Do work with the response here.
                    System.out.println("Finished executing task, here is the response: " + response);
                },
                error -> {
                    // Handle error here.
                    System.out.println("Something terrible happened:");
                    error.printStackTrace();
                }
        );

        // Execute the request.
        asyncThreader.execute(request);

        // Continue to execute other code here while the above task is executing.
        // In this example, a message is outputted until the above request has finished executing.
        System.out.println("Execute other tasks whilst the above task is being executed.");
        while (!request.isDone()) {
            System.out.println("Still waiting for task to execute");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Shutdown the async threader.
        asyncThreader.shutdown();

    }

}
