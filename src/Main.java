import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{
	private static final int WIDTH =1300;
	private static final int HEIGHT=650;
	
	public Main () {
		super("Guess Who!");
		setSize(WIDTH, HEIGHT);
		Game play = new Game();
		((Component) play).setFocusable(true);
		
		Color RoyalBlue = new Color(22,13,193);
		
		
		setBackground(RoyalBlue);
		
		
		getContentPane().add(play);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);		
	}
	

	public static void main(String[] args) {
		Main run = new Main();
		

	}


}
