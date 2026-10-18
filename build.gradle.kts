// Plugin versions match marmalade-tts-android 1.3.0 and marmalade-stt-android.
// The apps' own root builds supply these plugins when they include the module.
plugins {
    id("com.android.library") version "8.9.3" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false
}
