package com.snakegame;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class SnakeGame extends JFrame {

    SnakeGame() {

        super("Snake Game");
        add(new Board());
        pack(); // To refresh window to reflect changes

        // Set window

        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);

    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.print("Snake Game is up and Running!!");

        new SnakeGame();



    }
}