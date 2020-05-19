package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAbstract extends Panel implements ActionListener {


    public PanelAbstract(LayoutManager layout) {
        super(layout);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
