# Changelog

What each library commit changes on screen, for an app moving its pin.
Marmalade TTS's own theme is at 260fc1d (1.3.0 plus TTS #89).

## Unreleased

- **The blue, green and red presets carry no orange.** `buildPresetScheme`
  now recolours the secondary and tertiary roles too, not only primary.
  The selected tab's pill (`secondaryContainer`) is the preset's pale tone
  in light mode (it was Orange 50, the bar's own cream) and its light tone
  in dark mode (it was an Orange 900 pill, 1.7:1 on the bar). `WorkingDots`
  and `LiveDot` (tertiary) follow the preset. Marmalade is unchanged.
- **Slider tracks:** `marmaladeSliderColors()` takes the preset's container
  for the unfilled track in dark mode too (a dark preset's
  `secondaryContainer` is now the fill's own colour). Marmalade's dark
  track goes from Orange 900 to Orange 800.
- **Error red meets AA in both modes.** Light `error` is Red 700 #B91C1C
  (Red 600 was 4.2:1 on dialogs), dark is Red 400 #F87171 (#EF4444 was
  3.8-4.0:1 on cards and menus). `ThemeContrastTest` now checks error text.
