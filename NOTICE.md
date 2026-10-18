# Notices

The library's code is MIT (see [`LICENSE`](LICENSE)). It was lifted from
marmalade-tts-android 1.3.0, whose code is MIT too.

Two fonts ship in the library's resources (`marmalade-ui/src/main/res/font/`),
so they ship inside every app that uses it. Both are under the **SIL Open Font
License, Version 1.1**; the full text is in
[`LICENSES/OFL-1.1.txt`](LICENSES/OFL-1.1.txt). The OFL permits bundling,
redistribution and commercial use. It does not extend to the code: the fonts
stay OFL, the code stays MIT.

| Font | File | Use | Upstream | Copyright |
| --- | --- | --- | --- | --- |
| Manrope (variable) | `manrope_variable.ttf` | Body and headings | https://github.com/sharanda/manrope | Copyright 2019 The Manrope Project Authors (https://github.com/sharanda/manrope) |
| Momo Trust Display | `momo_trust_display.ttf` | The "marmalade" wordmark | https://github.com/typeassociates/MomoTrustDisplay | Copyright 2024 The Momo Trust Project Authors (https://github.com/typeassociates/MomoTrustDisplay) |

The copyright lines are each font's own name-table notice (nameID 0), as
recorded in marmalade-tts-android; the files are byte-identical to the ones
that app ships. An app using this library must list both fonts and the OFL
text on its own licences screen and in its NOTICE.

No dependency beyond the Jetpack Compose BOM and Material 3 (Apache-2.0,
which the apps already ship). No GPL or LGPL code.
