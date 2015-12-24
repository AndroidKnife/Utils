json
------

There are so many json libraries to parse json, like [google/gson](https://github.com/google/gson), [alibaba/fastjson](https://github.com/alibaba/fastjson), [FasterXML/jackson](https://github.com/FasterXML/jackson), etc..

so why this library can exist? For 3 reasons:
1. Light, just two files [[Utils/JSONResolver.java](https://github.com/AndroidKnife/Utils/blob/master/json/src/main/java/com/hwangjr/utils/json/JSONResolver.java), [Utils/JSONNode.java](https://github.com/AndroidKnife/Utils/blob/master/json/src/main/java/com/hwangjr/utils/json/JSONNode.java)] to parse json, **suitable for `sdk` etc..**
2. Easy to use, use annotation to parse json
3. Less is more, focus on parse json, just it.

This library provide:
1. Light json parse library, just two file: [[Utils/JSONResolver.java](https://github.com/AndroidKnife/Utils/blob/master/json/src/main/java/com/hwangjr/utils/json/JSONResolver.java), [Utils/JSONNode.java](https://github.com/AndroidKnife/Utils/blob/master/json/src/main/java/com/hwangjr/utils/json/JSONNode.java)]
2. [Utils/JSONGetter.java](https://github.com/AndroidKnife/Utils/blob/master/json/src/main/java/com/hwangjr/utils/json/JSONGetter.java) is an utility to get value from json object
3. [Utils/GSONWrapper.java at master · AndroidKnife/Utils](https://github.com/AndroidKnife/Utils/blob/master/json/src/main/java/com/hwangjr/utils/json/GSONWrapper.java) is an wrapper of gson, add JSONNode support

That's all! Enjoy it!

Usage
-----

This is just an utilities, so just call the function.

More
------
1. [google/gson](https://github.com/google/gson)
2. [alibaba/fastjson](https://github.com/alibaba/fastjson)
3. [FasterXML/jackson](https://github.com/FasterXML/jackson)