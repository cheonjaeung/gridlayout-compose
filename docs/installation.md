# Setting up Library

This library is published to Maven Central.
To download this library, just add it to your dependencies.

```groovy
dependencies {
    implementation("io.woong.compose.grid:grid:<version>")
}
```

> Checkout latest version in [GitHub Releases](https://github.com/cheonjaewoong/gridlayout-compose/releases).

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
| 1.2.x              | Jetpack Compose 1.5.0     | Compose Multiplatform 1.5.2     |
| 1.1.x              | Jetpack Compose 1.5.0     | Compose Multiplatform 1.5.2     |
| 1.0.x              | Jetpack Compose 1.5.0     | Compose Multiplatform 1.5.2     |
| 0.2.x              | Jetpack Compose 1.4.3     | Compose Multiplatform 1.4.1     |
| 0.1.x              | Jetpack Compose 1.4.3     | Compose Multiplatform 1.4.1     |

## Android SDK Version

It is recommended to use following target SDK version when using this library for Android platform.

| GridLayout Version | Android Target SDK | Android Minimum SDK |
|--------------------|--------------------|---------------------|
| 1.2.x              | Android 14 (34)    | Lollipop (21)       |
| 1.1.x              | Android 14 (34)    | Lollipop (21)       |
| 1.0.x              | Android 14 (34)    | Lollipop (21)       |
| 0.2.x              | Android 13 (33)    | Lollipop (21)       |
| 0.1.x              | Android 13 (33)    | Lollipop (21)       |
