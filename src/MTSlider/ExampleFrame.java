/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MTSlider;


import multiThumbSlider.lib.SliderPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author zeckzer
 */
public class ExampleFrame
    extends JFrame
{

    SliderPanel upperSlider;
    public ExampleFrame()
    {
        initGUI();
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }

    private void initGUI()
    {
        Dimension dimension = new Dimension(400, 400);
        setSize(dimension);
        setMinimumSize(dimension);
        setTitle("Multi Thumb Slider Example");

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        createUpperSlider();
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(upperSlider, gbc);
    }

    private void createUpperSlider()
    {
        upperSlider = new SliderPanel();
    }
}
