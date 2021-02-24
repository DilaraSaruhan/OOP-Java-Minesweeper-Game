import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener; //Fare hareketini sýralamak için

public class GUI extends JFrame {
	
	public boolean resetter = false;
	
	Date startDate = new Date();
	
	int spacing = 5; //Aralýk ölçüsü
	
	int neighs =0;  //Komþular
	
	public int mx = -100;
	public int my = -100;
	
	public int smileyX = 605;
	public int smileyY = 5;
	
	public int smileyCenterX = smileyX + 35;
	public int smileyCenterY = smileyY + 35;
	
	public int timeX = 1130;
	public int timeY = 5;
	
	public int sec = 0;
	// public int min = 0;
	// public int hou = 0;
	
	public boolean happiness = true;
	
	public boolean victory = false; 
	
	public boolean defeat = false;
	
	Random rand = new Random(); //Rastgele mayýn atamak için
	
	int[][] mines = new int[16][9];  //Mayýn olan kutular
	int[][] neighbours = new int[16][9];  //Komþular
	boolean[][] revealed = new boolean[16][9];  //Ortaya çýkarmak için
	boolean[][] flagged = new boolean[16][9];  //iþaretlenen kutular
	
	
	
	public GUI() {
		this.setTitle("Minesweeper Game");              //Baþlýk
		this.setSize(1294, 835);                        //Alan Boyutu 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Exit Butonu
		this.setVisible(true);       //Ekran oluþturmak için
		this.setResizable(false);    //Ekran boyutunu sabitlemek için
		
		for (int i=0; i<16; i++) {
			for(int j=0; j<9; j++) {
				if (rand.nextInt(100) < 10) { 
			        mines[i][j] = 1;
				}
				else {
					mines[i][j] = 0;
				}
				revealed[i][j]=false;
			}
		}
		
		for (int i=0; i<16; i++) {
			for(int j=0; j<9; j++) {
				neighs = 0;
				for (int m=0; m<16; m++) {
					for(int n=0; n<9; n++) {
						if(!(m == i && n == j)) {
						if (isN(i,j,m,n) == true)
							neighs++;
						}
					}
				}
				neighbours[i][j] = neighs;
			}
		}
		
		
		Board board = new Board();  //Board Oluþturma
		this.setContentPane(board);
		
		Move move = new Move();     //Fare hareketini dinlemek için
		this.addMouseMotionListener(move);
		
	Click click = new Click();  //Farenin týklamasý için
		this.addMouseListener(click);
	}	

	public class Board extends JPanel {
		
		public void paintComponent(Graphics g) {
			g.setColor(Color.pink); //Arka plan rengi
			g.fillRect(0, 0, 1280, 800);  //Arka plan renginin kaplayacaðý alan
			
			
			for (int i=0; i<16; i++) {     //16 Sütun 
				
				for(int j=0; j<9; j++) {   //9 Satýr
					
					g.setColor(Color.gray); //Mayýnlarýn rengi
					
					// Mayýnlarý gösterir
					/* if (mines[i][j]==1) {
					 g.setColor(Color.yellow);
					 } */
					 
					 if (revealed[i][j]== true) {
						g.setColor(Color.white);
						if(mines[i][j] ==1 ) {
							g.setColor(Color.red);
						}
						 }
					
				/*	if(mines[i][j] == 1) {  //Mayýna týkladýktan sonra kutunun rengi sarý olsun diye
						g.setColor(Color.yellow);
					} */
					
					if (mx >= spacing+i*80 && mx < spacing+i*80+80-2*spacing && my >= spacing+j*80+80+26 && my < spacing+j*80+26+80+80-2*spacing ) {
						g.setColor(Color.magenta);  //Fare üzerindeyken kutunun rengi
						/* if (revealed[i][j]== false) {
							g.setColor(Color.black);
							if (mines[i][j] == 0 && neighbours[i][j] !=0) {
								if(neighbours[i][j] ==1 ) {
									revealed[i][j]=true;
									g.setColor(Color.blue);
									System.out.println("1");
								} else if (neighbours[i][j] == 2) {
									revealed[i][j]=true;
									g.setColor(Color.green);
									System.out.println("2");
								}  else if (neighbours[i][j] ==3) {
									revealed[i][j]=true;
									g.setColor(Color.red);
									System.out.println("3");
								}else if (neighbours[i][j] == 4) {
									revealed[i][j]=true;
									g.setColor(new Color(0,0,128));	
									System.out.println("4");
								}else if (neighbours[i][j] == 5) {
									revealed[i][j]=true;
									g.setColor(new Color(178,34,34));
									System.out.println("5");
								}else if (neighbours[i][j] == 6) {
									revealed[i][j]=true;
									g.setColor(new Color(72,209,204));
									System.out.println("6");
								} else if (neighbours[i][j] == 8) {
									revealed[i][j]=true;
									g.setColor(Color.darkGray);	
								}
								
							g.setFont(new Font("Tahoma", Font.BOLD, 40));
							g.drawString(Integer.toString(neighbours[i][j]), i*80+27, j*80+80+55);
						 } 
					  }	*/
						
					}
						
					g.fillRect(spacing+i*80, spacing+j*80+80, 80-2*spacing, 80-2*spacing); //Mayýn yerlerinin sýralanmasý yani gri kutular
					
					if (revealed[i][j]== true) {
						g.setColor(Color.black);
						if (mines[i][j] == 0 && neighbours[i][j] !=0) {
							if(neighbours[i][j] ==1 ) {
								g.setColor(Color.blue);
								
							} else if (neighbours[i][j] == 2) {
								g.setColor(Color.green);
								
							}  else if (neighbours[i][j] ==3) {
								g.setColor(Color.red);
								
							}else if (neighbours[i][j] == 4) {
								g.setColor(new Color(0,0,128));	
								
							}else if (neighbours[i][j] == 5) {
								g.setColor(new Color(178,34,34));
								
							}else if (neighbours[i][j] == 6) {
								g.setColor(new Color(72,209,204));
								
							} else if (neighbours[i][j] == 8) {
								g.setColor(Color.darkGray);	
							}
							
						g.setFont(new Font("Tahoma", Font.BOLD, 40));
						g.drawString(Integer.toString(neighbours[i][j]), i*80+27, j*80+80+55);
					 } else if (mines[i][j] == 1){
						 g.fillRect(i*80+10+20, j*80+80+20, 20, 40);
						 g.fillRect(i*80+20, j*80+80+10+20, 40, 20);
						 g.fillRect(i*80+5+20, j*80+80+5+20, 30, 30);
						 g.fillRect(i*80+38, j*80+80+15, 4, 50);
						 g.fillRect(i*80+15, j*80+80+38, 50, 4);
					 }
				  }	
			   }
			}
			
			//Smile
			g.setColor(Color.yellow);	
			g.fillOval(smileyX, smileyY, 70, 70);
			g.setColor(Color.BLACK);	
			g.fillOval(smileyX+15, smileyY+20, 10, 10);
			g.fillOval(smileyX+45, smileyY+20, 10, 10);
			
			if(happiness == true ) {
				g.fillRect(smileyX+20, smileyY+50, 30, 5);
				g.fillRect(smileyX+17, smileyY+45, 5, 5);
				g.fillRect(smileyX+48, smileyY+45, 5, 5);
			} else {
				g.fillRect(smileyX+20, smileyY+45, 30, 5);
				g.fillRect(smileyX+17, smileyY+50, 5, 5);
				g.fillRect(smileyX+48, smileyY+50, 5, 5);
				
			}
			
			
			//Time
			
			g.setColor(Color.BLACK);
			g.fillRect(timeX, timeY, 140, 70);
			if(defeat == false && victory == false) {
			sec= (int) ((new Date().getTime()-startDate.getTime())/1000);
			}
			if(sec > 999) {
				sec = 999;
			}
			g.setColor(Color.WHITE);
			if(victory == true) {
				g.setColor(Color.green);
			} else if (defeat == true) {
				g.setColor(Color.red);
			}
			g.setFont(new Font("Tahoma", Font.PLAIN, 60));
			if(sec < 10) {
				g.drawString("00"+Integer.toString(sec), timeX, timeY+65);	
			} else if (sec< 100) {
				g.drawString("00"+Integer.toString(sec), timeX, timeY+65);	
			} else {
			  g.drawString(Integer.toString(sec), timeX, timeY+65); 
				
			}
		}
	}
	
	public class Move implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {   //Fare sürüklendiði zaman
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {   //Farenin hareket ettiði konum
			// TODO Auto-generated method stub
			mx = e.getX();
			my = e.getY();
			
			/*  Farenin anlýk konumlarýnýn hepsini ekrana yazmak için
			System.out.println("The mouse was moved!");
			
			System.out.println("X: " + mx + ", Y: " +my);
			*/
		}
		
	}
	public class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {  //Farenin týklandýðý konum
			// TODO Auto-generated method stub
			System.out.println("The mouse was clicked!");
			if(inBoxX() != -1 && inBoxY() != -1) {
				revealed[inBoxX()][inBoxY()]=true;
				
			}
			
			
			if(inBoxX() != -1 && inBoxY() != -1) {
			System.out.println("The mouse is in the [" + inBoxX() + "," + inBoxY() + "], Number of mine neighs: " + neighbours[inBoxX()][inBoxY()]); //Fare konumunu bildiren yer
		}
			else {
				System.out.println("The pointer is not inside of any box!");
			}
			
			if (inSmiley() == true) {
				System.out.println("The pointer is inside the smiley!");
			} else {
				System.out.println("The pointer is not inside the smiley!");
			}
			
			if (inSmiley() == true) {
				resetAll();
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
	
	public void checkVictoryStatus() {
		for (int i=0; i<16; i++) {
			for(int j=0; j<9; j++) {
				if(revealed[i][j] == true && mines[i][j] == 1) {
					defeat = true ;
					happiness = false;
				}
			  }
		   }
		
		if (totalBoxesRevealed() >= 144 - totalMines()) {
			victory = true;
		}
	}
	
	
	public int totalMines() {
		int total = 0;
		for (int i=0; i<16; i++) {
			for(int j=0; j<9; j++) {
			if(mines[i][j] == 1) {
				total++;
			  }
		   }
		}
		return total;
	}
	
	public int totalBoxesRevealed() {
		int total = 0;
		for (int i=0; i<16; i++) {
			for(int j=0; j<9; j++) {
			  if(revealed[i][j] == true) {
				total++;
			  }
		   }
		}
		return total;
	}
	
	public void resetAll() {
		resetter = true;
		startDate = new Date();
		
		happiness= true;
		victory = false;
		defeat = false;
		
		for (int i=0; i<16; i++) {
			for(int j=0; j<9; j++) {
				if (rand.nextInt(100) < 20) { //Random mayýn atamak için
			        mines[i][j] = 1;
				}
				else {
					mines[i][j] = 0;
				}
				revealed[i][j]=false;
				flagged[i][j]=false;
			}
		}
		
		for (int i=0; i<16; i++) {
			for(int j=0; j<9; j++) {
				neighs = 0;
				for (int m=0; m<16; m++) {
					for(int n=0; n<9; n++) {
						if(!(m == i && n == j)) {
						if (isN(i,j,m,n) == true)
							neighs++;
						}
					}
				}
				neighbours[i][j] = neighs;
			}
		}
		resetter = false;
	}
	
	public boolean inSmiley() {
		int dif = (int) Math.sqrt(Math.abs(mx-smileyCenterX)*Math.abs(mx-smileyCenterX)+Math.abs(my-smileyCenterY)*Math.abs(my-smileyCenterY));
		if (dif < 35) {
			return true;
		}
		
		return false;
	}
	public int inBoxX() {  //Farenin x konumu
		for (int i=0; i<16; i++) {
			
			for(int j=0; j<9; j++) {
				
				if (mx >= spacing+i*80 && mx < spacing+i*80+80-2*spacing && my >= spacing+j*80+80+26 && my < spacing+j*80+26+80+80-2*spacing) {
					return i;
				}
			}
		}
		return -1;
	}
	public int inBoxY() {  //Farenin y konumu
        for (int i=0; i<16; i++) {
			
			for(int j=0; j<9; j++) {
				
				if (mx >= spacing+i*80 && mx < spacing+i*80+80-2*spacing && my >= spacing+j*80+80+26 && my < spacing+j*80+26+80+80-2*spacing) {
					return j;
				}
			}
		}
		return -1;
		
	}
	public boolean isN(int mX, int mY, int cX, int cY) {
		if(mX - cX < 2 && mX - cX > -2 && mY- cY < 2 && mY - cY >  -2 && mines[cX][cY] ==1) {
			return true;
		}
		return false;
	}
}
