# Setting up Library

This library is published to Maven Central.
To download this library, just add it to your dependencies.

```groovy
dependencies {
    implementation("com.cheonjaeung.compose.grid:grid:<version>")
}
```

**After the 2.0.0 version, the group id and package name is changed from**
**`io.woong.compose.grid` to `com.cheonjaeung.compose.grid`.**

> Checkout the latest version in [GitHub Releases](https://github.com/cheonjaeung/gridlayout-compose/releases).

## Platform Supports

GridLayout for Compose is a multiplatform library.
The following table shows which platform is supported.

| Platform | Status                     |
|----------|----------------------------|
| Android  | ✅ Available (All Versions) |
| iOS      | ✅ Available (Since 2.0.0)  |
| Desktop  | ✅ Available (All Versions) |

## Compose Version Dependency

This library depends on the Compose library.
The following table shows Compose versions which each library version depends on.

| GridLayout Version | Compose Version (Multiplatform) | Compose Version (Android)        |
|--------------------|---------------------------------|----------------------------------|
| 2.7.0 ~ current    | Compose Multiplatform 1.10.0    | Depends on Compose Multiplatform |
| 2.5.0 ~ 2.6.0      | Compose Multiplatform 1.9.0     | Depends on Compose Multiplatform |
| 2.3.0 ~ 2.4.0      | Compose Multiplatform 1.8.0     | Jetpack Compose 1.8.0            |
| 2.1.0 ~ 2.2.1      | Compose Multiplatform 1.7.0     | Jetpack Compose 1.7.0            |
| 1.2.2              | Compose Multiplatform 1.6.0     | Jetpack Compose 1.6.0            |
| 1.0.0 ~ 1.2.1      | Compose Multiplatform 1.5.2     | Jetpack Compose 1.5.0            |
| 0.1.0 ~ 0.2.0      | Compose Multiplatform 1.4.1     | Jetpack Compose 1.4.3            |

## Android SDK Version

It is recommended to use the following target SDK version when using this library for Android platform.

| GridLayout Version | Android Target SDK | Android Minimum SDK |
|--------------------|--------------------|---------------------|
| 2.7.0 ~ current    | Android 16 (36)    | Marshmallow (23)    |
| 2.5.0 ~ 2.6.0      | Android 16 (36)    | Lollipop (21)       |
| 2.3.0 ~ 2.4.0      | Android 15 (35)    | Lollipop (21)       |
| 1.0.0 ~ 2.2.1      | Android 14 (34)    | Lollipop (21)       |
| 0.1.0 ~ 0.2.0      | Android 13 (33)    | Lollipop (21)       |
