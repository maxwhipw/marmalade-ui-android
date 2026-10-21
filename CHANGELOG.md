# Changelog

What each library commit changes on screen, for an app moving its pin.
Marmalade TTS's own theme is at 260fc1d (1.3.0 plus TTS #89).

## Unreleased

- **The blue, green and red presets carry no orange.** `buildPresetScheme`
  now recolours the secondary and tertiary roles too, not only primary.
  `secondaryContainer` is the preset's container (its pale tone in light
  mode, its dark tone in dark mode; it was Marmalade's Orange 50 and
  Orange 900). Material fills a default progress track, a nav bar's
  selected-tab pill and a selected segmented button with it. `WorkingDots`
  and `LiveDot` (tertiary) follow the preset. Marmalade is unchanged.
- **`marmaladeNavigationBarItemColors(marmaladePalette)`**: the bottom
  bar's tab colours, so the selected pill stands off the bar. Toast with
  its ink on the Marmalade palette, the scheme's pale secondaryContainer
  in light mode otherwise, and primary in dark mode (5.8-9.3:1 on the bar;
  the dark secondaryContainer is 1.4-1.8:1 there). An app moving its pin
  should use it for its `NavigationBarItem`s.
- **Slider tracks:** `marmaladeSliderColors()` takes the preset's container
  for the unfilled track in dark mode too. Marmalade's dark track goes
  from Orange 900 to Orange 800.
- **Error red meets AA in both modes.** Light `error` is Red 700 #B91C1C
  (Red 600 was 4.2:1 on dialogs), dark is Red 400 #F87171 (#EF4444 was
  3.8-4.0:1 on cards and menus). `ThemeContrastTest` now checks error text.
- **Wordmark docs:** a call site may set a heavier weight, which Compose
  synthesises (TTS's Speak screen draws it at 600). The font is unchanged.
