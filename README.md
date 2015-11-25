Utils
------

Android utils, like get phone info, log, get screen info etc...

This is a utils library with a small, extensible API which providers utility on top of Android's application develop.

I copy this utils into all the little apps I make, I'm tired of doing it, I'm lazzy. Now it's a library.

Now this utils have :
* `timber` for log, you also won't miss [Pidcat](http://github.com/JakeWharton/pidcat/).

Usage
-----

**Gradle Build**

Add libraries to your dependencies:
``` gradle
dependencies {
    ...
    compile 'com.hwangjr.utils:timber:1.0.0'
}
```

**Timber**

Two easy steps:

 1. Install any `Tree` instances you want in the `onCreate` of your application class.
 2. Call `Timber`'s static methods everywhere throughout your app.


License
-------

    Copyright 2015 HwangJR

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

