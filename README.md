# NetLogo Goo extension

This extension lets you manipulate the GUI widgets in NetLogo's Interface tab.  "GUI", hence "Goo".

This version of the extension requires (exactly) NetLogo 5.0beta2.

See the "Downloads" link here on GitHub for a zip archive you can download.

## Development status

Preliminary.  Known shortcomings include:

 * Do all primitives work with all widget types?  Don't know, hasn't been tested.
 * The format of the strings you pass to `goo:add` isn't documented yet. You can grab examples from any NetLogo model file.
 * `goo:add` requires a `goo:recompile` afterwards for at least some widget types (buttons yes, notes no). This could be improved.
 * ...?

## Usage

We'll fix this for NetLogo 5.0beta3, but at the moment, for this to work you need to replace the `scala-library.jar` file in the lib directory of your NetLogo application directory with the `scala-library.jar` file from Scala 2.8.1.  You can copy it from your Scala 2.8.1 installation, or grab it from this URL: `http://scala-tools.org/repo-releases/org/scala-lang/scala-library/2.8.1/scala-library-2.8.1.jar`

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

Use the NETLOGO environment variable to point to your NetLogo directory (containing NetLogo.jar) and SCALA_HOME to point to your Scala 2.8.1 installation.  For example:

    NETLOGO=/Applications/NetLogo\\\ 5.0beta2 SCALA_HOME=/usr/local/scala-2.8.1.final make

If compilation succeeds, `goo.jar` will be created.

## Credits

Thanks to Eric Russell for providing the initial version of the chooser primitives.

## Terms of Use

All contents © 2011 Uri Wilensky.

The contents of this package may be freely copied, distributed, altered, or otherwise used by anyone for any legal purpose.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
