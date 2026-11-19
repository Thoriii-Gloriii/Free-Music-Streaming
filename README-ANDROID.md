# Metrolist (Android-only)

Android-only extract of the project: `androidApp`, `shared` (common + android + jvmShared), `opusDecoder`,
`listenTogetherProto`, and the Gradle wrapper/config.

The two former git submodules are vendored as plain folders, so a normal push is all you need:
- `metroproto/` from https://github.com/MetrolistGroup/metroproto (commit c65557e)
- `opusDecoder/src/main/jni/libopus/` from https://github.com/xiph/opus (tag v1.5.2)

Build: Actions tab > "Build APK" > Run workflow (default variant: debug), or push to `main`.
The APK is uploaded as the `android-debug` artifact. Release/izzy need KEYSTORE_PATH (base64 keystore),
KEYSTORE_PASSWORD, KEY_ALIAS and KEY_PASSWORD secrets.
