package Interface;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    final MainFrame frame;

    JButton hospBtn = new JButton("Hospitals");
    JButton resBtn = new JButton("Residents");
    JButton gmrBtn = new JButton("Get matching as a resident");
    JButton gmhBtn = new JButton("Get matching as a hospital");
    JButton seeMatches = new JButton("See matches");
    JButton exit = new JButton("Exit");
    public ControlPanel(MainFrame frame) {
        this.frame = frame; init();
    }
    private void init() {
        GridLayout mgr = new GridLayout(6, 1, 0, 10);
        setLayout(mgr);

        add(hospBtn);

        add(resBtn);

        add(gmrBtn);

        add(gmhBtn);

        add(seeMatches);

        add(exit);

//        GridLayout mgr = new GridLayout(8, 3, 0, 10);
//        setLayout(mgr);
//
//        add(new Label()); //spacer
//        add(new Label()); //spacer
//        add(new Label()); //spacer
//
//        add(new Label()); //spacer
//        add(hospBtn);
//        add(new Label()); //spacer
//
//        add(new Label()); //spacer
//        add(resBtn);
//        add(new Label()); //spacer
//
//        add(new Label()); //spacer
//        add(gmrBtn);
//        add(new Label()); //spacer
//
//        add(new Label()); //spacer
//        add(gmhBtn);
//        add(new Label()); //spacer
//
//        add(new Label()); //spacer
//        add(seeMatches);
//        add(new Label()); //spacer
//
//        add(new Label()); //spacer
//        add(exit);
//        add(new Label()); //spacer
//
//        add(new Label()); //spacer


        //configure listeners for all buttons
        hospBtn.addActionListener(e -> frame.seeHospitals());
        resBtn.addActionListener(e-> frame.seeResidents());
        gmrBtn.addActionListener(e -> frame.getMatchingR());
        gmhBtn.addActionListener(e -> frame.getMatchingH());
        seeMatches.addActionListener(e -> frame.seeMatches());
        exit.addActionListener(e -> frame.dispose());
    }
}
