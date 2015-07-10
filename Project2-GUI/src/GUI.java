import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GUI extends BoxMover{

	static int lev;
	public static void setFrame(){
		if(Toolkit.getDefaultToolkit().getScreenSize().height == 768
				&& Toolkit.getDefaultToolkit().getScreenSize().width == 1366);
		else JOptionPane.showMessageDialog(null, "推荐设置1366*768屏幕分辩率");
		jf.setUndecorated(true);
		jf.setSize(1366,768);
	//	jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		jmGame();
		
		/*修改分辨率全屏,但由于存在一个小问题所以暂不使用*/
	/*	GraphicsDevice dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		dev.setFullScreenWindow(jf);
		if (dev.isDisplayChangeSupported()) {
			DisplayMode mode = new DisplayMode(1366, 768, 32,DisplayMode.REFRESH_RATE_UNKNOWN);
			dev.setDisplayMode(mode);
		} else {
			JOptionPane.showMessageDialog(null, "无法修改屏幕分辨率至1366*768,故不支持全屏");
		}
		*/
	}
	
	
	/*Windows*/
	public static void setWelcomeWindow(){
		/*设置初始界面*/
		jbtStart();
		jbtContinue_start();
		jbtExit();
		jlblCover();
		jpCover.setLayout(null);
		jf.add(jpCover);
		jf.setVisible(true);
	}
	
	public static void setGameWindow(){
		/*设置游戏界面*/
		map[total].readMap();
		map[total].drawMap();
		jlblGame();
		jlblTime();
		jbgGame();
		jmbGame.setVisible(true);
		jpGame.setLayout(null);
		jf.addKeyListener(keyListen);
		jf.add(jpGame);
		jf.setVisible(true);
		newlevel = true;
		startTime = System.currentTimeMillis();
		if(!start)timer.schedule(timerTask, 0, 1002);
		start = true;
		timerTask.run();
	}
	
	public static void setSelectWindow(){
		/*设置选关界面*/
		jbtSelect();
		jlblSelect();
		jpSelect.setLayout(null);
		jf.add(jpSelect);
		jf.setVisible(true);
	}
	
	public static void setWinWindow(){
		/*设置过关界面*/
		jbtContinue_win();
		jlblWin();
		jpWin.setLayout(null);
		jf.add(jpWin);
		jf.setVisible(true);
	}
	
	public static void setAuthorWindow(){
		/*设置作者界面*/
		jbtBack_author();
		jlblAuthor();
		jpAuthor.setLayout(null);
		jf.add(jpAuthor);
		jf.setVisible(true);
	}
	
	public static void setHelpWindow(){
		/*设置帮助界面*/
		jbtBack_help();
		jlblHelp();
		jpHelp.setLayout(null);
		jf.add(jpHelp);
		jf.setVisible(true);
		
	}
	
	public static void setRankWindow(){
		/*设置排行界面*/
		jbtBack_Rank();
		jlblRank();
		jpRank.setLayout(null);
		jf.add(jpRank);
		jf.setVisible(true);
	}

	
	
	/*Buttons*/
	public static void jbtStart(){
		ImageIcon startIcon = new ImageIcon("pic/start.png");
		ImageIcon startIcon2 = new ImageIcon("pic/start2.png");
		JButton jbtStart = new JButton(startIcon);
		jbtStart.setRolloverIcon(startIcon2);
		jbtStart.setBounds(600, 410, 148, 43);
		jbtStart.setBorder(null);
		jbtStart.setOpaque(true);
		jbtStart.setContentAreaFilled(false);
		jbtStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String a = "";
				while(a.equals("")){
					a = JOptionPane.showInputDialog(null, "输入你的用户名：");
					if(a == null)break;
				}
				if (a != null) {
					name = a;
					jf.remove(jpCover);
					//rank();
					setGameWindow();
					isGaming = true;
					start = true;
					Methods.refreshMap();
				}
			}
		});
		jpCover.add(jbtStart);
	}
	
	public static void jbtContinue_start(){
		ImageIcon continueIcon = new ImageIcon("pic/Continue.png");
		ImageIcon continueIcon2 = new ImageIcon("pic/Continue2.png");
		JButton jbtContinue = new JButton(continueIcon);
		jbtContinue.setRolloverIcon(continueIcon2);
		jbtContinue.setBounds(600, 460, 148, 43);
		jbtContinue.setBorder(null);
		jbtContinue.setOpaque(true);
		jbtContinue.setContentAreaFilled(false);
		jbtContinue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Methods.load();
			}
		});
		jpCover.add(jbtContinue);
	}
	
	public static void jbtExit(){
		ImageIcon exitIcon = new ImageIcon("pic/ex.png");
		ImageIcon exitIcon2 = new ImageIcon("pic/ex2.png");
		JButton jbtExit = new JButton(exitIcon);
		jbtExit.setRolloverIcon(exitIcon2);
		jbtExit.setBounds(600, 510, 148, 43);
		jbtExit.setBorder(null);
		jbtExit.setOpaque(true);
		jbtExit.setContentAreaFilled(false);
		jbtExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		jpCover.add(jbtExit);
	}
	
	public static void jbtSelect(){
		unlock[0] = true;
		ImageIcon[] selectIcon_t = new ImageIcon[9];
		ImageIcon[] selectIcon_f = new ImageIcon[9];
		JButton[] jbtSelect = new JButton[9];
		for(int i = 0; i < 9; i++){
			/*initialize icons*/
			int num = i + 1;
			selectIcon_t[i] = new ImageIcon("pic/l"+num+"t.png");
			selectIcon_f[i] = new ImageIcon("pic/l"+num+"f.png");

			/*add icons to buttons*/
			if(unlock[i])jbtSelect[i] = new JButton(selectIcon_t[i]);
			else jbtSelect[i] = new JButton(selectIcon_f[i]);
			
			/*listeners*/
			lev = i;
			if (unlock[i]) {
				jbtSelect[i].addActionListener(new ActionListener() {
					final int i = lev;

					public void actionPerformed(ActionEvent e) {
						loadTime = 0;
						newlevel = true;
						jf.remove(jpSelect);
						level = i + 1;
						map[total].readMap();
						if (start)
							go_on();
						setGameWindow();
						isGaming = true;

						Methods.refreshMap();
						jpSelect = new JPanel();
						jf.requestFocus();
					}
				});
			}
			/*set locations*/
			jbtSelect[i].setBounds(60 + 120 * (i % 3), 60 + 120 * (int)(i / 3), 100, 100);
			jbtSelect[i].setBorder(null);
			jbtSelect[i].setOpaque(true);
			jbtSelect[i].setContentAreaFilled(false);
			/*add buttons*/
			jpSelect.add(jbtSelect[i]);
		}
	}
	
	public static void jbtContinue_win(){
		ImageIcon continueIcon = new ImageIcon("pic/continues.png");
		ImageIcon continueIcon2 = new ImageIcon("pic/continues2.png");
		JButton jbtContinue = new JButton(continueIcon);
		jbtContinue.setRolloverIcon(continueIcon2);
		jbtContinue.setBounds(613, 654, 148, 43);
		jbtContinue.setBorder(null);
		jbtContinue.setOpaque(true);
		jbtContinue.setContentAreaFilled(false);
		jbtContinue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jf.remove(jpWin);
				jpWin = new JPanel();
				jf.addKeyListener(keyListen);
				jf.requestFocus();
				jpGame.setVisible(true);
				jmbGame.setVisible(true);
				isGaming = true;
				newlevel = true;
				startTime = System.currentTimeMillis();
				timerTask.run();
			}
		});
		jpWin.add(jbtContinue);
	}
	
	public static void jbtBack_help(){
		ImageIcon backIcon = new ImageIcon("pic/continues.png");
		ImageIcon backIcon2 = new ImageIcon("pic/continues2.png");
		JButton jbtBack = new JButton(backIcon);
		jbtBack.setRolloverIcon(backIcon2);
		jbtBack.setBounds(170, 650, 148, 43);
		jbtBack.setBorder(null);
		jbtBack.setOpaque(true);
		jbtBack.setContentAreaFilled(false);
		jbtBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jf.remove(jpHelp);
				jpGame.setVisible(true);
				jmbGame.setVisible(true);
				jf.requestFocus();
				jf.addKeyListener(keyListen);
				go_on();
				jf.setVisible(true);
			}
		});
		jpHelp.add(jbtBack);
	}
	
	public static void jbtBack_author(){
		ImageIcon backIcon = new ImageIcon("pic/continues.png");
		ImageIcon backIcon2 = new ImageIcon("pic/continues2.png");
		JButton jbtBack = new JButton(backIcon);
		jbtBack.setRolloverIcon(backIcon2);
		jbtBack.setBounds(875, 600, 148, 43);
		jbtBack.setBorder(null);
		jbtBack.setOpaque(true);
		jbtBack.setContentAreaFilled(false);
		jbtBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jf.remove(jpAuthor);
				jpGame.setVisible(true);
				jmbGame.setVisible(true);
				jf.requestFocus();
				jf.addKeyListener(keyListen);
				go_on();
				jf.setVisible(true);
			}
		});
		jpAuthor.add(jbtBack);
	}
	
	public static void jbtBack_Rank(){
		ImageIcon rankIcon = new ImageIcon("pic/continues.png");
		ImageIcon rankIcon2 = new ImageIcon("pic/continues2.png");
		JButton jbtRank = new JButton(rankIcon);
		jbtRank.setBounds(1000, 500, 148, 43);
		jbtRank.setRolloverIcon(rankIcon2);
		jbtRank.setBorderPainted(false);
		jbtRank.setOpaque(false);
		jbtRank.setContentAreaFilled(false);
		jbtRank.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (start) {
					jf.remove(jpRank);
					jpGame.setVisible(true);
					jmbGame.setVisible(true);
					jf.requestFocus();
					jf.addKeyListener(keyListen);
					go_on();
					jf.setVisible(true);
				}
				else{
					jf.remove(jpRank);
					setSelectWindow();
				}
			}
		});
		jpRank.add(jbtRank);
	}
	
	
	
	
	/*Labels*/
	public static void jlblCover(){
		ImageIcon coverIcon = new ImageIcon("pic/Cover.png");
		JLabel coverPic = new JLabel(coverIcon);
		coverPic.setBounds(0, 0, 1366, 768);
		jpCover.add(coverPic);
	}
	
	public static void jlblSelect(){
		ImageIcon selectIcon = new ImageIcon("pic/Select.png");
		JLabel selectPic = new JLabel(selectIcon);
		selectPic.setBounds(0, 0, 1366, 768);
		jpSelect.add(selectPic);
	}
	
	public static void jlblWin(){
		ImageIcon selectIcon = new ImageIcon("pic/Win.png");
		JLabel winPic = new JLabel(selectIcon);
		winPic.setBounds(0, 0, 1366, 768);
		jpWin.add(winPic);
	}
	
	public static void jtfWin(){
		int a = 0;
		for(int i = 0; i <= 9; i++){
			a += score[i];
		}
		jlbWin[0] = new JLabel("" + name);
		jlbWin[1] = new JLabel("第" + level + "关");
		jlbWin[2] = new JLabel("" + step + "步");
		jlbWin[3] = new JLabel("" + retreatTimes[level] + "步");
		jlbWin[4] = new JLabel("" + (int)(time[level]/1000) + "秒");
		jlbWin[5] = new JLabel("" + score[level] + "分");
		jlbWin[6] = new JLabel("" + a + "分");
		JLabel t = new JLabel("总分" + "    " + a + "分");
		for(int i = 0; i <= 5; i++){
			jlbWin[i].setBounds(740, 100 + 86 * i, 400, 100 );
			jlbWin[i].setFont(mw.deriveFont(Font.BOLD, 60));
			jlbWin[i].setForeground(new Color(231,56,227));
			jpWin.add(jlbWin[i]);
		}
		t.setBounds(900,600,800,100);
		t.setFont(mw.deriveFont(Font.BOLD, 60));
		jpWin.add(t);
	}
	
	public static void jlblGame(){
		for(int j = 0; j < 16; j++){
			for(int i = 0; i < 16; i++){
				jlbGame[i][j] = new JLabel(elementIcon[map[total].getMapElement(i, j)]);
				jlbGame[i][j].setBounds(299 + j * 48, i * 48, 48, 48);
				jlbGame[i][j].setBorder(null);
				jlbGame[i][j].setOpaque(true);
				jlbGame[i][j].setBackground(null);;
				jpGame.add(jlbGame[i][j]);
			}
		}
	}
	
	public static void jbgGame(){
		for(int i = 1; i < 10; i++){
			backgroundIcon[i] = new ImageIcon("pic/n0" + i + ".png");
			jbgGame[i] = new JLabel(backgroundIcon[i]);
			jbgGame[i].setBounds(0, 0, 1366, 768);
		}
	}
	
	public static void jlblTime(){
		ImageIcon timeIcon = new ImageIcon("pic/Time.png");
		jlblTime = new JLabel(timeIcon);
		jlblTime.setOpaque(false);
		jlblTime.setBounds(450, 40, 400, 100);
		jpGame.add(jlblTime);
	}
	
	public static void jlblShowTime() {
		jlblShowTime = new JLabel("00:00:00");
		jlblShowTime.setBounds(640, 43, 400, 100);
		jlblShowTime.setOpaque(false);
		jpGame.add(jlblShowTime);
	}
	
	public static void jlblHelp(){
		ImageIcon helpIcon = new ImageIcon("pic/Help.png");
		JLabel helpPic = new JLabel(helpIcon);
		helpPic.setBounds(0, 0, 1366, 768);
		jpHelp.add(helpPic);
	}
	
	public static void jlblAuthor(){
		ImageIcon authorIcon = new ImageIcon("pic/Author.png");
		JLabel authorPic = new JLabel(authorIcon);
		authorPic.setBounds(0, 0, 1366, 768);
		jpAuthor.add(authorPic);
	}
	
	public static void jlblRank(){
		ImageIcon rankIcon = new ImageIcon("pic/Rank.png");
		JLabel rankPic = new JLabel(rankIcon);
		rankPic.setBounds(0, 0, 1366, 768);
		jpRank.add(rankPic);
	}
	
	
	
	
	
	/*Menu*/
	public static void jmGame(){
		/*Define and add*/
		jmbGame = new JMenuBar();
		jmbGame.setBackground(new Color(178,189,187));
		jmbGame.setForeground(new Color(178,189,187));
		JMenu jmGame =new JMenu("游戏");
		JMenu jmHelp =new JMenu("帮助");
		JMenu jmRank =new JMenu("排行");
		
		JMenuItem[] jmiGame = {new JMenuItem("重新开始"), new JMenuItem("选择关卡"), 
								new JMenuItem("保存"), new JMenuItem("退出")};
		JMenuItem[] jmiHelp = {new JMenuItem("说明"), new JMenuItem("关于")};
		JMenuItem jmiRank = new JMenuItem("排行");
		
		jf.setJMenuBar(jmbGame);
		jmbGame.add(jmGame);
		jmbGame.add(jmHelp);
		jmbGame.add(jmRank);
		
		
		for(int i = 0; i < 4; i++){
			jmGame.add(jmiGame[i]);
			if(i == 1 || i == 2) jmGame.addSeparator();
		}
		for(int i = 0; i < 2; i++) jmHelp.add(jmiHelp[i]);
		jmRank.add(jmiRank);
		
		/*Functions*/
		jmiGame[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Methods.restart();
			}
		});
		jmiGame[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jf.remove(jpGame);
				jf.removeKeyListener(keyListen);
				jmbGame.setVisible(false);
				pause();
				setSelectWindow();
			}
		});
		jmiGame[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Methods.save();
				
			}
		});
		jmiGame[3].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		jmiHelp[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jf.removeKeyListener(keyListen);
				jpGame.setVisible(false);
				jmbGame.setVisible(false);
				pause();
				setHelpWindow();
			}
		});
		jmiHelp[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jf.removeKeyListener(keyListen);
				jpGame.setVisible(false);		
				jmbGame.setVisible(false);
				pause();
				setAuthorWindow();
			}
		});
		
		jmiRank.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				rank();
				jf.removeKeyListener(keyListen);
				jpGame.setVisible(false);		
				jmbGame.setVisible(false);
				pause();
				setRankWindow();
			}
		});
		jmbGame.setVisible(false);
	}

	public static void rank() 
	{		
		/*get information*/
		File[] file = new File("save/").listFiles();
		Scanner scanner = null;
		String scores = "";
		int[] totalScore = new int[file.length + 1];
		String[] names = new String[file.length + 1];
		names[0] = "None";
		String[] string = null;
		for (int i = 0; i < file.length; i++)
		{
			try 
			{
				scanner = new Scanner(file[i]);
				names[i + 1] = scanner.nextLine().substring(7);
				for (int j = 0; j < 6; j++)
				{
					scores = scanner.nextLine();
				}
				System.out.println(scores);
				string = new String[9];
				string = scores.split(",");
				totalScore[i + 1] += Integer.parseInt((string[0].substring(string[0].indexOf(':') + 1)));
				for (int j = 1; j < string.length; j++)
				{
					totalScore[i + 1] += Integer.parseInt(string[j]);
				}
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		
		/*sort*/
		int first = 0, second = 0, third = 0;
		for (int i = 1; i <= file.length; i++)
		{
			if (totalScore[i] > totalScore[first]) 
			{
				third = second;
				second = first;
				first = i;
			}
			else if (totalScore[i] > totalScore[second]) 
			{
				third = second;
				second = i; 
			}
			else if (totalScore[i] > totalScore[third])
			{
				third = i;
			}
		}
				
		/*GUI*/
		jpRank = new JPanel();
		jlblRank = new JLabel[6];
		jlblRank[0] = new JLabel(names[first]);
		jlblRank[1] = new JLabel("" + totalScore[first]);
		jlblRank[2] = new JLabel(names[second]);
		jlblRank[3] = new JLabel("" + totalScore[second]);
		jlblRank[4] = new JLabel(names[third]);
		jlblRank[5] = new JLabel("" + totalScore[third]);
		for(int i = 0; i < 6; i++){
			jlblRank[i].setFont(mw);
			jlblRank[i].setForeground(new Color(90,31,210));
			jlblRank[i].setBounds(1050 + 180 * (i % 2), 210 + 102 * (int)(i / 2), 150, 50);
			jpRank.add(jlblRank[i]);
		}
		jbtBack_Rank();
		jlblRank();
		
		
	}


	public static void pause(){
		isGaming = false;
		pauseTime1 = System.currentTimeMillis();
	}
	
	public static void go_on(){
		isGaming = true;
		pauseTime2 += System.currentTimeMillis() - pauseTime1;
	}

}


class KeyListen extends BoxMover implements KeyListener{
	/*keyListener*/
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP : System.out.println("up");Methods.up();break;
		case KeyEvent.VK_DOWN : System.out.println("down");Methods.down();break;
		case KeyEvent.VK_LEFT : System.out.println("left");Methods.left();break;
		case KeyEvent.VK_RIGHT : System.out.println("right");Methods.right();break;
		case KeyEvent.VK_B : System.out.println("back");Methods.retreat();break;
		}
		Methods.win_judge();
	}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	
}
