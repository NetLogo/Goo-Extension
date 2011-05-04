# NetLogo Goo extension

This extension lets you manipulate the GUI widgets in NetLogo's Interface tab.  "GUI", hence "Goo".

## Use

The provided primitives are:
 * `goo:chooser-items` (reporter)
 * `goo:set-chooser-items` (command)

## Building

Use the NETLOGO environment variable to point to your NetLogo directory (containing NetLogo.jar)
and SCALA_HOME to point to your Scala 2.8.1 installation:

    NETLOGO=/Applications/NetLogo\\\ 5.0beta2 SCALA_HOME=/usr/local/scala-2.8.1.final make

If compilation succeeds, `goo.jar` will be created.

## Credits

Thanks to Eric Russell for providing the initial version of the chooser primitives.

## Terms of Use

All contents © 2011 Uri Wilensky.

The contents of this package may be freely copied, distributed, altered, or otherwise used by anyone for any legal purpose.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
