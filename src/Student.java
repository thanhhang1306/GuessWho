import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Student {
	private String fileName, studentName, hairColor, hair, sex;
	private boolean animal, smile, face, backgroundCol, wholeBody;
	private ImageIcon image;
	
	public Student(){
		image = new ImageIcon();
		fileName = ""; studentName = "";
		hairColor = ""; hair = "";
		sex = "";
		animal = false; smile = false;
		face = false; backgroundCol = false; 
		wholeBody = false;		
	}
	
	public Student(String fN, String sN, String hC, boolean s, 
			boolean bC, boolean a, String h, boolean f, boolean wB, String sX){
		image = new ImageIcon(fN);
		studentName = sN; 
		hairColor = hC; 
		smile = s;
		backgroundCol = bC;
		animal = a;
		hair = h; 
		face = f;
		wholeBody =wB;
		sex = sX;
	}
	
	
	
	public String getName(){
		return studentName;
	}
	
	public ImageIcon getPic(){
		return image;
	}
	
	public String toString(){
		return "gender: " + sex + ", hair color: " + hairColor + ", hair length: " + hair;
	}
	
	/*public String getHairColor(){
		return hairColor;
	}
	
	public String getHair(){
		return hair;
	}
	
	public String getSex(){
		return sex;
	}
	
	public ImageIcon getPic(){
		return image;
	}
	
	public String toString(){
		return "gender: " + sex;
	}
	
	public boolean getAnimal(){
		return animal;
	}
	
	public boolean getSmile(){
		return smile;
	}
	
	public boolean getComposition(){
		return wholeBody;
	}
	
	public boolean getFacialHair(){
		return face;
	}
	
	public boolean getBackground(){
		return backgroundCol;
	}
	 */
	
	
	
	public String getThreeData(int x){
		String value = "";
			switch(x){
			case 0:
				value = sex;
				break;
			case 1: 
				value = hairColor;
				break;
			case 2: 
				value = hair;
				break;
			}
			return value;
		}
	
	public boolean getTwoData(int x, int y){
		Boolean value = false;
			switch(x){
			case 0:
				if(y==1)
					value = animal;
				else if (y==2)
					value = backgroundCol;
				break;
			case 1: 
				if (y==1)
					value = smile;
				else if (y==2)
					value = wholeBody;
				break;
			case 2: 
				value = face;
				break;
			}
			value.toString();
			return value;
		}
	
	
	}
