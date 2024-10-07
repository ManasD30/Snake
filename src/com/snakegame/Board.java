package com.snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    int score = 0;
    int highest_score = 99;

    // Specifying information about dots
    private int dots;
    private final int dot_size = 10;
    private final int all_dots = 8192;


    private final int  random_location = 28;

    // Game assets
    private Image head;
    private Image body;
    private Image apple;

    // Apple coordinates
    private int apple_X;
    private int apple_y;

    // Coordinate Arrays
    private final int[] x = new int[all_dots];
    private final int[] y = new int[all_dots];

    private Timer timer;

    int width = getWidth();
    int height = getHeight();

    private boolean inGame = true;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    Font font = new Font("SANS SERIF", Font.BOLD, 10);

    Font font2 = new Font("SANS SERIF", Font.BOLD, 20);
    FontMetrics metric = getFontMetrics(font2);

    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500, 500));
        setFocusable(true); // Allows game to receive inputs by bringing game in focus

        loadImage();
        initGame();
    }

    // Initializing images to display in window
    public void loadImage(){

        ImageIcon image1 = new ImageIcon(ClassLoader.getSystemResource("com/snakegame/icons/head.png"));
        head = image1.getImage();

        ImageIcon image2 = new ImageIcon(ClassLoader.getSystemResource("com/snakegame/icons/dot.png"));
        body = image2.getImage();

        ImageIcon image3 = new ImageIcon(ClassLoader.getSystemResource("com/snakegame/icons/apple.png"));
        apple = image3.getImage();
    }

    // Game initialization
    public void initGame() {

        dots = 3;

        // Creating coordinates for the snake image
        for(int i = 0;i < dots;i++){
            y[i] = 100;
            x[i] = 100 - i*dot_size;
        }

        locateApple();

        timer = new Timer(140, this);
        timer.start();
    }

    // Generating random co-ordinates for the apples
    public void locateApple(){

        int x = (int)(Math.random()*random_location);
        apple_X = x*dot_size;
        int y = (int)(Math.random()*random_location);
        apple_y = y*dot_size;

    }

    // Function for generating snake game graphics
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        draw(graphics);
    }

    // Function to draw the snake
    public void draw(Graphics graphics){

        if(inGame){
            graphics.drawImage(apple, apple_X, apple_y, this);
            for(int i = 0;i < dots;i++){
                if(i==0)
                    graphics.drawImage(head, x[i], y[i], this);
                else
                    graphics.drawImage(body, x[i], y[i], this);
            }
            graphics.setColor(Color.WHITE);
            graphics.setFont(font);
            String currentScore = "Score: " + String.valueOf(score);
            graphics.drawString(currentScore, 440, 20);
        } else {
            gameOver(graphics);
        }

        Toolkit.getDefaultToolkit().sync();

    }

    public void gameOver(Graphics graphics){

        String gameIsOver = "GAME OVER!! YOU LOSE";
        String playerScore = "Your Score: " + String.valueOf(score);

        graphics.setColor(Color.WHITE);
        graphics.setFont(font2);

        graphics.drawString(gameIsOver, (500 - metric.stringWidth(gameIsOver))/2, 150);
        graphics.drawString(playerScore, (500 - metric.stringWidth(playerScore))/2, 180);
    }

    // Function to move the snake
    public void move(){
        for(int i = dots; i>0 ; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        if(leftDirection)
            x[0] = x[0] - dot_size;
        if(rightDirection)
            x[0] = x[0] + dot_size;
        if (downDirection)
            y[0] = y[0] + dot_size;
        if (upDirection)
            y[0] = y[0] - dot_size;
    }

    // Function of ActionListener class to respond to click of buttons
    public void actionPerformed(ActionEvent e){

        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    public void checkApple() {
        if(x[0]==apple_X && y[0]==apple_y) {
            dots++;
            score++;
            locateApple();
        }
    }

    public void checkCollision(){
        for(int i=dots;i>0;i--){
            if( (i>=4) && (x[0]==x[i]) && (y[0]==y[i]) )
                inGame = false;
            if(y[0] >= 500)
                y[0] = y[0] - 500;
            if(x[0] >= 500)
                x[0] = x[0] - 500;
            if(y[0] < 0)
                y[0] = y[0] + 500;
            if(x[0] < 0)
                x[0] = x[0] + 500;
            if(!inGame)
                timer.stop();
        }
    }

    // Class for sensing keystrokes for navigating the snake
    public class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if(key==KeyEvent.VK_RIGHT && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if(key==KeyEvent.VK_LEFT && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if(key==KeyEvent.VK_UP && (!downDirection)) {
                rightDirection = false;
                upDirection = true;
                leftDirection = false;
            }

            if(key==KeyEvent.VK_DOWN && (!upDirection)) {
                rightDirection = false;
                leftDirection = false;
                downDirection = true;
            }
        }
    }
}