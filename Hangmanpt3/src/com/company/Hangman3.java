//Robert Marsh
//Nov 22, 2020
//Program plays full game of hangman


package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

class HangmanPanel extends JPanel implements ActionListener {//declare variables
    private Button clickButton;
    private JTextField enterText;
    private JPanel controlPanel;
    private int countWrong = 0;//letter wrong
    private String[] words = {"LUMP", "SAT", "ALONE", "IN", "A", "BOGGY", "MARSH"};//word array
    private String word;//single word variable
    String input = "";//stores users input from text field
    String displayString;//displayString will be empty with spaces,
    //then filled in with user guess if correct and used to display
    //above blank lines
    String message = "FILL IN THE BLANKS!";//message to user

    public HangmanPanel() {//create button, text field, listener for panel
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html
        setLayout(new BorderLayout());
        controlPanel = new JPanel();//create panel
        clickButton = new Button("Guess");//create button
        clickButton.addActionListener(this);//make button listen for action, mouse click
        enterText = new JTextField();//spot for user to input letter/word
        enterText.setFont(new Font("Courier", Font.PLAIN, 18));//set input font and size
         //https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.add(enterText);//add text field to panel
        controlPanel.add(clickButton);//add button to panel
        controlPanel.setPreferredSize(new Dimension(600, 40));//set size of panel
        add(controlPanel, BorderLayout.PAGE_END);
        word = words[new SecureRandom().nextInt(words.length)];//get single random word
        displayString = " ".repeat(word.length());//create blanks over lines
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //System.out.println(countWrong);//for test purposes
        //System.out.println(word);//for test purposes
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.black);
        Font c = new Font("Courier", Font.PLAIN, 48);//set up font
        g2D.setFont(c);
        g2D.drawString("HANGMAN", 95,40);//panel text header
        drawGallow(g2D);
        drawLines(g2D);
        switch(countWrong) {//switch to iterate through drawing methods
            case 6:
                drawRightLeg(g2D);
                message = "YOU LOSE!";
                displayString = word;
                clickButton.setLabel("New Game");
                countWrong = 0;
                revalidate();//revalidate panel to update button size for different text length
            case 5:
                drawLeftLeg(g2D);
            case 4:
                drawRightArm(g2D);
            case 3:
                drawLeftArm(g2D);
            case 2:
                drawBody(g2D);
            case 1:
                drawHead(g2D);
        }
        //checkWordLetter();

        //print letters
        for (int i = 0; i < displayString.length(); i++) {
            drawLetter(g2D, i);
        }
        //display message
        g2D.drawString(message, 95, 90 );

    }
    public void checkWordLetter() {
        if (input.length() > 1) {
            if (input.equals(word)) {
                message =  "YOU WIN!";
            }
            else {
                message = "YOU LOSE!";
            }

            displayString = word;
            clickButton.setLabel("New Game");

        }

        else if (input.length() == 1) {
            //convert word to an array of characters
            char[] wordArray = word.toCharArray() ;
            //convert display string to an array of characters
            char[] displayStringArray = displayString.toCharArray();
            //get only the first character that was entered
            //into the JTextField and store in the input variable
            char inputChar = input.charAt(0);

            boolean letterFound = false;
            for (int i = 0; i < wordArray.length; i++) {
                if (inputChar == wordArray[i]) {
                    displayStringArray[i] = inputChar;
                    letterFound = true;
                }
            }

            if (!letterFound) {
                countWrong++;
            }
            displayString = new String(displayStringArray);

            if (displayString.equals(word)) {
                message = "YOU WIN!";
                clickButton.setLabel("New Game");

            }
        }
    }

    public void drawLines(Graphics2D g2D) {//draw letter lines based on number of letter in rand word
        g2D.setColor(Color.black);
        g2D.setStroke(new BasicStroke(3.0f));
        for(int i = 0; i < word.length(); i++){
            g2D.drawLine(i * 50 + 200, 500, i * 50 + 230, 500);
        }
    }

    public void drawLetter(Graphics2D g2D, int i) {//draw letters
        g2D.drawString(String.valueOf(displayString.charAt(i)), i * 50 + 200, 495);
    }

    public void drawGallow(Graphics2D g2D) {
        g2D.setStroke(new BasicStroke(5.0f));
        g2D.setColor(Color.black);
        g2D.drawLine(25, 500, 175, 500);//base
        g2D.drawLine(100, 500, 100, 100);//main post
        g2D.drawLine(102, 175, 175, 102);//gusset
        g2D.drawLine(100, 100, 300, 100);//outward arm
        g2D.drawLine(300, 100, 300, 150);//rope
    }

    public void drawHead(Graphics2D g2D) {
        g2D.setColor(Color.yellow);
        g2D.fillOval(250, 150, 100, 100);//yellow circle
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawOval(250, 150, 100, 100);//black outline
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawArc(275, 190, 50, 40, 180, 180);//smile
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawLine(275, 170, 290, 190);//part of left eye
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawLine(275, 190, 290, 170);//part of left eye
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawLine(310, 170, 325, 190);//part of right eye
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawLine(310, 190, 325, 170);//part of right eye
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawArc(310, 212, 15, 25, 210, 180);//tongue
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawLine(315, 225, 318, 230);//tongue line
    }

    public void drawBody(Graphics2D g2D) {
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawLine(300, 250, 300, 350);//body line
    }

    public void drawLeftArm(Graphics2D g2D) {
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawLine(300, 265, 280, 330);//left arm
    }

    public void drawRightArm(Graphics2D g2D) {
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawLine(300, 265, 320, 330);//right arm
    }

    public void drawLeftLeg(Graphics2D g2D) {
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawLine(300, 350, 280, 415);//left leg
    }

    public void drawRightLeg(Graphics2D g2D) {
        g2D.setStroke(new BasicStroke(3.0f));
        g2D.setColor(Color.black);
        g2D.drawLine(300, 350, 320, 415);//right leg
    }

    @Override
    public void actionPerformed(ActionEvent e) {//event for button
        input = enterText.getText().toUpperCase();//turns user input to uppercase variable for case-insensitive check
        enterText.setText("");//clears text field
        enterText.requestFocus();//puts cursor back in text field

        if (word.equals(displayString)) {//check if input equals word and resets game
            countWrong = 0;
            clickButton.setLabel("Guess");
            word = words[new SecureRandom().nextInt(words.length)];
            message = "FILL IN THE BLANKS!";
            input = "";
            displayString = " ".repeat(word.length());
        }
        checkWordLetter();//check letter or word method call
        revalidate();//revalidate panel to update button size for different text length
        repaint();//refresh panel
    }
}

public class Hangman3 {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new HangmanGameFrame();
                frame.setTitle("Hangman Part III");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class HangmanGameFrame extends JFrame {//frame size and add panel
    public HangmanGameFrame() {
        HangmanPanel panel = new HangmanPanel();
        panel.setPreferredSize(new Dimension(600, 600));
        add(panel);
        pack();
    }
}
