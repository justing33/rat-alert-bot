import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

import static GameEngine.Game.startAlliesGame;
import static GameEngine.ObserverMode.lookForNext1v1;
import static GameEngine.ObserverMode.watchGame;
import static Utilities.Constants.*;
import static Utilities.Controller.*;


    public class Bot extends Frame implements ActionListener {
        static JFrame frame;

        public static void main(String args[]) {
            Bot redAlertBot = new Bot();
            frame = new JFrame("RAT ALERT B0T");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Button sovietsButton = new Button(SOVIETS);
            Button alliesButton = new Button(ALLIES);
            Button observeButton = new Button(OBSERVE);
            sovietsButton.addActionListener(redAlertBot);
            alliesButton.addActionListener(redAlertBot);
            observeButton.addActionListener(redAlertBot);

            Panel panel = new Panel();
            panel.add(sovietsButton);
            panel.add(alliesButton);
            panel.add(observeButton);

            frame.add(panel);
            frame.setSize(300, 75);
            frame.setVisible(true);
        }

        /***
         * Determine what game to play based on faction chosen at game start
         * @param event
         */
        public void actionPerformed(ActionEvent event) {
            try {
                findScreenResolution();
                //altTabIntoGame();

                switch (event.getActionCommand()) {
                    case SOVIETS:
                        System.out.println("RAT TESTING in five seconds:");
                        Thread.sleep(5000);
    /*                    ArrayList outputList = (ArrayList) Look_for_Building();
                        System.out.println(outputList);*/
                        lookForNext1v1(0,false);

                        System.out.println("RAT TESTING complete:");
                        break;


                    case ALLIES:
                        System.out.println("RAT-BOT initializing");
                        startAlliesGame(determineMap());
                        break;

                    case OBSERVE:
                        System.out.println("RAT-BOT initializing OBSERVE mode");
                        watchGame();
                        break;
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }

        }

    }

