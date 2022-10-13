
import javax.swing.JFrame;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Utilities.Constants.*;
import static Utilities.Controller.*;

public class Bot extends Frame implements ActionListener {
    static JFrame frame;

    public static void main(String args[])
    {
        Bot redAlertBot = new Bot();
        frame = new JFrame("RAT ALERT B0T");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Button sovietsButton = new Button(SOVIETS);
        Button alliesButton = new Button(ALLIES);
        sovietsButton.addActionListener(redAlertBot);
        alliesButton.addActionListener(redAlertBot);

        Panel panel = new Panel();
        panel.add(sovietsButton);
        panel.add(alliesButton);

        frame.add(panel);
        frame.setSize(300, 75);
        frame.setVisible(true);
    }

    /***
     * Determine what game to play based on faction chosen at game start
     * @param event
     */
    public void actionPerformed(ActionEvent event)
    {
        try {
            findScreenResolution();
            altTabIntoGame();

            //TODO: Starting a game testing..
//            selectQuickmatch();
//            selectFaction();
//            startQuickmatchMatch(); //careful uncommenting this..

            //TODO: Testing out identifying the map
            String map = identifyQuickmatchMap();
            System.out.println("map => " + map);

//
//            switch(event.getActionCommand()){
//                case SOVIETS:
//                    startSovietGame(determineMap());
//                    break;
//                case ALLIES:
//                    startAlliesGame(determineMap());
//                    break;
//            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

}
