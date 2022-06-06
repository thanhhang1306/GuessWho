
public class Board {
	private String[][] questionBoard;
	private int col, row;
	
	public Board(){
		col = 3;
		row = 3;
		questionBoard = new String[col][row];
		resetQuestion();
	}
	
	public void resetQuestion(){
		for (int i = 0; i < col; i++){
			for (int j = 0; j < row; j++){
				questionBoard[i][j] = "none";
			//	System.out.println(gameBoard[i][j]);
			}
			//System.out.println();
		}
	}
	
	
	public String getValues(int co, int ro){
		return questionBoard[co][ro];
	}
	
	public void setValues(int co, int ro, String a){
		questionBoard[co][ro] = a;
	}
	
	
	public void setValue(int co, int ro){
		switch(co){
			case 0:
				if(ro == 0) questionBoard[co][ro] =  "sex";
				else if(ro == 1) questionBoard[co][ro] =  "animal";
				else questionBoard[co][ro] =  "background";
				break;
			case 1: 
				if(ro == 0) questionBoard[co][ro] =  "hair";
				else if(ro == 1) questionBoard[co][ro] =  "smiling";
				else questionBoard[co][ro] =  "pic";
				break;
			case 2: 
				if(ro == 0) questionBoard[co][ro] =  "hair length"; 
				else if(ro == 1) questionBoard[co][ro] =  "facial hair";
				else questionBoard[co][ro] =  "name";
				break;
		}
	}
}