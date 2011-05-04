package org.nlogo.extensions.goo

import java.awt.Component
import org.nlogo.api.{ Argument, Context, DefaultClassManager, DefaultCommand, DefaultReporter,
                       Dump, LogoList, PrimitiveManager, Syntax }
import org.nlogo.app.{ WidgetPanel, WidgetWrapper }
import org.nlogo.nvm.ExtensionContext
import org.nlogo.window.{ ChooserWidget, GUIWorkspace, Widget }

class GooExtension extends DefaultClassManager {
  
  def load(pm: PrimitiveManager) {
    pm.addPrimitive("chooser-items", new GetChooserItems)
    pm.addPrimitive("set-chooser-items", new SetChooserItems)
  }

}

trait Helpers {
  def findChooser(context: Context, name: String): Option[ChooserWidget] =
    getWidgets(context)
      .collect{case cw: ChooserWidget => cw}
      .find(_.name() == name)
  def getWidgets(context: Context): Iterable[Widget] = {
    val wp = context.asInstanceOf[ExtensionContext]
              .workspace.asInstanceOf[GUIWorkspace]
              .getWidgetContainer.asInstanceOf[WidgetPanel]
    (0 until wp.getComponentCount)
      .map(wp.getComponent)
      .collect{case ww: WidgetWrapper => ww.widget }
  }
  def invokeLater(body: => Unit) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      override def run() { body }
    })
  }
}

class GetChooserItems extends DefaultReporter with Helpers {
  override def getSyntax = 
    Syntax.reporterSyntax(Array(Syntax.TYPE_STRING),
                          Syntax.TYPE_LIST)
  override def getAgentClassString = "OTPL"
  def report(args: Array[Argument], context: Context) = {
    findChooser(context, args(0).getString) match {
      case None => LogoList.Empty
      case Some(chooser) =>
        val ec = context.asInstanceOf[ExtensionContext]
        ec.workspace.readFromString("[ " + chooser.choicesWrapper + " ]")
          .asInstanceOf[LogoList]
    }
  }
}

class SetChooserItems extends DefaultCommand with Helpers {
  override def getSyntax =
    Syntax.commandSyntax(Array(Syntax.TYPE_STRING,
                               Syntax.TYPE_LIST))
  override def getAgentClassString = "OTPL"
  def perform(args: Array[Argument], context: Context) {
    for(cw <- findChooser(context, args(0).getString)) {
      val values =
        args(1).getList.scalaIterator
          .map(Dump.logoObject(_, true, false))
          .mkString("", "\n", "\n")
      invokeLater { cw.choicesWrapper(values) }
    }
  }
}
