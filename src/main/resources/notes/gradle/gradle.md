+ 官网下载安装
+ 配置环境变量，并配置GRADLE_USER_HOME设置本地仓库地址
+ 配置仓库源 在gradle目录的init.d下新建一个名为init.gradle的文件，内容如下

```
allprojects {
    repositories {
        mavenLocal()
        maven { name 'ali' ; url 'https://maven.aliyun.com/repository/public' }
        mavenCentral()
    }

    buildscript { 
        repositories {
            maven { name 'ali' ; url 'https://maven.aliyun.com/repository/public' }
        }
    }
}
```
