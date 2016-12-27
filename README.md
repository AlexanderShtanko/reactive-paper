# Reactive Paper
RxJava wrapper for [Paper](https://github.com/pilgr/Paper)

Paper is a fast NoSQL data storage for Android that lets you save/restore Java/Kotlin objects using efficient Kryo serialization. Object structure changes handled automatically.


## How to use:

### Add dependency

Add to repositories:
```groovy

repositories {
    maven {
        url  "http://jcenter.bintray.com"
    }
}
```


Add to dependencies:
```groovy
compile 'com.alexandershtanko.reactivepaper:reactive-paper:1.0.0'
```


### Get instance

```
RxPaper.getInstance()
```

###  Initialize Reactive Paper
```
rxPaper.init(context)
```

###  Destroy
```
rxPaper.destroy(context)
```

###  Read

How to get Observable for Item with bookName and key:
```
rxPaper.read(String bookName, String key)
```


How to get Observable for Map of items with bookName:
```
rxPaper.read(String bookName, boolean cached)
```

How to get value:
```
rxPaper.readOnce(String bookName, boolean cached)
rxPaper.readOnce(String bookName, String key, boolean cached)
```

###  Write

How to write item:
```
rxPaper.write(String bookName, String key, T object, boolean cached)
```

How to write map of items:
```
rxPaper.write(String bookName, Map<String, T> objectMap, boolean cached)
```

###  Delete

```
rxPaper.delete(String bookName, String key)
rxPaper.delete(String bookName)
```

###  Lazy Loading

How to get lazy objects with bookName:
```
rxPaper.readLazy(String bookName)
```

How to async load object with result in Main Thread:
```
lazyObject.getObjectAsync(Action1<RxPaper.PaperObject<T>> action)
```

### License
    Copyright 2016 Alexander Shtanko

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.