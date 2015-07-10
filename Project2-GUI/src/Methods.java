import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Methods extends BoxMover{
	
	public static void refreshMap(){
		/*refresh the frame every second or every move*/
		jf.remove(jpGame);
		jpGame = new JPanel();
		jpGame.add(jlblShowTime);
		jpGame.add(jlblTime);
		for(int j = 0; j < 16; j++){
			for(int i = 0; i < 16; i++){
				jlbGame[i][j] = new JLabel(elementIcon[map[total].getMapElement(i, j)]);
				jlbGame[i][j].setBounds(299 + j * 48, i * 48, 48, 48);
				jlbGame[i][j].setBorder(null);
				jlbGame[i][j].setOpaque(false);
				jlbGame[i][j].setBackground(null);;
				jpGame.add(jlbGame[i][j]);
			}
		}
		for(int i = 1; i < 10; i++){
			jpGame.add(jbgGame[i]);
		}
		jpGame.setLayout(null);
		jf.add(jpGame);
		jf.setVisible(true);
	}
	
	public static void retreat(){
		if(step > 0 && justLoad > 0){
			retreatTimes[level] += 1;
			map[total].setMap(new int [16][16]);
			justLoad--;
			total--;
			step--;
			refreshMap();
		}
		else{
			JOptionPane.showMessageDialog(null, "You can't retreat any more!");
		}
	}
	
	public static int[][] copymap(int map[][]){//a method to copy map
		int Map[][] = new int [16][16];
		for(int i = 0; i < 16; i++){
			for(int j = 0; j < 16; j++){
				Map[i][j] = map[i][j];
			}
		}
		return Map;
	}
	
	public static int leave(int i,int j){
		/*it is used to decide whether the man is stand on empty ground or the terminal of boxes*/
		if(step == 0||step == 1)return 2;
		else {
			if(map[total-1].getMapElement(i, j) == 6 || map[total-1].getMapElement(i, j) == 4)return 4;
			else return 2;
		}
	}
	
	public static void up() {
		/*move up*/
		map[total].findPos();
		int i = map[total].getplayerX(), j = map[total].getplayerY();
		int Map[][] = copymap(map[total].getMap());
	a:	switch(Map[i-1][j]){
			case 2 : Map[i-1][j] = 5; Map[i][j] = leave(i,j);break;
			case 3 :	{switch(Map[i-2][j]){
							case 2 : System.out.println("enter");Map[i][j] = leave(i,j);Map[i-1][j] = 5;Map[i-2][j] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i-1][j] = 5;Map[i-2][j] = 6;break a;							
							default : total--;step--;break a;}
						}
			case 4 :	Map[i][j] = leave(i,j);Map[i-1][j] = 5;break;
			case 6 :	{switch(Map[i-2][j]){
							case 2 : Map[i][j] = leave(i,j);Map[i-1][j] = 5;Map[i-2][j] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i-1][j] = 5;Map[i-2][j] = 6;break a;	
							default : total--;step--;break a;}
						}
			default : total--;step--;break a;
			}
			total++;step++;justLoad++;
			map[total] = new Map();
			map[total].setMap(Map);
			refreshMap();
	}
	
	public static void down() {
		/*move down*/
		map[total].findPos();
		int i = map[total].getplayerX(), j = map[total].getplayerY();
		int Map[][] = copymap(map[total].getMap());
	a:	switch(Map[i+1][j]){
			case 2 : Map[i+1][j] = 5; Map[i][j] = leave(i,j);break;
			case 3 :	{switch(Map[i+2][j]){
							case 2 : Map[i][j] = leave(i,j);Map[i+1][j] = 5;Map[i+2][j] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i+1][j] = 5;Map[i+2][j] = 6;break a;							
							default : total--;step--;break a;}
						}
			case 4 :	Map[i][j] = leave(i,j);Map[i+1][j] = 5;break;
			case 6 :	{switch(Map[i+2][j]){
							case 2 : Map[i][j] = leave(i,j);Map[i+1][j] = 5;Map[i+2][j] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i+1][j] = 5;Map[i+2][j] = 6;break a;	
							default : total--;step--;break a;}
						}
			default : total--;step--;break a;
			}
			total++;step++;justLoad++;
			map[total] = new Map();
			map[total].setMap(Map);
			refreshMap();
	}
	
	public static void left() {
		/*move left*/
		map[total].findPos();
		int i = map[total].getplayerX(), j = map[total].getplayerY();
 		int Map[][] = copymap(map[total].getMap());
	a:	switch(Map[i][j-1]){
			case 2 : Map[i][j-1] = 5; Map[i][j] = leave(i,j);break;
			case 3 :	{switch(Map[i][j-2]){
							case 2 : Map[i][j] = leave(i,j);Map[i][j-1] = 5;Map[i][j-2] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i][j-1] = 5;Map[i][j-2] = 6;break a;							
							default : total--;step--;break a;}
						}
			case 4 :	Map[i][j] = leave(i,j);Map[i][j-1] = 5;break;
			case 6 :	{switch(Map[i][j-2]){
							case 2 : Map[i][j] = leave(i,j);Map[i][j-1] = 5;Map[i][j-2] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i][j-1] = 5;Map[i][j-2] = 6;break a;	
							default : total--;step--;break a;}
						}
			default : total--;step--;break a;
			}
			total++;step++;justLoad++;
			map[total] = new Map();
			map[total].setMap(Map);
			refreshMap();
	}
	
	public static void right() {
		/*move right*/
		map[total].findPos();
		int i = map[total].getplayerX(), j = map[total].getplayerY();
		int Map[][] = copymap(map[total].getMap());
	a:	switch(Map[i][j+1]){
			case 2 : Map[i][j+1] = 5; Map[i][j] = leave(i,j);break;
			case 3 :	{switch(Map[i][j+2]){
							case 2 : Map[i][j] = leave(i,j);Map[i][j+1] = 5;Map[i][j+2] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i][j+1] = 5;Map[i][j+2] = 6;break a;							
							default : total--;step--;break a;}
						}
			case 4 :	Map[i][j] = leave(i,j);Map[i][j+1] = 5;break;
			case 6 :	{switch(Map[i][j+2]){
							case 2 : Map[i][j] = leave(i,j);Map[i][j+1] = 5;Map[i][j+2] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i][j+1] = 5;Map[i][j+2] = 6;break a;	
							default : total--;step--;break a;}
						}
			default : total--;step--;break a;
			}
			total++;step++;justLoad++;
			map[total] = new Map();
			map[total].setMap(Map);
			refreshMap();
	}
	
	public static void win_judge(){
		win = true;
		for(int i = 0;i < 16; i++){
			for(int j = 0; j < 16; j++){
				if(map[total].getMapElement(i, j) == 3)win = false;
			}
		}
		if(win && level < 9){
			score();
			level += 1;
			total++; step =0;
			map[total] = new Map();
			map[total].readMap();
			map[total].drawMap();
			refreshMap();
		}
		if(win && level ==9){
			score();
		}
		if(win){
			newlevel = true;
			loadTime = 0;
			GUI.setWinWindow();
			jf.removeKeyListener(keyListen);
			jpGame.setVisible(false);
			jmbGame.setVisible(false);
			unlock[level - 1] = true;
			GUI.pause();
			save();
		}
	}
	
	public static void score() {
		/*Calculate the score*/
		score[level] = 1000 * level - step * 10 - retreatTimes[level] * 50 - (int)(time[level]/1000);
		GUI.jtfWin();
	}

	public static void restart(){
		pauseTime2 += time[level];
		step = 0;
		total++;
		map[total] = new Map();
		map[total].readMap();
		refreshMap();
	}
	
	public static void save(){
		FileWriter fw;
		if (total > 0) {
			try {
				fw = new FileWriter("save/" + name + ".save");
				fw.write("Player:" + name + "\r\n" + "Level:" + level + "\r\n"
						+ "Step:" + step + "\r\n" + "Total steps:" + total
						+ "\r\n" + "Time:");
				for (int i = 1; i <= 9; i++)
					fw.write(time[i] + ",");
				
				fw.write("\r\nRetreat times:");
				for (int i = 1; i <= 9; i++)
					fw.write(retreatTimes[i] + ",");
				
				fw.write("\r\nScore:");
				for (int i = 1; i <= 9; i++)
					fw.write(score[i] + ",");
				
				int unlockLevel = 0;
				for(unlockLevel = 1; unlockLevel < 9; unlockLevel++) if(!unlock[unlockLevel])break;
				unlockLevel--;
				fw.write("\r\nUnlock Level:" + unlockLevel);
				
				fw.write("\r\n\r\nCurrent Map :\r\n");
				for (int i = 0; i < 16; i++) {
					for (int j = 0; j < 16; j++) {
						fw.write(String.valueOf(map[total].getMapElement(i, j)));
					}
					fw.write("\r\n");
				}
				fw.write("\r\n\r\nLast Map :\r\n");
				for (int i = 0; i < 16; i++) {
					for (int j = 0; j < 16; j++) {
						fw.write(String.valueOf(map[total - 1].getMapElement(i,
								j)));
					}
					fw.write("\r\n");
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else JOptionPane.showMessageDialog(null, "You can't save now !");
	}
	
	public static void load(){//load the saved map
		
		JFileChooser jfc = new JFileChooser("Choose a save file");
		jfc.setCurrentDirectory(new File("save"));
		int result = jfc.showOpenDialog(jf);
		File selectedFile = null;
		jfc.setVisible(true);
		if (result == JFileChooser.APPROVE_OPTION) {
			System.out.println("OK button is pushed.");
			selectedFile = jfc.getSelectedFile();
			String filePath = selectedFile.getAbsolutePath();
			if (selectedFile.exists() && filePath.endsWith(".save")) {
				Scanner input = null;
				String getInput = "";
				try {
					input = new Scanner(selectedFile);
					
					/*get name*/
					getInput = input.nextLine();
					name = getInput.substring(7);
					
					getInput = input.nextLine();
					level = Integer.parseInt(getInput.substring(6));
					
					getInput = input.nextLine();
					step = Integer.parseInt(getInput.substring(5));
					
					getInput = input.nextLine();
					total = Integer.parseInt(getInput.substring(12));
					
					getInput = input.nextLine();
					String[] string = new String[9];
					string = getInput.split(",");
					time[1] = Long.parseLong((string[0].substring(string[0].indexOf(':') + 1)));
					for(int i = 1; i < string.length; i++)time[i + 1] = Long.parseLong(string[i]);
					
					getInput = input.nextLine();
					string = new String[9];
					string = getInput.split(",");
					retreatTimes[1] = Integer.parseInt((string[0].substring(string[0].indexOf(':') + 1)));
					for(int i = 1; i < string.length; i++)retreatTimes[i + 1] = Integer.parseInt(string[i]);
					
					getInput = input.nextLine();
					string = new String[9];
					string = getInput.split(",");
					score[1] = Integer.parseInt((string[0].substring(string[0].indexOf(':') + 1)));
					for(int i = 1; i < string.length; i++)score[i + 1] = Integer.parseInt(string[i]);
					
					getInput = input.nextLine();
					int unlockLevel = Integer.parseInt(getInput.substring(13));
					for(int i = 1; i <= unlockLevel; i++)unlock[i] = true;
					
					getInput = input.nextLine();
					getInput = input.nextLine();
					String row = "";
					map[total] = new Map();
					for (int i = 0; i < 16; i++){
						row = input.next();
						for (int j = 0; j < 16; j++){
							map[total].setMapElement(i, j, row.charAt(j)-48);
						}
					}
					
					getInput = input.nextLine();
					getInput = input.nextLine();
					getInput = input.nextLine();
					getInput = input.nextLine();
					map[total-1] = new Map();
					for (int i = 0; i < 16; i++){
						row = input.next();
						for (int j = 0; j < 16; j++){
							map[total-1].setMapElement(i, j, row.charAt(j)-48);
						}
					}
					input.close();
					
					justLoad = 1;
					loadTime = time[level];
					start = true;
					jf.remove(jpCover);
					map[total].drawMap();
					GUI.jbgGame();
					jmbGame.setVisible(true);
					refreshMap();
					GUI.jlblTime();
					jf.addKeyListener(keyListen);
					jpGame.setLayout(null);
					jf.add(jpGame);
					jf.setVisible(true);
					
					isGaming = true;
					startTime = System.currentTimeMillis();
					timer.schedule(timerTask, 0, 1002);
					timerTask.run();
					
				} catch (FileNotFoundException e) {}
			} else {
				JOptionPane.showMessageDialog(null, "You did not select a right file.");
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("Cancel button is pushed.");
		} else if (result == JFileChooser.ERROR_OPTION) {
			System.err.println("Error when select file.");
		}
		
	}
	
}
	
