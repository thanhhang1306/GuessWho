
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.*; 


public class Game  extends JPanel implements Runnable, KeyListener,  MouseListener, MouseMotionListener{


	private BufferedImage back; 
	private int key; 
	private ArrayList<Student> studentList;
	private ArrayList<Integer> crossOutPic;
	private char screen;
	private ImageIcon menu, choose, finalGuess, question, cross;
	private final int SCREEN_WIDTH = 1300, SCREEN_HEIGHT = 650, BOX = 3;
	private Board board;
	private int position, student;
	private String phrasePrinted;
	private String chain;
	private int counter; 
	
	public Game() {
		new Thread(this).start();	
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		crossOutPic = new ArrayList<Integer>();
		key =-1; 
		screen = 'M';
		board = new Board();
		menu = new ImageIcon("mainmenu.png");
		choose = new ImageIcon("choose.png");
		finalGuess = new ImageIcon("final guess.png");
		question = new ImageIcon("question.png");
		cross = new ImageIcon("cross.png");
		student = pickStudent();
		position = 0;
		phrasePrinted = "";
		chain = "";
		counter = 0;
		
		try {
			studentList = getValue();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	String fileName = "3rdPeriodAttributes.csv";
	}



	public void run()
	{
		try
		{
			while(true)
			{
				Thread.currentThread().sleep(5);
				repaint();
			}
		}
		catch(Exception e)
		{
		}
	}



	public ArrayList<Student> getValue() throws FileNotFoundException{//ArrayList<Student> 
		ArrayList<Student> list = new ArrayList<>();
		Scanner scan = new Scanner(new File("3rdPeriodAttributes.csv"));
		scan.nextLine();
		while(scan.hasNextLine()){
			String[]tempArray = scan.nextLine().split(",");
			Student s = new Student(tempArray[0],tempArray[1],tempArray[2],Boolean.parseBoolean(tempArray[3]),Boolean.parseBoolean(tempArray[4]),
					Boolean.parseBoolean(tempArray[5]),tempArray[6],Boolean.parseBoolean(tempArray[7]),Boolean.parseBoolean(tempArray[8]),tempArray[9]);
			list.add(s);
		}
		return list;
		//System.out.println(list.get(31).getName());// : STATUS: WORKING

		//	return list;
	}






	public void paint(Graphics g){

		Graphics2D twoDgraph = (Graphics2D) g; 
		if( back ==null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 


		Graphics g2d = back.createGraphics();

		g2d.clearRect(0,0,getSize().width, getSize().height);

		//System.out.println(studentList.get(student).getName());
		//System.out.println(studentList.get(student).getTwoData(0,2));
		//System.out.println(studentList.get(student).getTwoData(1,1));
		
		getScreen(g2d);
		displayImage(g2d);
		twoDgraph.drawImage(back, null, 0, 0);

	}

	public void displayImage(Graphics g){
		if (screen == 'C'||screen == 'F'){
			int i = 0;
			 	while(i < studentList.size()){
				if (i < 15)
					g.drawImage(studentList.get(i).getPic().getImage(),75*(i+1), 200, 70, 100, this);
				else 
					g.drawImage(studentList.get(i).getPic().getImage(),38 + 75*(i-15), 350, 70, 100, this);
				i++;

			}
			/* System.out.println(studentList);
			 * override method, not called on with Student.toString();
			 */
		}
	}

	public void getScreen(Graphics g){
		switch(screen){
		case 'M':
			g.drawImage(menu.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			break;
		case 'C': 
			g.drawImage(choose.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			crossOutPic(crossOutPic,g);
			g.drawImage(cross.getImage(),75, 700, 70, 600, this);
			break;
		case 'F': 
			g.drawImage(finalGuess.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			break;
		case 'Q': 
			g.drawImage(question.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			break;
		case 'S':
			drawChar(g);
			getStuff(g);
			writeWords(g);
			g.drawString("Turn: " + getCounter(), 580, 500);
			break;
		}
	}

	public void writeWords(Graphics g){
		g.setColor(Color.white);
		g.setFont(new Font("Garamond", Font.BOLD, 50));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString(getPhrasePrinted(), 650 -  metrics.stringWidth(getPhrasePrinted())/2, SCREEN_HEIGHT/2 + 80);
	}

	public void printPage(int col, int row, Graphics g){
		if (!board.getValues(col, row).equals("none")){
			if(row != 1){
				ImageIcon picture = new ImageIcon(String.valueOf(col) + String.valueOf(row) + ".png");
				g.drawImage(picture.getImage(),0,0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			}
			else{
				ImageIcon picture1 = new ImageIcon("yesno.png");
				g.drawImage(picture1.getImage(),0,0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			}
		}

	}

	public void drawChar(Graphics g){
		for (int i = 0; i < BOX; i++){
			for (int j = 0; j < BOX; j++)
				printPage(i, j, g);
		}
	}

	public int pickStudent(){
		return (int)(Math.random()*((31 - 0) + 1) + 0);
	}

	public void getStuff(Graphics g){
		for (int i = 0; i < BOX; i++){
			for (int j = 0; j < BOX; j++){
				switch (board.getValues(i,j)){
				case "sex":
					threeChoices("Male", "Female", "Other",i);
					break;
				case "hair": 
					threeChoices("Black", "Blonde", "Brunette",i);
					break;
				case "hair length":
					threeChoices("Long", "Short", "Pulled back/in ponytail/wearing hat",i);
					break;
				case "animal":
					twoChoice(i,j); // Note: True is yes; False is no
					break;
				case "background":
					twoChoice(i,j); // Note: True is light background; False is dark background 
					break;
				case "smiling":
					twoChoice(i,j); // Note: True is yes; False is no
					break;
				case "pic":
					twoChoice(i,j); // Note: True is face; False is whole body
					break;
				case "facial hair": 
					twoChoice(i,j); // Note: True is yes; False is no
					break;		
				case "name": // Whether a letter is in the student’s name; main methods done in keyPressed();
					g.setColor(Color.white);
					g.setFont(new Font("Garamond", Font.BOLD, 30));
					FontMetrics metrics = getFontMetrics(g.getFont());
					g.drawString(getChain(), 750 -  metrics.stringWidth(getChain())/2, SCREEN_HEIGHT/2 - 20);
					// System.out.println(getChain());
				}
			}
		}	
	}
	

	public void threeChoices(String choice1, String choice2, String choice3, int i){
		if(getPosition() == 1 && studentList.get(student).getThreeData(i).equals(choice1))
			phrasePrinted = "true";
		else if(getPosition() == 2 && studentList.get(student).getThreeData(i).equals(choice2))
			phrasePrinted = "true";
		else if(getPosition() == 3 && studentList.get(student).getThreeData(i).equals(choice3))
			phrasePrinted = "true";
		else if (getPosition() == 1 || getPosition() == 2 || getPosition() == 3)
			phrasePrinted = "false";
		else if(getPosition() == 4)
			phrasePrinted = "";
	}
	
	public void twoChoice(int i, int j){
		if(getPosition() == 1 && studentList.get(student).getTwoData(i,j) == true)
			phrasePrinted = "true";
		else if(getPosition() == 3 && studentList.get(student).getTwoData(i,j) == false)
			phrasePrinted = "true";
		else if (getPosition() == 1 || getPosition() == 3)
			phrasePrinted = "false";
		else if(getPosition() == 4)
			phrasePrinted = "";
	}

	
	public void crossOutPic(ArrayList<Integer> list, Graphics g) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) < 15)
				g.drawImage(cross.getImage(),75*(list.get(i)+1), 200, 70, 100, this);
			else if(list.get(i)>14)
				g.drawImage(cross.getImage(),38 + 75*(list.get(i)-15), 350, 70, 100, this);
		}
	}
	public String getPhrasePrinted(){
		return phrasePrinted;
	}

	public int getPosition(){
		return position; 
	}
	
	public String getChain(){
		return chain;
	}
	
	public int getCounter(){
		return counter;
	}
	
	public boolean checkChar(String charKey){
		if(studentList.get(student).getName().toUpperCase().contains(charKey))
			return true;
		else return false;
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}





	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		key= e.getKeyCode();
		char charKey = (char)(key);
		if(board.getValues(2, 2).equals("name")){
			phrasePrinted = "";
			if(getChain().length() > 40)
				chain = "";
			if(charKey !=32){
				chain += charKey + ", ";
				counter++;
			}
			if(checkChar(String.valueOf(charKey)))
					phrasePrinted = "true";
				else phrasePrinted = "false";
		}
		
		if(key == 32){
			switch (screen){
			case 'F': 
				screen = 'C';
				break;
			case 'Q': 
				screen = 'C';
				break;
			case 'S': 
				screen = 'Q';
				board.resetQuestion();
				position = 4;
				phrasePrinted = "";
				break;
			}
		}
	}



	@Override
	public void keyReleased(KeyEvent e) {




	}



	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		switch (screen){
		case 'M': 
			if (arg0.getY()>355 && arg0.getY()<455){
				if (arg0.getX()>338 && arg0.getX()<626)
					screen = 'C';
				if (arg0.getX()>669 && arg0.getX()<956)
					System.exit(0);
			}
			break;
		case 'C':
			if(arg0.getY()>527 && arg0.getY()<613){
				if(arg0.getX()>992 && arg0.getX()<1235) 
					screen = 'Q';
				else if(arg0.getX()>65 && arg0.getX()<308)
					//	System.out.println("hey");
					screen = 'F';
			}
			for (int i = 0; i < studentList.size();i++) {
				if (i < 15) {
					if(arg0.getX() > 75*(i+1) && arg0.getX() < 75*(i+1) + 70 && arg0.getY() > 200 && arg0.getY() < 300) 
						crossOutPic.add(i);
				}
				else if (i>14) {
					if(arg0.getX() > 38+ 75*(i - 15) && arg0.getX() < 38 + 75*(i - 15) + 70 && arg0.getY() > 350 && arg0.getY() < 450) 
						crossOutPic.add(i);
				}
			System.out.println(crossOutPic);
			}
			
			break;
		case 'Q': 
			for (int i = 0; i < BOX; i++){
				for (int j = 0; j < BOX; j++){
					if (arg0.getX() > i*414 + 95 && arg0.getX() < i*414 + 95 + 284
							&& arg0.getY() > j*158 + 199 && arg0.getY() < j*158 + 199 + 100){
						board.setValue(i,j);
						screen = 'S';
						//	screen = 'M';
					}
				}
			}
			break;
		case 'S': 
			if(arg0.getY()>238 && arg0.getY()<340){
				if(arg0.getX()>130 && arg0.getX()<418 && !board.getValues(2,2).equals("name")) {
					position = 1;
					counter++;
				}
				else if(arg0.getX()>507 && arg0.getX()<795&&(board.getValues(0, 0).equals("sex") || 
						board.getValues(1, 0).equals("hair") || board.getValues(2, 0).equals("hair length"))){
					position = 2;
					counter++;
				}
				else if(arg0.getX()>884 && arg0.getX()<1172){
					position = 3;
					counter++;
				}
			}
			else position = 4;
		}
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}





}
