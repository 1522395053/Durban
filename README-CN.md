`Durban`是一个MD风格的图片裁剪工具，结合图片选择库[Album](https://github.com/yanzhenjie/Album)是最好的搭配。

它来自[uCrop](https://github.com/Yalantis/uCrop)，我修改了大部分代码，让它使用起来更加友好，**重点是一次可以裁剪多张图片**，同时更适合和[Album](https://github.com/yanzhenjie/Album)结合使用。

QQ技术交流群：[547839514](https://jq.qq.com/?_wv=1027&k=49Xx0JX)  
图片选择库推荐：[https://github.com/yanzhenjie/Album](https://github.com/yanzhenjie/Album)  

**[English Document](./README.md)**  

# 截图
<image src="./image/1.gif" width="280px"/>  <image src="./image/2.gif" width="280px"/>  

# 依赖
* Gradle：
```groovy
compile 'com.yanzhenjie:durban:1.0.1'
```

* Maven:
```xml
<dependency>
  <groupId>com.yanzhenjie</groupId>
  <artifactId>durban</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

# 使用介绍
支持在`Activity`、`android.app.Fragment`、`android.support.v4.app.Fragment`中调用：  
```java
Durban.with(Activity);
Durban.with(android.app.Fragment);
Durban.with(android.support.v4.app.Fragment);
```

调用示例：  
```java
Durban.with(this)
    // 裁剪界面的标题。
    .title("Crop")
    .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
    .navigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryBlack))
    // 图片路径list或者数组。
    .inputImagePaths(imagePathList)
    // 图片输出文件夹路径。
    .outputDirectory(cropDirectory)
    // 裁剪图片输出的最大宽高。
    .maxWidthHeight(500, 500)
    // 裁剪时的宽高比。
    .aspectRatio(1, 1)
    // 图片压缩格式：JPEG、PNG。
    .compressFormat(Durban.COMPRESS_JPEG)
    // 图片压缩质量，请参考：Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
    .compressQuality(90)
    // 裁剪时的手势支持：ROTATE, SCALE, ALL, NONE.
    .gesture(Durban.GESTURE_ALL)
    .controller(
        Controller.newBuilder()
        .enable(false) // 是否开启控制面板。
        .rotation(true) // 是否有旋转按钮。
        .rotationTitle(true) // 旋转控制按钮上面的标题。
        .scale(true) // 是否有缩放按钮。
        .scaleTitle(true) // 缩放控制按钮上面的标题。
        .build()) // 创建控制面板配置。
    .requestCode(200)
    .start();
```
**注意**：`inputImagePaths()`方法的参数可以是路径list，也可以是路径数组：
```java
// 例如: 
List<String> imagePathList = ...
Durban.with(this)
    ...
    .inputImagePaths(imagePathList)
    ...

// 或者: 
String[] imagePathArray = ...
Durban.with(this)
    ...
    .inputImagePaths(imagePathArray)
    ...

// 或者: 
Durban.with(this)
    ...
    .inputImagePaths(path1, path2, path3...)
    ...
```

然后重写`onActivityResult()`方法接受剪裁结果：  
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
        case 200: {
            // 解析剪切结果：
            if (resultCode != RESULT_OK) {
                ArrayList<String> mImageList = Durban.parseResult(data);
            } else {
                // TODO 其它处理...
            }
            break;
        }
    }
}
```

## 意外的语言配置
Durban支持英文、简体中文和繁体中文，如果需要支持其它语言，你可以在`values-xxx`（xxx的意思是语言标记，比如：values-zh、values-zh-rHK）复写Durban的`string.xml`的资源，并且在`Application#onCreate()`中配置语言：  
```java
Durban.initialize(
    DurbanConfig.newBuilder(this)
    .setLocale(...)
    .build()
);
```

# 混淆规则
Durban可以被完全混淆，如果出现了问题请添加如下混淆规则：  
```txt
-dontwarn com.yanzhenjie.curban.**
-keep class com.yanzhenjie.curban.**{*;}

-dontwarn com.yanzhenjie.loading.**
-keep class com.yanzhenjie.loading.**{*;}
```

# 感谢
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