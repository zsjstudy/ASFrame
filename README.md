# ASFrameAfDemo
如何使用它？
先在 build.gradle(Project:XXXX) 的 repositories 添加:
    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
然后在 build.gradle(Module:app) 的 dependencies 添加:
    dependencies {
            ...
            implementation 'com.github.lijiazhen1201:AfDemo:VERSION_CODE'
    }
