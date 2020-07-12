/* (swing1.1.1) */
package multiThumbSlider.lib;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;


/**
 * @version 1.0 09/08/99
 */
public class MetalMThumbSliderUI extends MetalSliderUI 
  implements MThumbSliderAdditional {

  MThumbSliderAdditionalUI additonalUi;
  MouseInputAdapter mThumbTrackListener;
  final MThumbSlider parent;

  public static ComponentUI createUI(JComponent c)    {
    return new MetalMThumbSliderUI((MThumbSlider)c);
  }

  
  public MetalMThumbSliderUI(MThumbSlider parent)   {
	  this.parent = parent;
  }

  // added the override method, because this method is already in the parent
  @Override
  public void installUI(JComponent c)   {
    additonalUi = new MThumbSliderAdditionalUI(this);
    additonalUi.installUI(c);
    mThumbTrackListener = createMThumbTrackListener((JSlider) c);
    super.installUI(c);
  }

  // added the override method, because this method is already in the parent
  @Override
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);
    additonalUi.uninstallUI(c);
    additonalUi = null;
    mThumbTrackListener = null;
  }
  
  protected MouseInputAdapter createMThumbTrackListener( JSlider slider ) {
    return additonalUi.trackListener;
  }

  // added the override method, because this method is already in the parent
  @Override
  protected TrackListener createTrackListener( JSlider slider ) {
    return null;
  }

  // added the override method, because this method is already in the parent
  @Override
  protected ChangeListener createChangeListener( JSlider slider ) {
    return additonalUi.changeHandler;
  }

  // added the override method, because this method is already in the parent
  @Override
  protected void installListeners( JSlider slider ) {
    slider.addMouseListener(mThumbTrackListener);
    slider.addMouseMotionListener(mThumbTrackListener);
    slider.addFocusListener(focusListener);
    slider.addComponentListener(componentListener);
    slider.addPropertyChangeListener( propertyChangeListener );
    slider.getModel().addChangeListener(changeListener);
  }

  // added the override method, because this method is already in the parent
  @Override
  protected void uninstallListeners( JSlider slider ) {
    slider.removeMouseListener(mThumbTrackListener);
    slider.removeMouseMotionListener(mThumbTrackListener);
    slider.removeFocusListener(focusListener);
    slider.removeComponentListener(componentListener);
    slider.removePropertyChangeListener( propertyChangeListener );
    slider.getModel().removeChangeListener(changeListener);
  }

  // added the override method, because this method is already in the parent
  @Override
  protected void calculateGeometry() {
    super.calculateGeometry();
    additonalUi.calculateThumbsSize();
    additonalUi.calculateThumbsLocation();
  }

  // added the override method, because this method is already in the parent
  @Override
  protected void calculateThumbLocation() {}
    
  
    
  
  Icon thumbRenderer;

  // added the override method, because this method is already in the parent
  @Override
  public void paint( Graphics g, JComponent c ) {
    Rectangle clip = g.getClipBounds();
    Rectangle[] thumbRects = additonalUi.getThumbRects();
    thumbRect = thumbRects[0];    
    int thumbNum = additonalUi.getThumbNum();
    
    if ( slider.getPaintTrack() && clip.intersects( trackRect ) ) {
      boolean filledSlider_tmp = filledSlider;
      filledSlider = false;
      paintTrack( g );
      filledSlider = filledSlider_tmp;
      
      if ( filledSlider ) {
        g.translate(  trackRect.x,  trackRect.y );
        
        Point t1 = new Point(0,0);
        Point t2 = new Point(0,0);
        Rectangle maxThumbRect = new Rectangle(thumbRect);
        thumbRect = maxThumbRect;
        
        if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
          t2.y = (trackRect.height - 1) - getThumbOverhang();
          t1.y = t2.y - (getTrackWidth()- 1);
          t2.x = trackRect.width - 1;
          int maxPosition = xPositionForValue(slider.getMaximum());
	  thumbRect.x = maxPosition - (thumbRect.width / 2) -2;
	  thumbRect.y = trackRect.y;
        }
        else {
          t1.x = (trackRect.width - getThumbOverhang()) - getTrackWidth();
          t2.x = (trackRect.width - getThumbOverhang()) - 1;
          t2.y = trackRect.height - 1;
          int maxPosition = yPositionForValue(slider.getMaximum());
	  thumbRect.x = trackRect.x;
	  thumbRect.y = maxPosition - (thumbRect.height / 2) -2;
        } 
        
        Color fillColor = ((MThumbSlider)slider).getTrackFillColor(); 
        if (fillColor == null) {
          fillColor = MetalLookAndFeel.getControlShadow();
        }
        fillTrack( g, t1, t2, fillColor);
        
        for (int i=thumbNum-1; 0<=i; i--) {
          thumbRect = thumbRects[i];
          fillColor = ((MThumbSlider)slider).getFillColorAt(i);
          if (fillColor == null) {
            fillColor = MetalLookAndFeel.getControlShadow();
          }
          fillTrack( g, t1, t2, fillColor);
        }
        
        g.translate( -trackRect.x, -trackRect.y );    
      }      
    }
    if ( slider.getPaintTicks() && clip.intersects( tickRect ) ) {
      paintTicks( g );
      paintBoxes( g );
    }
    if ( slider.getPaintLabels() && clip.intersects( labelRect ) ) {
      paintLabels( g );
    }    
    
    for (int i=thumbNum-1; 0<=i; i--) {
      if ( clip.intersects( thumbRects[i] ) ) {
        thumbRect = thumbRects[i];
        thumbRenderer = ((MThumbSlider)slider).getThumbRendererAt(i);
        if (thumbRenderer == null) {
          if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
            thumbRenderer = horizThumbIcon;
          } else {
            thumbRenderer = vertThumbIcon;
          }
        }
        paintThumb( g );
      }
    }    
  }

  // added the override method, because this method is already in the parent
  @Override
  public void paintThumb(Graphics g) {     
    thumbRenderer.paintIcon( slider, g, thumbRect.x,     thumbRect.y );    
  }    
  

  public void fillTrack(Graphics g, Point t1, Point t2, Color fillColor) {
    //                               t1-------------------
    //                               |                   |
    //                               --------------------t2    
    int middleOfThumb = 0;
    
    if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
      middleOfThumb = thumbRect.x + (thumbRect.width / 2) - trackRect.x;	        
      if ( slider.isEnabled() ) {
        g.setColor(fillColor);     		  
        g.fillRect( t1.x+2,
		    t1.y+2,
	            middleOfThumb - t1.x -1,
		    t2.y - t1.y -3);		    
        g.setColor(fillColor.brighter());
        g.drawLine( t1.x+1, t1.y+1, middleOfThumb, t1.y+1 );
        g.drawLine( t1.x+1, t1.y+1, t1.x+1,        t2.y-2 );        
      } else {		  
        g.setColor(fillColor);    
        g.fillRect( t1.x, 
		    t1.y,
		    middleOfThumb - t1.x +2,
		    t2.y - t1.y );
      }
    }
    else {
      middleOfThumb = thumbRect.y + (thumbRect.height / 2) - trackRect.y;    
      if ( slider.isEnabled() ) {      	      
        g.setColor( slider.getBackground() );
	g.drawLine( t1.x+1, middleOfThumb, t2.x-2, middleOfThumb );
	g.drawLine( t1.x+1, middleOfThumb, t1.x+1, t2.y - 2 );
	g.setColor( fillColor );
	g.fillRect( t1.x + 2,
		    middleOfThumb + 1,
		    t2.x - t1.x -3,
		    t2.y-2 -  middleOfThumb);
      } else {	      
        g.setColor( fillColor );
	g.fillRect( t1.x,
		    middleOfThumb +2,
	            t2.x-1 - t1.x,
		    t2.y - t1.y );
      }
    }
  }  

  protected void paintBox( Graphics g, int y, int xFrom, int xTo, int height ,int i) {
      g.setColor(Color.black);
      g.fillRect(xFrom, y, xTo-xFrom, height);
      if (height != 0){
          String str =  Integer.toString(i);
          g.drawString( str, xTo -20, height - 27);// the hight is -ve so i am going over the box by 3
      }
  }

  void paintBoxes(Graphics g){
	  Rectangle tickBounds = tickRect;
	  g.translate( 0, tickBounds.y);

      int value = slider.getMinimum();
      int xPos = 0;
      int xPos2 = 0;
      int MCSno = 0 ;
      int i=0;
      if ( slider.getMinorTickSpacing() > 0 ) {
          while ( value < slider.getMaximum() ) {
              xPos = xPositionForValue( value );
              xPos2 = xPositionForValue( value + slider.getMinorTickSpacing() );
              //Get height of the box
              /*Integer height = this.parent.boxHeight.get(value);
              System.out.println("Box (value, height): " + value + " " + height);
              if(height == null){
            	  height = 1;
              }
              // function to get mcsno
              try {
               Integer MCSnoInRange = getMCSnoInRange(value);
                System.out.println("MCSnoInRange (value, MCSnoInRange): " + value + " " + MCSnoInRange);
               if (MCSnoInRange != null) {
                    paintBox( g,TICK_BUFFER-30, xPos, xPos2, -height, MCSnoInRange.intValue());
                }
              }catch (Throwable thr) {
                  System.out.println("Value: " + value);
                  System.out.println(thr.getMessage());
              }
               */
              value += slider.getMinorTickSpacing();
              //this.parent.getm
          }
      }
      g.translate( 0, -tickBounds.y);
      
  }


  // added the override method, because this method is already in the parent
  @Override
  public void scrollByBlock(int direction) {}
  // added the override method, because this method is already in the parent
  @Override
  public void scrollByUnit(int direction) {}
  
  
  //  
  //  MThumbSliderAdditional
  //
  // added the override method, because this method is already in the parent
  @Override
  public Rectangle getTrackRect() {
    return trackRect;
  }
  // added the override method, because this method is already in the parent
  @Override
  public Dimension getThumbSize() {
    return super.getThumbSize();
  }
  // added the override method, because this method is already in the parent
  @Override
  public int xPositionForValue(int value) {
    return super.xPositionForValue( value);
  }
  // added the override method, because this method is already in the parent
  @Override
  public int yPositionForValue(int value) {
    return super.yPositionForValue( value);
  }
  
  /*public int getMCSnoInRange(int box){
    return this.parent.getMCSnoInRange(box);
  }
*/
}

