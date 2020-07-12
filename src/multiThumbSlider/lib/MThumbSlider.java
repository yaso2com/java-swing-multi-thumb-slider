/* (swing1.1.1) */
package multiThumbSlider.lib;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.Icon;
import javax.swing.JSlider;

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

    // use the SliderPanel instead of the ChangeRootPanel since it gets the values from there anyway
	public MThumbSlider(final SliderPanel parent, int n) {
		createThumbs(n);
		updateUI();
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
		    updateLabelUIs();
		    setUI(new MetalMThumbSliderUI());
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

	public Color getFillColorAt(int index) {
		return fillColors[index];
	}

	public void setFillColorAt(Color color, int index) {
		fillColors[index] = color;
	}

	public Color getTrackFillColor() {
		return trackFillColor;
	}
}
