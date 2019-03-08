Async Threader [![CircleCI](https://circleci.com/gh/hassieswift621/async-threader.svg?style=svg)](https://circleci.com/gh/hassieswift621/async-threader) [ ![Download](https://api.bintray.com/packages/hassieswift621/maven/async-threader/images/download.svg) ](https://bintray.com/hassieswift621/maven/async-threader/_latestVersion) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/14333c1f3d0b44ca9f2efcdb375f498a)](https://www.codacy.com/app/hassieswift621/async-threader?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=hassieswift621/async-threader&amp;utm_campaign=Badge_Grade)
==============

Async Threader is a simple to use type-safe Java library which automatically executes callbacks after a callable or future completes or fails. No need for manual checks any longer.

The async threader creates a fixed thread pool size. By default it is the number of CPUs / CPU cores + 1, but can be customised.

The request uses a callable which does execution asynchronously and a callback which does post execution on the calling thread, perfect for many tasks.

After finishing with the async threader, you should call the shutdown method to free up resources used by the thread pool, which will also keep your application alive.

See a sample in the sample module.

Dependencies
------------
The library is available on JCenter. The latest version is 2.1.0.

**Gradle setup**
```gradle
implementation 'uk.co.hassie.libraries:async-threader:<LATEST-VERSION>'
```

**Maven setup**
```maven
<dependency>
  <groupId>uk.co.hassie.libraries</groupId>
  <artifactId>async-threader</artifactId>
  <version><LATEST-VERSION></version>
  <type>pom</type>
</dependency>
```

Tutorial
--------
**Creating an async threader instance**
```java
// Using the default thread pool size.
AsyncThreader asyncThreader = new AsyncThreader();

// Using a custom thread pool size.
AsyncThreader asyncThreader = new AsyncThreader.Builder()
    .setThreadPoolSize(2)
    .build();
```

**Execute a task asynchronously and work with the response.**
```java
// Build request which will return a response from the callable.
// In this example, the download of a JSON is implied.
Request<JSONObject> request = new Request<>(
    // The callable task is executed asynchronously.
    () -> {
        
        // Run some code to download a JSON.
        
        // Return the JSON object.
        return jsonObject;
    },
    // The callbacks are executed on the calling thread.
    response -> {
        // Do work with the response here.
        System.out.println(response.getJSONObject("some object").toString());
    },
    error -> {
        // Error occurred. Handle if required.
        // The error will be a Throwable type.
        error.printStackTrace();
});

// Eexecute the request.
asyncThreader.execute(request);

// Do other tasks here while the above request is executed.
// Or add more requests.

// Once done with the async threader, shut it down to release resources.
asyncThreader.shutdown();
```

**Submit a task for execution and return a CompletableFuture**
```java
CompletableFuture<JSONObject> future = asyncThreader.execute(
    // The callable task to execute asynchronously.
    () -> {
        // Do stuff.
        return jsonObject;
});

// Once the callable has finished executing, the completable future
// will automatically have its complete() or completeExceptionally()
// method called. 

// Do stuff with the completable future when required.
```

**Executing a fire and forget task / no return value**
```java
asyncThreader.execute(
    () -> {
        // Some code to execute a fire and forget task
        // or a task which has no return value.
    }    
);
```

License
-------
Copyright ©2018-2019 Hassie.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
