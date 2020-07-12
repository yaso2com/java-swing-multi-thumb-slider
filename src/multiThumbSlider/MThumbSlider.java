/* (swing1.1.1) */
package multiThumbSlider;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.Icon;
import javax.swing.JSlider;

import de.vfes.gui.actions.Actions;
import de.vfes.gui.contentpanel.changerootpanel.ChangeRootPanel;

/**
 * @version 1.0 09/08/99
 */
public class MThumbSlider extends JSlider {
	protected int thumbNum;
	protected BoundedRangeModel[] sliderModels;
	protected Icon[] thumbRenderers;
	protected Color[] fillColors;
	protected Color trackFillColor;

	private static final String uiClassID = "MThumbSliderUI";

	public final HashMap<Integer, Integer> boxHeight;
        private HashMap<Integer, Integer> MCSnoInRange;
	
	public MThumbSlider(final ChangeRootPanel parent, int n) {
		this.boxHeight = new HashMap<Integer, Integer>();
		this.MCSnoInRange = new HashMap<Integer, Integer>();
		createThumbs(n);
		updateUI();
		this.addMouseListener(Actions.SLIDER_ACTION(parent, this));
		
	}

	protected void createThumbs(int n) {
		thumbNum = n;
		sliderModels = new BoundedRangeModel[n];
		thumbRenderers = new Icon[n];
		fillColors = new Color[n];
		for (int i = 0; i < n; i++) {
			sliderModels[i] = new DefaultBoundedRangeModel(-20, 0, -20, 0);
			thumbRenderers[i] = null;
			fillColors[i] = null;
		}
	}

	public void updateUI() {
		 
		  // AssistantUIManager.setUIName(this);
		    //super.updateUI();
		        
		    
		    // another way
		    //
		    updateLabelUIs();    
		    //setUI(AssistantUIManager.createUI(this));
		    //setUI(new BasicMThumbSliderUI(this));
		    setUI(new MetalMThumbSliderUI(this));
		    //setUI(new MotifMThumbSliderUI(this));    
		    

		 
	}

	public String getUIClassID() {
		return uiClassID;
	}

	public int getThumbNum() {
		return thumbNum;
	}

	public int getValueAt(int index) {
		return getModelAt(index).getValue();
	}

	public void setValueAt(int n, int index) {
		try {
			if (index > 0) {
				if (n < getModelAt(index - 1).getValue()) {
					return;
				}
			}
			if (index < thumbNum - 1) {
				if (n > getModelAt(index + 1).getValue()) {
					return;
				}
			}
			getModelAt(index).setValue(n);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// should I fire?
	}

	public int getMinimum() {
		return getModelAt(0).getMinimum();
	}

	public int getMaximum() {
		return getModelAt(0).getMaximum();
	}

	public BoundedRangeModel getModelAt(int index) {
		return sliderModels[index];
	}

	public Icon getThumbRendererAt(int index) {
		return thumbRenderers[index];
	}

	public void setThumbRendererAt(Icon icon, int index) {
		thumbRenderers[index] = icon;
	}

	public Color getFillColorAt(int index) {
		return fillColors[index];
	}

	public void setFillColorAt(Color color, int index) {
		fillColors[index] = color;
	}

	public Color getTrackFillColor() {
		return trackFillColor;
	}

	public void setTrackFillColor(Color color) {
		trackFillColor = color;
	}

        /*public void setMCSnoInRange(int box, int MCSnoInRange) {
                System.out.println("box :" + box + "MCSnoInRange" + MCSnoInRange);
                MCSnoInRange=0;
                this.MCSnoInRange.put(box, new Integer(MCSnoInRange));

        }

        public int getMCSnoInRange(int box) {
                    return this.MCSnoInRange.get(box);
        }

         */

}
