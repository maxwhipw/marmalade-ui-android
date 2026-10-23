# marmalade-ui-android

The shared design layer of the Marmalade Android apps: colours, the
surface and ink ramp ("depth"), soft shadows, shapes, typography with its
bundled fonts, motion, and a few generic components. It is
marmalade-tts-android's theme as of 1.3.0 plus its #89 contrast fixes
(dark mode meets WCAG AA), lifted as it was. Marmalade STT uses it too,
so both apps look and read the same.

## What is in it

`app.marmalade.ui.theme`

- `MarmaladeTheme(darkTheme, themePreset)`: the theme as TTS uses it.
  Presets `SYSTEM` (Material You on Android 12+, Marmalade before),
  `MARMALADE`, `MIDNIGHT`, `FOREST`, `BERRY`.
- `MarmaladeTheme(colorScheme, darkTheme, wordmarkColor, depth)`: the hook
  for an app's own presets. Pass a scheme you built (usually a shared one
  with a few roles changed); depth, shapes, typography and the wordmark
  colour come along.
- `MarmaladeLightColors`, `MarmaladeDarkColors`, `buildPresetScheme`,
  `ThemePreset`, `MarmaladeToast` / `MarmaladeToastInk`, `LocalWordmarkColor`,
  `resolveThemeIsDark`, `contrastRatio`, `labelOn`.
- `MaterialTheme.depth`: the layer ramp (page, card, raised, sheet, scrim),
  the ink ramp (`dim` for the quietest text, `faint` for icons, `ghost`,
  `line`, `line2`) and the soft shadows; `Modifier.softShadow`,
  `darkDepth` / `lightDepth`.
- `MarmaladeMotion` (one easing, 150 / 200 / 320 ms), `riseIn()`,
  `rememberReducedMotion()`.
- `MarmaladeShapes`, `MarmaladeTypography`, `Manrope`, `Wordmark`.

`app.marmalade.ui.components`

- `MarmaladeCard`, `MarmaladeSheet`, `MarmaladeMenu`, `chevronAngle`,
  `WorkingDots`, `LiveDot`.
- `SteppedSliderTrack` and `marmaladeSliderColors()` for sliders.
- `marmaladeNavigationBarItemColors(marmaladePalette)` for a bottom bar's
  tabs: a selected pill that stands off the bar in every preset.
  `MarmaladeNavigationBarLabel(text, selected)` for their labels: bold on
  the selected tab.

App-specific parts (screens, an app's extra presets, its own components)
stay in the app.

## Using it in an app

Add it as a git submodule pinned to a commit, and include the module:

```
git submodule add https://github.com/maxwhipw/marmalade-ui-android.git third_party/marmalade-ui-android
```

`settings.gradle.kts`:

```kotlin
include(":marmalade-ui")
project(":marmalade-ui").projectDir = file("third_party/marmalade-ui-android/marmalade-ui")
```

`app/build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":marmalade-ui"))
}
```

The module's build file names its plugins without versions, so the app's
root build must declare `com.android.application` (or `.library`),
`org.jetbrains.kotlin.android` and `org.jetbrains.kotlin.plugin.compose`.
The library builds from source inside the app's build: no Maven repository,
no network access, which keeps F-Droid builds reproducible.

### Pinning rules

- Pin a commit, never a branch. Move the pin in its own commit that says
  which library commit it takes and what changes on screen.
- The library has no app-specific code. If an app needs something, it adds
  it in the app, or proposes it here when the other app could use it too.
- A change here changes every app that moves its pin. Check both apps'
  contrast tests after moving.

## Contrast

`ThemeContrastTest` holds what the theme promises (WCAG 2.2 AA): dark mode
meets AA for every curated preset (text 4.5:1, icons and control edges
3:1); light mode's quiet text and icons do too, and so does error text in
both modes (Red 700 light, Red 400 dark, on every surface and dialogs);
but light mode's orange and its
Stone 400 outline are knowingly below AA (Max's call, 2026-10-17). An app
that promises more (a high-contrast preset) builds its own scheme through
the `MarmaladeTheme(colorScheme, …)` hook and tests it itself.

## Versions

The same as marmalade-tts-android 1.3.0: AGP 8.9.3, Kotlin 2.1.0 (with
the Compose compiler plugin 2.1.0), Compose BOM 2024.12.01, Gradle 8.11.1,
compileSdk 36, minSdk 28, Java 17.

## Building on its own

```
./gradlew :marmalade-ui:assembleRelease test
```

Needs an Android SDK (`ANDROID_HOME` or `local.properties`).

## Licence

MIT, see [LICENSE](LICENSE). The two bundled fonts are under the SIL Open
Font License 1.1, see [NOTICE.md](NOTICE.md).
