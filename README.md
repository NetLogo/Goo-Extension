# NetLogo Goo extension

This experimental, unsupported extension lets you manipulate the GUI widgets in NetLogo's Interface tab.  "GUI", hence "Goo".

NetLogo 5.x is required.

You can download a snapshot of the extension from https://github.com/downloads/NetLogo/Goo-Extension/goo-20120119.zip

## Development status

Preliminary.  Known shortcomings include:

 * For now, requires the full app (not applet or embedding) because it uses org.nlogo.app stuff.  (It's fixable, but not trivially.)
 * Do all primitives work with all widget types?  Don't know, haven't tested.
 * `goo:add` requires a `goo:recompile` afterwards for at least some widget types (buttons yes, notes no). This could be improved.
 * ...?

For something more recent and more advanced, but also still under development, check out https://github.com/CRESS-Surrey/eXtraWidgets.

## Usage

The provided primitives are:

 * `goo:show name` (command)
 * `goo:hide name` (command)
 * `goo:move name x-offset y-offset` (command)
 * `goo:add spec` (command)
 * `goo:recompile` (command) --- as a side effect, halts the model
 * `goo:chooser-items` (reporter)
 * `goo:set-chooser-items` (command)

All of these are demonstrated in the included "Goo Tester" model.

See https://github.com/NetLogo/NetLogo/wiki/Model-file-format for documentation on the format of the strings that `goo:add` expects.

## Building

The extension is written in Scala (version 2.9.1).

Use the NETLOGO environment variable to point to your NetLogo directory (containing NetLogo.jar) and SCALA_HOME to point to your Scala 2.9.1 installation.  For example:

    NETLOGO=/Applications/NetLogo\\\ 5.0 SCALA_HOME=/usr/local/scala-2.9.1.final make

If compilation succeeds, `goo.jar` will be created.

## Credits

Thanks to Eric Russell for providing the initial version of the chooser primitives.

## Terms of Use

[![CC0](http://i.creativecommons.org/p/zero/1.0/88x31.png)](http://creativecommons.org/publicdomain/zero/1.0/)

The NetLogo Goo extension is in the public domain.  To the extent possible under law, Uri Wilensky has waived all copyright and related or neighboring rights.
