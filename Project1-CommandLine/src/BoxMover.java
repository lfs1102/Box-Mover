public class BoxMover {
	static int level = 1,step,total,height,width;//define current level,current step,total steps and height and width of the map.
	static Map map[] = new Map[9999];//define a map object array,and instantiate one by one when needed.
	static Methods methods = new Methods();//define the methods object.
	
	public static void main(String[] args) {
		methods.first_words();//print the words need to say when the program starts.
		map[0] = new Map();map[0].readMap();map[0].drawMap();//instantiate the first map object and print it.
		while(true){//it's the keep loop;run one loop only when there is an input.
			methods.do_order(methods.input());//tell the order and calculate.
			methods.showResult();//show the result after calculation.
			methods.judge();//tell whether it wins or dies;
		}
	}
}