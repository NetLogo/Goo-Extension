# NetLogo Goo extension

This extension lets you manipulate the GUI widgets in NetLogo's Interface tab.  "GUI", hence "Goo".

This version of the extension requires (exactly) NetLogo 5.0beta3.

You can download a pre-built snapshot of the extension here: https://github.com/downloads/NetLogo/Goo-Extension/goo.zip

## Development status

Preliminary.  Known shortcomings include:

 * For now, requires the full app (not applet or embedding) because it uses org.nlogo.app stuff.  (It's fixable, but not trivially.)
 * Do all primitives work with all widget types?  Don't know, haven't tested.
 * The format of the strings you pass to `goo:add` isn't documented yet. You can grab examples from any NetLogo model file.
 * `goo:add` requires a `goo:recompile` afterwards for at least some widget types (buttons yes, notes no). This could be improved.
 * ...?

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

## Building

The extension is written in Scala (version 2.9.0.1).

Use the NETLOGO environment variable to point to your NetLogo directory (containing NetLogo.jar) and SCALA_HOME to point to your Scala 2.9.0.1 installation.  For example:

    NETLOGO=/Applications/NetLogo\\\ 5.0beta3 SCALA_HOME=/usr/local/scala-2.9.0.1 make

If compilation succeeds, `goo.jar` will be created.

## Credits

Thanks to Eric Russell for providing the initial version of the chooser primitives.

## Terms of Use

[![CC0](http://i.creativecommons.org/p/zero/1.0/88x31.png)](http://creativecommons.org/publicdomain/zero/1.0/)

The NetLogo Goo extension is in the public domain.  To the extent possible under law, Uri Wilensky has waived all copyright and related or neighboring rights.
