package multiThumbSlider;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SliderPanel extends JPanel {

	private final JLabel text;
	private final MThumbSlider slider;
	private final ChangeRootPanel changeRootPanel;

	public SliderPanel(final ChangeRootPanel crp, int number) {
		this.changeRootPanel = crp;
		this.text = new JLabel();


		this.slider = new MThumbSlider(crp, 4);
		this.slider.setPaintTicks(true);
		this.slider.setPaintLabels(true);
		this.slider.setSnapToTicks(true);
		this.slider.putClientProperty("JSlider.isFilled", Boolean.TRUE);
		this.slider.setSnapToTicks(true);
		this.slider.setMajorTickSpacing(5);
		this.slider.setMinorTickSpacing(1);

		this.slider.setValueAt(-11, 3);
		this.slider.setValueAt(-15, 2);
		this.slider.setValueAt(-17, 1);
		this.slider.setValueAt(-19, 0);

		this.slider.setFillColorAt(Color.green, 1);
		this.slider.setFillColorAt(Color.yellow, 2);
		this.slider.setFillColorAt(Color.red, 3);

		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new BorderLayout());
		this.add(text, BorderLayout.NORTH);
		this.add(slider, BorderLayout.CENTER);
	}
    public MThumbSlider getMThumbSlider(){
    	return this.slider;
    	
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
    public double getMinExp(){
    	return this.slider.getMinimum();
    }
    public double getMaxExp(){
    	return this.slider.getMaximum();
    }
    public int getTicks(){
    	return 20;
    }
	public void setValue(double value, int index) {
		int expValue = 0;
		Pattern p = Pattern.compile(".*[eE](.*)");
		Matcher m = p.matcher((new Double(value)).toString());
		if (m.matches()) {
			expValue = Integer.valueOf(m.group(1));
		}

		this.slider.setValueAt(expValue, index);
	}

	public int getIntValue(int index) {
		return this.slider.getValueAt(index);
	}

	public double getValue(int index) {
		int exponent = this.slider.getValueAt(index);
		return Math.pow(10, exponent);
	}
	/*public void setSliderBoxHeight(int box, int value) {
		this.slider.boxHeight.put(box, new Integer(value));
	}
*/
        
       /* public void setMCSnoInRange(String MCSnoInRange) {
                this.MCSnoInRange = MCSnoInRange;
        }
        
        public String getMCSnoInRange() {
                    return this.MCSnoInRange;
        }
        */

        /*public void setMCSnoInRange(int box, int MCSnoInRange) {
            System.out.println("box MCSnoInRange boxHeight " + box + " " + MCSnoInRange + " " + computeHistogramHeight(MCSnoInRange));
            System.out.flush();
                this.setSliderBoxHeight(box, computeHistogramHeight(MCSnoInRange));
                this.slider.setMCSnoInRange(box, MCSnoInRange);

        }

        public int getMCSnoInRange(int box) {
                    return this.slider.getMCSnoInRange(box);
        }


        public int computeHistogramHeight(int MCSnoInRange) {
            int boxHeight = 0;

                if (MCSnoInRange > 0) {
                    boxHeight += 5;
                }
                if (MCSnoInRange > 25) {
                    boxHeight += 5;
                }
                if (MCSnoInRange > 50) {
                    boxHeight += 5;
                }
                if (MCSnoInRange > 75) {
                    boxHeight += 5;
                }
                if (MCSnoInRange > 100) {
                    boxHeight += 5;
                }
            return boxHeight;

        }
         */
}
