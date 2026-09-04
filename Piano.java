/*
 * Justin Tse
 * Make a Piano
 * keys: a list of the letters on the keyboard that map to the piano
 * note: a list of the musical staff
 * sound: a list of all the audio files for the notes
 * record: a list that keeps track of the notes the user has played
 * count: keeps track of how many notes have been played since the record button has been pressed
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import tapplet.TApplet;
import javax.swing.*;
import java.util.*;
public class Piano extends TApplet{
	public static void main(String[] args) {
		new Piano();
	}
	Image background;
	AudioClip[] sound;
	boolean on = false;
	int count = 0;
	static int[] record = new int[500];
	char[] keys = {'q', '2', 'w', '3', 'e', '4', 'r', 't', '6', 'y', '7', 'u', 'i', '9', 'o', '0', 'p', '-', '[', ']'}; 
	static String[] whole = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "[", "]"};
	public void init() {
		for(int z = 0; z < 500; z++) {
			record[z] = -1;
		}
		setSize(1219, 768);
		background = getImage(getCodeBase(), "./galaxy.png");
		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(background, 0);
		while(tracker.checkAll(true) != true){ }
		if (tracker.isErrorAny()){
			JOptionPane.showMessageDialog(null, "Trouble loading pictures.");
		}
		Graphics g = getScreenBuffer();
		sound = new AudioClip [24]; //creating an array of all the audio file notes
		g.drawImage(background, 0, 0, this);
		String[] note = {"F","G","A","B","C","D","E","F","G","A","B","C","D","E","F"};
		
		for(int a = 1; a < 25; a++)	{
			sound[a-1] = getAudioClip(getCodeBase(), "key" + Integer.toString(a) + ".wav");
		}
		
		//drawing whole note keys
		for(int i = 0; i < 12; i++) {
			g.setColor(Color.white);
			g.fillRect(i*100 + 9, 458, 100, 300);
			g.setColor(Color.black);
			g.drawRect(i*100 + 9, 458, 100, 300);
			g.drawString(note[i], i*100 + 59, 658);
			g.drawString("( " + whole[i] + " )", i*100 + 52, 678);
//			g.drawString(Integer.toString(i), i*100 + 65, 658);
		}
		
		//drawing sharp keys
		g.setColor(Color.black);
		g.fillRect(84, 458, 50, 150);
		g.setColor(Color.white);
		g.drawString("( 2 )", 99, 500);
		g.setColor(Color.black);
		g.fillRect(184, 458, 50, 150);
		g.setColor(Color.white);
		g.drawString("( 3 )", 199, 500);
		g.setColor(Color.black);
		g.fillRect(284, 458, 50, 150);
		g.setColor(Color.white);
		g.drawString("( 4 )", 299, 500);
		g.setColor(Color.black);
		g.fillRect(484, 458, 50, 150);
		g.setColor(Color.white);
		g.drawString("( 6 )", 499, 500);
		g.setColor(Color.black);
		g.fillRect(584, 458, 50, 150);
		g.setColor(Color.white);
		g.drawString("( 7 )", 599, 500);
		g.setColor(Color.black);
		g.fillRect(784, 458, 50, 150);
		g.setColor(Color.white);
		g.drawString("( 9 )", 799, 500);
		g.setColor(Color.black);
		g.fillRect(884, 458, 50, 150);
		g.setColor(Color.white);
		g.drawString("( 10 )", 897, 500);
		g.setColor(Color.black);
		g.fillRect(984, 458, 50, 150);
		g.setColor(Color.white);
		g.drawString("( 11 )", 997, 500);
		g.setColor(Color.black);
		g.fillRect(1184, 458, 50, 150);
		g.fillRect(1284, 458, 50, 150);
		g.fillRect(1384, 458, 50, 150);
		
		//record
		g.setColor(Color.red);
		g.fillRect(50, 50, 200, 100);
		g.setColor(Color.black);
		g.drawString("Record", 130, 100);
		
		g.setColor(Color.green);
		g.fillRect(300, 50, 200, 100);
		g.setColor(Color.black);
		g.drawString("Play", 390, 100);
		
		//Instructions
		g.setColor(Color.green);
		g.drawString("Instructions:", 800, 50);
		g.drawString("You can click the keys to play or use the keys on the keyboard to play", 700, 70);
		g.drawString("Click record to record the notes you play", 700, 90);
		g.drawString("Click record again to stop recording", 700, 110);
		g.drawString("Click play to play the notes you recorded only AFTER you have finished recording", 700, 130);
		
	}
	//when keyboard keys are pressed to play the piano
	public void keyDown(KeyEvent key)	{
		for(int i = 0; i < keys.length; i++) {
			if(key.getKeyChar() == keys[i]) {
				sound[i].play();
				if(on) {
					record[count] = i;
					count++;
				}
			}
		}
	}
	public void mouseDown(int x, int y) {
		//Record button
		if(x >= 50 && x <= 250 && y >= 50 && y <= 150) {
			if (on == false) {
				for(int z = 0; z < 500; z++) {
					record[z] = -1;
				}
				count = 0;
			}
			on = !on;
		}
		//Play button
		if(x >= 300 && x <= 500 && y >=50 && y <= 150 && on == false) {
			int l = 0;
			while (record[l] != -1) {
				sound[record[l]].play();
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				l++;
			}
		}
		if(x >= 84 && x <= 134 && y <= 608 && y >= 458)	{
			sound[1].play();
			if(on) {
				record[count] = 1;
				count++;
			}
		}
		else if(x >= 184 && x <= 234 && y <= 608 && y >= 458)	{
			sound[3].play();
			if(on) {
				record[count] = 3;
				count++;
			}
		}
		else if(x >= 284 && x <= 334 && y <= 608 && y >= 458)	{
			sound[5].play();
			if(on) {
				record[count] = 5;
				count++;
			}
		}
		else if(x >= 484 && x <= 534 && y <= 608 && y >= 458)	{
			sound[8].play();
			if(on) {
				record[count] = 8;
				count++;
			}
		}
		else if(x >= 584 && x <= 634 && y <= 608 && y >= 458)	{
			sound[10].play();
			if(on) {
				record[count] = 10;
				count++;
			}
		}
		else if(x >= 784 && x <= 834 && y <= 608 && y >= 458)	{
			sound[13].play();
			if(on) {
				record[count] = 13;
				count++;
			}
		}
		else if(x >= 884 && x <= 934 && y <= 608 && y >= 458)	{
			sound[15].play();
			if(on) {
				record[count] = 15;
				count++;
			}
		}
		else if(x >= 984 && x <= 1034 && y <= 608 && y >= 458)	{
			sound[17].play();
			if(on) {
				record[count] = 17;
				count++;
			}
		}
		else if(x >= 1184 && x <= 1234 && y <= 608 && y >= 458)	{
			sound[20].play();
			if(on) {
				record[count] = 20;
				count++;
			}
		}
		else {
			for(int i = 0; i < 12; i++)	{
				//checks if sharps have been played
				
				//checks if whole notes have been played
				/*else */if((x >= i*100 + 9 && x <= (i+1)*100 + 9 && y <= 768 && y >= 458))	{
					for(int j = 0; j < 12; j++) {
						if(i == j)	{
							if(j == 4)	{
								sound[7].play();
								if(on) {
									record[count] = 7;
									count++;
								}
							}
							else if(j == 7)	{
								sound[12].play();
								if(on) {
									record[count] = 12;
									count++;
								}
							}
							else if(j == 11) {
								sound[19].play();
								if(on) {
									record[count] = 19;
									count++;
								}
							}
							else if(j < 4) {
								sound[j*2].play();
								if(on) {
									record[count] = j*2;
									count++;
								}
							}
							else if(j > 4 && j < 7) {
								sound[j*2 - 1].play();
								if(on) {
									record[count] = j*2 - 1;
									count++;
								}
							}
							else if(j > 7 && j < 11) {
								sound[j*2 - 2].play();
								if(on) {
									record[count] = j*2 - 2;
									count++;
								}
							}
							else {
								sound[j*2 - 3].play();
								if(on) {
									record[count] = j*2 - 3;
									count++;
								}
							}
						}
					}
				}	
			}
		}
	}	
}