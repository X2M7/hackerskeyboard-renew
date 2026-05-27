# Hacker's Keyboard Renew

Hacker's Keyboard Renew is an unofficial modernized fork of
[Hacker's Keyboard](https://github.com/klausw/hackerskeyboard), the Android
software keyboard that brings a PC-style layout to touch devices.

The goal of this fork is to keep the original spirit of Hacker's Keyboard while
making the project build and run on current Android tooling, with a longer-term
target of Google Play readiness.

![Hacker's Keyboard 5-row layout](hk-5row-en-s.png)

## Project Status

This project is in early modernization work.

Current changes include:

- Updated Gradle and Android Gradle Plugin configuration.
- Added a modern Android namespace and target SDK configuration.
- Updated support-library notification usage to AndroidX.
- Added required manifest `android:exported` declarations for newer Android
  versions.
- Fixed native build configuration for current CMake/NDK tooling.
- Set the default keyboard mode to the full 5-row layout in portrait and
  landscape.

The app currently builds with:

- Android Gradle Plugin 8.9.0
- Gradle 8.11.1
- Java 17
- compileSdk 35
- targetSdk 35
- minSdk 23

## Important Notes

The original Hacker's Keyboard codebase was created around 2011 and is based on
the Android 2.3 Gingerbread AOSP keyboard. Some parts of the app still need
deeper modernization before this fork should be considered production-ready.

Known areas that still need work:

- Language switching behavior on modern Android.
- Popup key behavior on modern Android.
- Full compatibility testing across recent Android versions.
- Release signing and Play Store packaging workflow.
- Privacy policy, store listing, and other Play Store submission materials.

## Building

From the repository root:

```bash
./gradlew assembleDebug
```

The debug APK will be generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

For a release bundle:

```bash
./gradlew bundleRelease
```

The release bundle will be generated at:

```text
app/build/outputs/bundle/release/app-release.aab
```

## Installing a Debug Build

After building a debug APK, install it with:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

If the keyboard still opens with an older layout after reinstalling, clear the
app data or uninstall the previous build first. Android keeps keyboard
preferences across updates.

```bash
adb shell pm clear org.pocketworkstation.pckeyboard
```

Then enable Hacker's Keyboard Renew in Android's system keyboard settings.

## Relationship to the Original Project

This is not an official continuation by the original author. It is an
independent fork intended to renew compatibility with modern Android versions.

Original project:

```text
https://github.com/klausw/hackerskeyboard
```

Original author:

```text
Klaus Weidner
```

## License

This project remains licensed under the Apache License 2.0, following the
original Hacker's Keyboard project. See [LICENSE](LICENSE) for details.
