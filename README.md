Async Threader [![CircleCI](https://circleci.com/gh/hassieswift621/async-threader.svg?style=svg)](https://circleci.com/gh/hassieswift621/async-threader) [ ![Download](https://api.bintray.com/packages/hassieswift621/maven/async-threader/images/download.svg) ](https://bintray.com/hassieswift621/maven/async-threader/_latestVersion) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/14333c1f3d0b44ca9f2efcdb375f498a)](https://www.codacy.com/app/hassieswift621/async-threader?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=hassieswift621/async-threader&amp;utm_campaign=Badge_Grade)
==============

Async Threader is a simple to use type-safe Java asynchronous threader which creates a thread pool and uses callable and future tasks to execute tasks asynchronously.

The async threader makes use of callables and futures to do this.
The async threader creates a fixed thread pool size. By default it is the number of CPUs / CPU cores + 1, but can be customised.

The request uses a callable which does execution asynchronously and a callback which does post execution on the calling thread, perfect for many tasks such as API requests.

After finishing with the async threader, you should call the shutdown method to free up any resources used by the thread pool.

See a sample in the sample module.

Dependencies
------------
The library is available on JCenter. The latest version is 1.0.0

**Gradle setup**
```gradle
implementation 'uk.co.hassieswift621.libraries:async-threader:1.0.0'
```

**Maven setup**
```maven
<dependency>
  <groupId>uk.co.hassieswift621.libraries</groupId>
  <artifactId>async-threader</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

Tutorial
--------
```java
// Using the default thread pool size.
AsyncThreader asyncThreader = new AsyncThreader.Builder()
    .build();

// Using a custom thread pool size.
AsyncThreader asyncThreader = new AsyncThreader.Builder()
    .setThreadPoolSize(2)
    .build();

// Build request which will return a response from the callable.
Request<JSONObject> request = new Request<>(
    // The callable is executed asynchronously.
    new Callable<JSONObject>() {
        @Override
        public JSONObject call() throws Exception {
                                        
            // Run some code to download a json.
                   
            // Return the response.                     
            return jsonObject;
        }
    },
    // The callback is executed on the calling thread.
    new Callback<JSONObject>() {
        @Override
        public void onSuccess(JSONObject response) {
            // Do work with the response here.
            }
            @Override
            public void onFailure(Throwable throwable) {
            // Error occurred. Handle error here.
            }
        }
    }
);

// Eexecute the request.
asyncThreader.execute(request);

// Do other tasks here while the above request is executed.
// Or add more requests.

// Once done with the async threader, shut it down to release resources.
asyncThreader.shutdown();

```


License
-------
Copyright ©2018 HassieSwift621.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
