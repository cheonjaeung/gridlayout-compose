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

> Checkout latest version in [GitHub Releases](https://github.com/cheonjaeung/gridlayout-compose/releases).

## Platform Supports

GridLayout for Compose is a multiplatform library.
The following table shows which platform is supported.

| Platform | Status      |
|----------|-------------|
| Android  | ✅ Available |
| iOS      | ✅ Available |
| Desktop  | ✅ Available |

## Compose Version Dependency

This library depends on Compose library.
The following table shows Compose versions which each library version depends on.

| GridLayout Version | Compose Version (Android) | Compose Version (Multiplatform) |
|--------------------|---------------------------|---------------------------------|
| 2.1.0 ~ current    | Jetpack Compose 1.7.0     | Compose Multiplatform 1.7.0     |
| 1.2.2 ~ 2.0.0      | Jetpack Compose 1.6.0     | Compose Multiplatform 1.6.0     |
| 1.0.0 ~ 1.2.1      | Jetpack Compose 1.5.0     | Compose Multiplatform 1.5.2     |
| 0.1.0 ~ 0.2.0      | Jetpack Compose 1.4.3     | Compose Multiplatform 1.4.1     |

## Android SDK Version

It is recommended to use following target SDK version when using this library for Android platform.

| GridLayout Version | Android Target SDK | Android Minimum SDK |
|--------------------|--------------------|---------------------|
| 1.0.0 ~ current    | Android 14 (34)    | Lollipop (21)       |
| 0.1.0 ~ 0.2.0      | Android 13 (33)    | Lollipop (21)       |
