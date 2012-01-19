package org.nlogo.extensions.goo

/*
NOTE ON THREADING:

NetLogo has two threads, the job thread on which NetLogo code (including the perform
and report methods of extension primitives) runs, and the AWT event thread.  Swing
only allows us to manipulate the GUI from the event thread, so we have to be careful
to switch threads in order to do GUI stuff.

To help with this, the Helpers trait below has invokeLater and invokeAndWait methods
for easy switching to the event thread.  invokeLater queues the body for later execution
on the event thread; invokeAndWait blocks until the body has finished and returns the
resulting value.
*/

import java.awt.Component
import org.nlogo.api.{ Argument, Context, DefaultClassManager, DefaultCommand, DefaultReporter,
                       Dump, LogoList, PrimitiveManager, ReporterRunnable, Syntax, Version }
import org.nlogo.app.{ WidgetPanel, WidgetWrapper }
import org.nlogo.nvm.ExtensionContext
import org.nlogo.window.{ ChooserWidget, GUIWorkspace, Widget }
import org.nlogo.window.Events.CompileAllEvent

class GooExtension extends DefaultClassManager {
  
  def load(pm: PrimitiveManager) {
    pm.addPrimitive("recompile", new Recompile)
    pm.addPrimitive("add", new AddWidget)
    pm.addPrimitive("show", new ShowWidget)
    pm.addPrimitive("hide", new HideWidget)
    pm.addPrimitive("move", new MoveWidget)
    pm.addPrimitive("chooser-items", new GetChooserItems)
    pm.addPrimitive("set-chooser-items", new SetChooserItems)
  }

}

trait Helpers {
  def widgetsNamed(context: Context, name: String): Iterable[Widget] =
    getWidgets(context)
      .collect{case w: Widget => w}
      .filter(_.displayName == name)
  def workspace(context: Context): GUIWorkspace =
    context.asInstanceOf[ExtensionContext]
      .workspace.asInstanceOf[GUIWorkspace]
  def widgetPanel(context: Context): WidgetPanel =
    workspace(context).getWidgetContainer.asInstanceOf[WidgetPanel]
  def chooserNamed(context: Context, name: String): Option[ChooserWidget] =
    widgetsNamed(context, name)
      .collect{case cw: ChooserWidget => cw}
      .headOption
  def getWidgets(context: Context): Iterable[Widget] =
    widgetPanel(context).getComponents
      .collect{case ww: WidgetWrapper => ww.widget }
  def invokeLater(body: => Unit) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      override def run() { body }
    })
  }
  def invokeAndWait[T](context: Context)(body: => T) =
    workspace(context).waitForResult(
      new ReporterRunnable[T] {
        override def run = body
      })
}

class Recompile extends DefaultCommand with Helpers {
  override def getSyntax = Syntax.commandSyntax
  def perform(args: Array[Argument], context: Context) {
    invokeLater {
      (new CompileAllEvent).raise(workspace(context))
    }
  }
}

class AddWidget extends DefaultCommand with Helpers {
  override def getSyntax =
    Syntax.commandSyntax(Array(Syntax.StringType))
  def perform(args: Array[Argument], context: Context) {
    val spec = args(0).getString
    invokeLater {
      widgetPanel(context).loadWidget(spec.split("\n"),
                                      Version.version)
    }
  }
}

class ShowWidget extends DefaultCommand with Helpers {
  override def getSyntax =
    Syntax.commandSyntax(Array(Syntax.StringType))
  def perform(args: Array[Argument], context: Context) {
    val name = args(0).getString
    invokeLater {
      widgetsNamed(context, name)
      .foreach(_.getParent.setVisible(true))
    }
  }
}

class HideWidget extends DefaultCommand with Helpers {
  override def getSyntax =
    Syntax.commandSyntax(Array(Syntax.StringType))
  def perform(args: Array[Argument], context: Context) {
    val name = args(0).getString
    invokeLater {
      widgetsNamed(context, name)
        .foreach(_.getParent.setVisible(false))
    }
  }
}

class MoveWidget extends DefaultCommand with Helpers {
  override def getSyntax =
    Syntax.commandSyntax(Array(Syntax.StringType,
                               Syntax.NumberType,
                               Syntax.NumberType))
  def perform(args: Array[Argument], context: Context) {
    val (name, xOffset, yOffset) =
      (args(0).getString, args(1).getIntValue, args(2).getIntValue)
    invokeLater {
      for(w <- widgetsNamed(context, name)) {
        val bounds = w.getParent.getBounds
        w.getParent.setLocation(bounds.x + xOffset,
                                bounds.y + yOffset)
      }
    }
  }
}

class GetChooserItems extends DefaultReporter with Helpers {
  override def getSyntax = 
    Syntax.reporterSyntax(Array(Syntax.StringType),
                          Syntax.ListType)
  def report(args: Array[Argument], context: Context) = {
    val name = args(0).getString
    invokeAndWait(context)(chooserNamed(context, name)) match {
      case None => LogoList.Empty
      case Some(chooser) =>
        workspace(context).readFromString("[ " + chooser.choicesWrapper + " ]")
          .asInstanceOf[LogoList]
    }
  }
}

class SetChooserItems extends DefaultCommand with Helpers {
  override def getSyntax =
    Syntax.commandSyntax(Array(Syntax.StringType,
                               Syntax.ListType))
  def perform(args: Array[Argument], context: Context) {
    val name = args(0).getString
    for(chooser <- invokeAndWait(context)(chooserNamed(context, name))) {
      val values =
        args(1).getList.scalaIterator
          .map(Dump.logoObject(_, true, false))
          .mkString("", "\n", "\n")
      invokeLater { chooser.choicesWrapper(values) }
    }
  }
}
