import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class Methods extends BoxMover{
	static boolean quick, win, toDraw = true,dead_1, dead_2;//define boolean types. They are: whether it's quick move, whether it wins, whether it need to draw map and whether it dies

	public void do_order(String input) {
		toDraw = true;quick = false;//initialize boolean types at first
		if (input.equalsIgnoreCase("w"))up();//tell the input order
		else if (input.equalsIgnoreCase("a"))left();
		else if (input.equalsIgnoreCase("s"))down();
		else if (input.equalsIgnoreCase("d"))right();
		else if (input.equalsIgnoreCase("h"))help();
		else if (input.equalsIgnoreCase("r"))restart();
		else if (input.equalsIgnoreCase("e"))exit();
		else if (input.equalsIgnoreCase("f"))share();
		else if (input.equalsIgnoreCase("v"))save();
		else if (input.equalsIgnoreCase("l"))load();
		else if (isNumber(input))changeLevel(input);
		else if (isRetreat(input))retreat(input);
		else if (isQuick(input))quick(input);//quick move
		else if (input.equalsIgnoreCase("answer"))//show correct moves automatically
			if(step == 0)showAnswer();
			else System.out.println("此功能只能在初始状态时使用，请重新开始或撤消至第一步以使用此功能\n千万不要以为这是AI。。。。。。");
		else illegal_input();
	}
	public static void showResult(){//Three kinds of possible result
		if(quick);
		else if(toDraw) map[total].drawMap();
		else System.out.println("撞墙啦！！！");
	}
	public void judge(){
		win_judge();
		dead_1();
		dead_2();
	}
	public static String input(){//get the input
		return new Scanner(System.in).next();
	}
	public static void exit(){
		System.out.println("拜拜~");
		System.exit(0);
	} 
	public static void share(){
		System.out.println("分享到人人微博？骗你的，这也信啊=_=||太天真了少年！！图样图森破！！！");
	}
	public static void illegal_input(){
		System.out.println("不好意思你输入了非法字符→_→这程序太蠢看不懂");
	}
	public static boolean isNumber(String input){//tell whether input means choose level
		boolean is_number = false;
		if (input.length() == 1 && input.charAt(0) < 58 && input.charAt(0) > 48) is_number = true;
		return is_number;
	}
	public static boolean isRetreat(String input){//tell whether input means retreat
		boolean is_back = false,is_number = true;
		if(input.charAt(0) != 'b' && input.charAt(0) != 'B')return false;
		for(int i = 1; i < input.length(); i++){
			if(!(isNumber(String.valueOf(input.charAt(i))) || input.charAt(i) == 48)) is_number = false;
		}
		if(is_number) is_back = true;
		return is_back;
	}
	public static void retreat(String input){//retreat
		int retreatTimes = 0;
		if(input.length() == 1 && step > 0){//retreat one step
			map[total].setMap(new int [11][11]);
			total--;
			step--;
		}
		else if(input.length() == 1 && step == 0)System.out.println("不能再退啦！");//can't retreat one step
		else{//retreat many steps
			for(int i = input.length()-1; i >= 1; i--){
				retreatTimes += (input.charAt(i)-48)*Math.pow(10, input.length()-i-1);//find the steps to retreat
			}
			if(retreatTimes > step)System.out.println("退不了那么多步！");//can't retreat so many steps
			else {
				for(int i = total - retreatTimes +1; i >= total ;i++){
					map[i].setMap(new int[11][11]); //delete steps that have been retreated
				}
				total -= retreatTimes; step -= retreatTimes;//change total steps and current step
			}
		}
	}
	public static int leave(int i,int j){//it is used to decide whether the man is stand on empty ground or the terminal of boxes
		if(step == 0||step == 1)return 2;
		else {
			if(map[total-1].getMapElement(i, j) == 9 || map[total-1].getMapElement(i, j) == 4)return 4;
		//	if(map[total-1].getMapElement(j, width-i-1) == 9 || map[total-1].getMapElement(j, width-i-1) == 4)return 4;//use in the function "reverse map",but it can't add score so I abandoned it. 
			else return 2;
		}
	}
	public static void left(){//go left. it's too complex to explain so I don't intend to explain. What's more, I believe you don't want to check the detail,right?
		map[total].findPos();
		int i = map[total].getplayerX(), j = map[total].getplayerY();
 		int Map[][] = copymap(map[total].getMap());
 		boolean fail = false;
	a:	switch(Map[i][j-1]){
			case 2 : Map[i][j-1] = 5; Map[i][j] = leave(i,j);break;
			case 3 :	{switch(Map[i][j-2]){
							case 2 : Map[i][j] = leave(i,j);Map[i][j-1] = 5;Map[i][j-2] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i][j-1] = 5;Map[i][j-2] = 9;break a;							
							default : total--;step--;toDraw = false;fail = true;break a;}
						}
			case 4 :	Map[i][j] = leave(i,j);Map[i][j-1] = 5;break;
			case 9 :	{switch(Map[i][j-2]){
							case 2 : Map[i][j] = leave(i,j);Map[i][j-1] = 5;Map[i][j-2] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i][j-1] = 5;Map[i][j-2] = 9;break a;	
							default : total--;step--;toDraw = false;fail = true;break a;}
						}
			default : total--;step--;toDraw = false;fail = true;break a;
			}
			total++;step++;
			if(!fail)map[total] = new Map();
			map[total].setMap(Map);
	}
	public static void right(){
		map[total].findPos();
		int i = map[total].getplayerX(), j = map[total].getplayerY();
		int Map[][] = copymap(map[total].getMap());
 		boolean fail = false;
	a:	switch(Map[i][j+1]){
			case 2 : Map[i][j+1] = 5; Map[i][j] = leave(i,j);break;
			case 3 :	{switch(Map[i][j+2]){
							case 2 : Map[i][j] = leave(i,j);Map[i][j+1] = 5;Map[i][j+2] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i][j+1] = 5;Map[i][j+2] = 9;break a;							
							default : total--;step--;toDraw = false;fail = true;break a;}
						}
			case 4 :	Map[i][j] = leave(i,j);Map[i][j+1] = 5;break;
			case 9 :	{switch(Map[i][j+2]){
							case 2 : Map[i][j] = leave(i,j);Map[i][j+1] = 5;Map[i][j+2] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i][j+1] = 5;Map[i][j+2] = 9;break a;	
							default : total--;step--;toDraw = false;fail = true;break a;}
						}
			default : total--;step--;toDraw = false;fail = true;break a;
			}
			total++;step++;
			if(!fail)map[total] = new Map();
			map[total].setMap(Map);
	}
	public static void down(){
		map[total].findPos();
		int i = map[total].getplayerX(), j = map[total].getplayerY();
		int Map[][] = copymap(map[total].getMap());
 		boolean fail = false;
	a:	switch(Map[i+1][j]){
			case 2 : Map[i+1][j] = 5; Map[i][j] = leave(i,j);break;
			case 3 :	{switch(Map[i+2][j]){
							case 2 : Map[i][j] = leave(i,j);Map[i+1][j] = 5;Map[i+2][j] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i+1][j] = 5;Map[i+2][j] = 9;break a;							
							default : total--;step--;toDraw = false;fail = true;break a;}
						}
			case 4 :	Map[i][j] = leave(i,j);Map[i+1][j] = 5;break;
			case 9 :	{switch(Map[i+2][j]){
							case 2 : Map[i][j] = leave(i,j);Map[i+1][j] = 5;Map[i+2][j] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i+1][j] = 5;Map[i+2][j] = 9;break a;	
							default : total--;step--;toDraw = false;fail = true;break a;}
						}
			default : total--;step--;toDraw = false;fail = true;break a;
			}
			total++;step++;
			if(!fail)map[total] = new Map();
			map[total].setMap(Map);
	}
	public static void up(){
		map[total].findPos();
		int i = map[total].getplayerX(), j = map[total].getplayerY();
		int Map[][] = copymap(map[total].getMap());
 		boolean fail = false;
	a:	switch(Map[i-1][j]){
			case 2 : Map[i-1][j] = 5; Map[i][j] = leave(i,j);break;
			case 3 :	{switch(Map[i-2][j]){
							case 2 : Map[i][j] = leave(i,j);Map[i-1][j] = 5;Map[i-2][j] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i-1][j] = 5;Map[i-2][j] = 9;break a;							
							default : total--;step--;toDraw = false;fail = true;break a;}
						}
			case 4 :	Map[i][j] = leave(i,j);Map[i-1][j] = 5;break;
			case 9 :	{switch(Map[i-2][j]){
							case 2 : Map[i][j] = leave(i,j);Map[i-1][j] = 5;Map[i-2][j] = 3;break a;
							case 4 : Map[i][j] = leave(i,j);Map[i-1][j] = 5;Map[i-2][j] = 9;break a;	
							default : total--;step--;toDraw = false;fail = true;break a;}
						}
			default : total--;step--;toDraw = false;fail = true;break a;
			}
			total++;step++;
			if(!fail)map[total] = new Map();
			map[total].setMap(Map);
	}
	public static void restart(){//restart : change several values
		step = 0;
		total++;
		map[total] = new Map();
		map[total].readMap();
	}
	public static void save(){//save the map to save.map
		FileWriter fw;
		try {
			fw = new FileWriter(".\\save.map");
			fw.write(height + " " + width + " " + level + "\r\n");
			for (int i = 0; i < width; i++){
				for (int j = 0; j < height; j++){
					fw.write(String.valueOf(map[total].getMapElement(i, j)));
				}
				fw.write("\r\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void load(){//load the saved map
		String level_S = ".\\save.map";
		Scanner input = null;
		try{
		input = new Scanner(new File(level_S));}
		catch (Exception e){
			System.out.println("没有存档！");
			System.exit(-1);
		}
		total++;step = 0;
		map[total] = new Map();
		height = Integer.parseInt(input.next());
		width = Integer.parseInt(input.next());
		level = Integer.parseInt(input.next());
		String row = null;
		for (int i = 0; i < width; i++){
			row = input.next();
			for (int j = 0; j < height; j++){
				map[total].setMapElement(i, j, row.charAt(j)-48);
			}
		}
		input.close();
	}
	public void changeLevel(String input){//change several values
		level = Integer.parseInt(input);
		step = 0;
		total++;
		map[total] = new Map();
		map[total].readMap();
	}
	public static void first_words(){//first words
		System.out.println("这是无比丑陋无比二的推箱子游戏=_=||\n不管长得怎么样我们还是愉快地开始吧~~。。。\n"
				+ "\t\t好的~首先我们来详细说明一下如何操作吧！\n\t\t输入1-9选择关卡~~~\n\t\t↑W表示向上 ↑\t↓S表示向下 ↓\n"
				+ "\t\t←A表示向左 ←\t→D表示向右 →\n\t\tR是重新开始本关    H是显示操作帮助    \n"
				+ "\t\tB表示悔棋一步      B+“数字n”表示悔棋n步（中间不加空格）    \n\t\tV是存档    L是读档    E是退出游戏\n\n"
				+ "\t为了方便操作，游戏不区分大小写哟~！\n输入'-'+w/a/s/d的组合可以连续移动！例如输入\"-wsaadsswdd\"就可以直接通过第一关~\n"
				+ "【作弊大法】输入answer可以自动推箱子直到过关。\n\n（偷偷告诉你！输入F可以分享游戏到人人或者微博哦！^_^）");
	}
	public static void help(){
		System.out.println("我们再来重复一遍规则~\n\n输入1-9选择关卡~~~\n↑W表示向上 ↑\t↓S表示向下 ↓\n←A表示向左 ←"
				+ "\t→D表示向右 →\n\nR表示重新开始    E表示退出游戏    \t\nB表示悔棋一步    B+“数字n”表示悔棋n步    \n"
				+ "V是存档    L是读档    \n\t（什么你问H去哪了？很简单，你既然能调出帮助必然知道H是干什么的。。。。)\n");
	}
	public static int[][] copymap(int map[][]){//a method to copy map
		int Map[][] = new int [width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				Map[i][j] = map[i][j];
			}
		}
		return Map;
	}
	public void win_judge(){//win judge
		win = true;
		for(int i = 0;i < width; i++){
			for(int j = 0; j < height; j++){
				if(map[total].getMapElement(i, j) == 3)win = false;
			}
		}
		if(win && level < 9){
			System.out.println("\n恭喜过关！\n下面进入第"+(++level)+"关！");
			timer(1500);
			total++;step =0;
			map[total] = new Map();
			map[total].readMap();
			map[total].drawMap();
		}
		else if(win && level ==9){
			toDraw = false;
			System.out.println("恭喜你通关了！");
		}
	}
	public void dead_1(){//check one box after another to tell whether it's dead_1
		dead_1 = true;
	d1:	for(int i = 1;i < width-1; i++){
			for(int j = 1; j < height-1;j++){
				if(map[total].getMapElement(i, j) == 3||map[total].getMapElement(i, j) == 9){
					if(!around(i,j)){
						dead_1 = false;
						break d1;
					}
				}
			}
		}
		if(dead_1 == true){
			System.out.println("你已经陷入死局1啦！悔棋或者重来吧！");
		}
	}
	public boolean around(int i,int j){//check the elements around to know whether a box can be moved
		int up = map[total].getMapElement(i-1, j);
		int down = map[total].getMapElement(i+1, j);
		int left = map[total].getMapElement(i, j-1);
		int right = map[total].getMapElement(i, j+1);
		if(((up == 3||up == 9||up == 1)&&(right == 3||right == 9||right == 1))||
				((up == 3||up == 9||up == 1)&&(left == 3||left == 9||left == 1))||
				((down == 3||down == 9||down == 1)&&(right == 3||right == 9||right == 1))||
				((down == 3||down == 9||down == 1)&&(left == 3||left == 9||left == 1))){
			return true;
		}
		else return false;
	}
	public void dead_2(){
		dead_2 = false;
		int[][] copymap = new int[width][height];
		int[][] copymap2 = new int[width][height];
		map[total].findPos();
		int playerX = map[total].getplayerX(),playerY = map[total].getplayerY();
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				copymap[i][j] = map[total].getMapElement(i, j);
				if((copymap[i][j] == 3 ||copymap[i][j] == 9) &&  around(i,j))copymap[i][j] = 1;
				if(copymap[i][j] == 4 || copymap[i][j] == 5 || (copymap[i][j] == 9 && around(i,j) == false))copymap[i][j] = 2;
			}
		}
	d2:	for (int i = 1; i < width-1; i++){
			for (int j = 1; j < height-1; j++){
				if(copymap[i][j] == 3){
					copymap2 = copymap(copymap);
					reach(playerX,playerY,copymap2);
					if(copymap2[i][j] != 7){dead_2 = true;break d2;}
					copymap[i][j] = 2;
				}
			}
		}
		if(dead_2)System.out.println("你已经陷入死局2啦！悔棋或者重来吧！");
	}
	public static void reach(int i,int j,int reachmap[][]){
		reachmap[i][j] = 7;
		if(reachmap[i][j-1] == 2||reachmap[i][j-1] == 3)reach(i,j-1,reachmap);
		if(reachmap[i][j+1] == 2||reachmap[i][j+1] == 3)reach(i,j+1,reachmap);
		if(reachmap[i-1][j] == 2||reachmap[i-1][j] == 3)reach(i-1,j,reachmap);
		if(reachmap[i+1][j] == 2||reachmap[i+1][j] == 3)reach(i+1,j,reachmap);
	}
	public static boolean isQuick(String input){//quick move
		if(input.charAt(0) == '-')return true;
		else return false;
	}
	public static void quick(String input){
		quick = true;
		for(int i = 1; i < input.length(); i++){
			if(input.charAt(i) == 'w'||input.charAt(i) == 'W'){up();timer(100);map[total].drawMap();}
			if(input.charAt(i) == 'a'||input.charAt(i) == 'A'){left();timer(100);map[total].drawMap();}
			if(input.charAt(i) == 's'||input.charAt(i) == 'S'){down();timer(100);map[total].drawMap();}
			if(input.charAt(i) == 'd'||input.charAt(i) == 'D'){right();timer(100);map[total].drawMap();}
		}
	}
	public static void timer(long time){
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		while(end-start < time){
			end = System.currentTimeMillis();
		}
	}
	public static void showAnswer(){//It's all the answers of the 9 maps!!!
		switch(level){
			case 1: quick("-wsaadsswdd");break;
			case 2: quick("-dsdsdssaaddwwasadwwasswwwwdsssasdwds");break;
			case 3: quick("-aawawaassdwwawdssddsddwaaawaassdwawdssaaaawdddsdwwsaaawwwdwdddwddsssswwwwaasaaasasssdddddsddwaaaasaawdsdwaaawwwdwwasssswwwdddddsdsssaaaaasaawddsdwwsaawwwwdddddwdsssswwwaaaaaassssdddddsddwaaaasaw");break;
			case 4: quick("-asasssddwwssaawdwddsawaawwddssaaaawdsdddwwaaswddssaassddwsaawwwaasd");break;
			case 5: quick("-awaawsddwawwdwasssddwsassddwddwwaaddssaasaawwawwwdddsawaasssddwwdwasssaasaawdsdwwwssaawdsdwssdsddwddwwaaddssaasaawwdwwsdddssawdwaadssasaawwdw");break;
			case 6: quick("-awwwaawaaawwdddddssssdssaawaasawwwssdddsddwwawaaadddsdssaawwdwaadsssddwasawwdwadwwwaaswas");break;
			case 7: quick("-ddwwwwssdddwwwdddwwaaaswdddssaaasawdsssaaassaawwddddsdwwwwwsddssdssawaaaaassaawwddddsdwwwwddssdsaaaaaassaawwddddsdwwwssaaawwawwdsdasssaassddwsaawwddddsdww");break;
			case 8: quick("-sddddddwwaaaswdddssaaaaaawwdsdaasdwdsssdsasawdwwwddddwwaaaswdddssaaawassssasdwwwwddddwwasdssaaawassssassddwawwwwwdwdsdsaawasssssdsawwwwwwaawaassdddwdsssdsawwwaaaawddwdsasdwdssss");break;
			case 9: quick("-wdwwdddddsssaawaaddssddwwwaaaaaasassdwawdwddddddsssaawaaadddsddwadwwassdsawwwaaaaaasassdwdddddwdswwaaaaaasasddddddwwaaswaaassddddawwddssaaaaawwdsasddd");break;
			default : break;
		}
	}
	public static void reverse(){//ABANDONED  reverse the map for every move 
		total++;
		height = height + width; width = height - width; height = height - width;//change the value of height and width  
		map[total] = new Map();
		map[total].clearMap(width, height);
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				map[total].setMapElement(i, j, map[total-1].getMapElement(height-j-1, i));
			}
		}
		map[total-1] = new Map();
		map[total-1].clearMap(width, height);
		map[total-1].setMap(map[total].getMap());
		map[total] = new Map();
		total--;
	}
}