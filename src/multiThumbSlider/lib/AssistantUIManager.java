package multiThumbSlider.lib;

/* (swing1.1.1) */


import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;


/**
 * @version 1.0 09/08/99
 */
public class AssistantUIManager {

  public static ComponentUI createUI(JComponent c)  {
    String componentName   = c.getClass().getName();
    
    int index = componentName.lastIndexOf(".") +1;
    // StringBuffer: is synchronised, therefore, its better to use asynchronous threads by using StringBuilder => its faster
    StringBuilder sb = new StringBuilder();

    sb.append( componentName.substring(0, index) );
    
    //
    // UIManager.getLookAndFeel().getName()
    // 
    // [ Metal ] [  Motif  ] [   Mac   ] [ Windows ]
    //   Metal    CDE/Motif   Macintosh    Windows
    //
    
    String lookAndFeelName = UIManager.getLookAndFeel().getName();
    if (lookAndFeelName.startsWith("CDE/")) {
      lookAndFeelName = lookAndFeelName.substring(4);
    }
    sb.append( lookAndFeelName );    
    sb.append( componentName.substring(index) );    
    sb.append( "UI" );    
    
    ComponentUI componentUI = getInstance(sb.toString());
    
    if (componentUI == null) {
      sb.setLength(0);
      sb.append( componentName.substring(0, index) );
      sb.append( "Basic");
      sb.append( componentName.substring(index) );
      sb.append( "UI" );      
      componentUI = getInstance(sb.toString());
    }
    
    return componentUI;
  }
  
  private static ComponentUI getInstance(String name) {
    try {
      return (ComponentUI)Class.forName(name).newInstance();
    } catch (ClassNotFoundException ex) {
    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
    } catch (InstantiationException ex) {
      ex.printStackTrace();
    }
    return null;
  }
}
