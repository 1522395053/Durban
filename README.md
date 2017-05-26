`Durban` is an MD-style picture cropping tool that combines the image selection tool with [Album](https://github.com/yanzhenjie/Album) Use is the best.  

It comes from the [uCrop](https://github.com/Yalantis/uCrop), but I modified most of the code, let it use more friendly. 

Recommended image selection library: [https://github.com/yanzhenjie/Album](https://github.com/yanzhenjie/Album)  

[中文文档](./README-CN.md)  

# Screenshot
<image src="./image/1.gif" width="280px"/>  

# Dependencies
* Gradle：
```groovy
compile 'com.yanzhenjie:durban:1.0.0'
```

* Maven:
```xml
<dependency>
  <groupId>com.yanzhenjie</groupId>
  <artifactId>durban</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

# Usage
Support `Activity`, `android.app.Fragment`, `android.support.v4.app.Fragment`:  
```java
Durban.with(Activity);
Durban.with(android.app.Fragment);
Durban.with(android.support.v4.app.Fragment);
```

How to call clipping:  
```java
Durban.with(this)
    // Che title of the UI.
    .title("Crop")
    .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
    .navigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryBlack))
    // Image path list/array.
    .inputImagePaths(imagePathList)
    // Image output directory.
    .outputDirectory(cropDirectory)
    // Image size limit.
    .maxWidthHeight(500, 500)
    // Aspect ratio.
    .aspectRatio(1, 1)
    // Output format: JPEG, PNG.
    .compressFormat(Durban.COMPRESS_JPEG)
    // Compress quality, see Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
    .compressQuality(90)
    // Gesture: ROTATE, SCALE, ALL, NONE.
    .gesture(Durban.GESTURE_ALL)
    .requestCode(200)
    .start();
```
**Note:** `inputImagePaths()` method can be a picture path list, can also be a picture path array, such as:  
```java
// Such as: 
List<String> imagePathList = ...
Durban.with(this)
    ...
    .inputImagePaths(imagePathList)
    ...

// OR: 
String[] imagePathArray = ...
Durban.with(this)
    ...
    .inputImagePaths(imagePathArray)
    ...

// OR: 
Durban.with(this)
    ...
    .inputImagePaths(path1, path2, path3...)
    ...
```

And then override the `onActivityResult()` method to accept the cropping results:  
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
        case 200: {
            // Analyze the list of paths after cropping.
            if (resultCode != RESULT_OK) {
                ArrayList<String> mImageList = Durban.parseResult(data);
            } else {
                // TODO other...
            }
            break;
        }
    }
}
```

# Proguard-rules
Durban can be completely confusing, if there is a problem, add the rule to the proguard-rules:
```txt
-dontwarn com.yanzhenjie.curban.**
-keep class com.yanzhenjie.curban.**{*;}

-dontwarn com.yanzhenjie.loading.**
-keep class com.yanzhenjie.loading.**{*;}
```

# Thanks
1. [uCrop](https://github.com/Yalantis/uCrop)  
2. [Album](https://github.com/yanzhenjie/Album)  

# License
```text
Copyright 2017 Yan Zhenjie

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```