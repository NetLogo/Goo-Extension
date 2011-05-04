package org.nlogo.extensions.goo;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.SwingUtilities;
import org.nlogo.api.*;
import org.nlogo.app.WidgetPanel;
import org.nlogo.app.WidgetWrapper;
import org.nlogo.nvm.ExtensionContext;
import org.nlogo.window.ChooserWidget;
import org.nlogo.window.GUIWorkspace;
import org.nlogo.window.Widget;

public class GooExtension extends org.nlogo.api.DefaultClassManager {
    
    public void load (PrimitiveManager primManager) {
        primManager.addPrimitive( "chooser-items", new GetChooserItems() );
        primManager.addPrimitive( "set-chooser-items", new SetChooserItems() );
    }
    
   public static class GetChooserItems extends DefaultReporter {
       
      public Syntax getSyntax () {
          return Syntax.reporterSyntax(new int[] { Syntax.TYPE_STRING }, Syntax.TYPE_LIST);
      }
      
      public String getAgentClassString () {
          return "OTPL" ;
      }
      
      public Object report (Argument args[], Context context)
              throws ExtensionException , LogoException {
          ChooserWidget cw = findChooserWidget(context, args[0].getString());
          if (cw != null) {
              String choicesString = cw.choicesWrapper();
              try {
                  return (LogoList)org.nlogo.compiler.Compiler.readFromString
					  ("[ " + choicesString + " ]" , context.getAgent().world().program().is3D );
              } catch (CompilerException e) {
                  // this should never happen
                  org.nlogo.util.Exceptions.handle(e);
              }
          }
          return LogoList.Empty();
      }
    }
    
   public static class SetChooserItems extends DefaultCommand {
        
        public Syntax getSyntax () {
            return Syntax.commandSyntax(new int[] { Syntax.TYPE_STRING, 
                                                    Syntax.TYPE_LIST });
        }
        
        public String getAgentClassString () {
            return "OTPL" ;
        }
        
        public void perform (Argument args[], Context context)
                throws ExtensionException , LogoException {
            final ChooserWidget cw = findChooserWidget(context, args[0].getString());
            if (cw != null) {
                String choices = cw.choicesWrapper();
                LogoList values = args[1].getList();
                StringBuffer buf = new StringBuffer() ;
                for (Iterator it = values.iterator(); it.hasNext(); ) {
                    buf.append(org.nlogo.api.Dump.logoObject(it.next() , true , false));
                    buf.append('\n');
                }
                final String valuesStr = buf.toString();
                SwingUtilities.invokeLater(new Runnable() {
                        public void run () {
                            cw.choicesWrapper(valuesStr);
                        }
                    });
            }
        }
    }
   
    private static ChooserWidget findChooserWidget (Context context, String name) {
        Widget[] widgets = getWidgets(context);
        for (int i = 0 ; i < widgets.length; i += 1) {
            if ((widgets[i] instanceof ChooserWidget) &&
                ((ChooserWidget)widgets[i]).name().equals(name)) {
                return (ChooserWidget)widgets[i];
            }
        }
        return null;
    }
   
    private static Widget[] getWidgets (Context context) {
        ExtensionContext ec = (ExtensionContext)context;
        GUIWorkspace ws = (GUIWorkspace)ec.workspace();
        WidgetPanel wp = (WidgetPanel)ws.getWidgetContainer();
        List<Widget> widgets = new ArrayList<Widget>(wp.getComponentCount());
        for (int i = 0 ; i < wp.getComponentCount(); i += 1) {
            Component c = wp.getComponent(i);
            if (c instanceof WidgetWrapper) {
                widgets.add(((WidgetWrapper)c).widget());
            }
        }
        return widgets.toArray(new Widget[widgets.size()]);
    }

}
