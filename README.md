# Hacker's Keyboard Next

Hacker's Keyboard Next is an independent development fork based on the
original Hacker's Keyboard project and the later Android modernization work
from pull request #989.

The project focuses on improving compatibility and usability on current
Android versions while preserving the original PC-style keyboard layout and
philosophy.

## Project Goal

The goal of Hacker's Keyboard Next is to continue testing and improving the
modernized Hacker's Keyboard codebase.

Our current focus includes:

- Fixing compatibility issues on recent Android versions.
- Testing the keyboard in real-world applications and different input fields.
- Fixing problems discovered during everyday use.
- Improving behavior after switching between applications.
- Improving candidate and suggestion view compatibility.
- Keeping the project buildable with current Android development tools.

Where appropriate, improvements developed in this project may be contributed
back to Hacker's Keyboard Renew.

If the Renew project is no longer actively maintained, Hacker's Keyboard Next
can continue as an independent fork.

## Current Work

The project currently includes fixes and improvements related to:

- Android 15 IME layout and setup screen behavior.
- Settings screen navigation on newer Android versions.
- A crash caused by permanent notifications on Android 13+.
- Candidate view visibility in non-fullscreen mode.
- Opening keyboard settings from the input method.
- Composing text disappearing after switching between applications.

Further testing is ongoing, especially across different applications and
Android versions.

## Building

The project currently builds with:

- Android Gradle Plugin 8.9.0
- Gradle 8.11.1
- Java 17
- compileSdk 35
- targetSdk 35
- minSdk 23

To build a debug APK:

```bash
./gradlew assembleDebug
```

The APK will be generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Project History and Credits

### Original Project

Hacker's Keyboard was originally created by
[Klaus Weidner](https://github.com/klausw).

Original project:

https://github.com/klausw/hackerskeyboard

The original project is licensed under the Apache License 2.0.

### Android Modernization

The modern Android build and tooling work used as the foundation for this
project was developed by
[X2M7](https://github.com/X2M7) in pull request #989 for the original
Hacker's Keyboard project.

X2M7 later continued this modernization work as
[Hacker's Keyboard Renew](https://github.com/X2M7/hackerskeyboard-renew).

Hacker's Keyboard Next builds on this modernized foundation and adds further
testing, compatibility fixes and improvements.

## License

This project remains licensed under the Apache License 2.0, following the
license of the original Hacker's Keyboard project.

See [LICENSE](LICENSE) for details.
