package multiThumbSlider.lib;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SliderPanel extends JPanel {

	private final MThumbSlider slider;

	public SliderPanel() {

		this.slider = new MThumbSlider(this, 4);// hard coded number of thumbs
		this.slider.setPaintTicks(true);
		this.slider.setPaintLabels(true);
		this.slider.setSnapToTicks(true);
		this.slider.putClientProperty("JSlider.isFilled", Boolean.TRUE);
		this.slider.setSnapToTicks(true);
		this.slider.setMajorTickSpacing(5);
		this.slider.setMinorTickSpacing(1);

		this.slider.setValueAt(-11, 3);// (position of the thumb, the nth+1 thumb = the highest one)
		this.slider.setValueAt(-15, 2);
		this.slider.setValueAt(-17, 1);
		this.slider.setValueAt(-19, 0);

		setMThumbSliderColors(0);

		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new BorderLayout());
		this.add(slider, BorderLayout.CENTER);
	}

    public void setMThumbSliderColors(int x){
    	switch (x){
    	case 0 : {
    		this.slider.setFillColorAt(Color.green, 1);
    		this.slider.setFillColorAt(Color.yellow, 2);
    		this.slider.setFillColorAt(Color.red, 3);
    	}
    	break;
    	case 1 : {
    		this.slider.setFillColorAt(Color.blue, 1);
			this.slider.setFillColorAt(Color.yellow, 2);
			this.slider.setFillColorAt(Color.pink, 3);
    	}
    	break;
    	case 2 : {
    		this.slider.setFillColorAt(Color.red, 1);
			this.slider.setFillColorAt(Color.blue, 2);
			this.slider.setFillColorAt(Color.green, 3);
    	}
    	break;
    	default:{
    		this.slider.setFillColorAt(Color.green, 1);
    		this.slider.setFillColorAt(Color.yellow, 2);
    		this.slider.setFillColorAt(Color.red, 3);
    	}
    	break;
    	}
    }

	public double getValue(int index) {
		int exponent = this.slider.getValueAt(index);
		return Math.pow(10, exponent);
	}

	public double[] getSliderValues(){
		return new double[]{
				getValue(0),
				getValue(1),
				getValue(2),
				getValue(3)
		};
	}
}
