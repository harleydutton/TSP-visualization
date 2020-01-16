
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;

public class City2 {
	double x, y;
	int id = 0;
	static int totalCities = 0;
	public City2(){
		x = Math.random();
		y = Math.random();
		id = totalCities;
		totalCities++;
	}
	public double dist(City2 other){
		return Math.sqrt(Math.pow(this.x-other.x,2)+Math.pow(this.y-other.y, 2));
	}
	public String toString(){
		return id+":("+x+","+y+")";
	}
	public static double tourLength(ArrayList<City2> path){
		double out = 0.0;
		for(int i = 0 ; i < path.size()-1; i++){
			out+=path.get(i).dist(path.get(i+1));
		}
		out+=path.get(0).dist(path.get(path.size()-1));
		return out;
	}
	public static void main(String[] args){
		int num = 30;
		City2[] country = new City2[num];
		for(int i = 0 ; i < num; i++){
			country[i]=new City2();
		}
		//greedy. seems like a good place to start.
		//make a copy of country for a destructive read.
		ArrayList<City2> greedyChoices = new ArrayList<City2>();
		for(City2 c:country){
			greedyChoices.add(c);
		}
		//loop through greedy choices. add the closest one to the start of the path to the start of the path and remove it from choices.
		ArrayList<City2> greedyPath = new ArrayList<City2>();
		greedyPath.add(greedyChoices.remove(0));
		while(greedyChoices.size()>0){
			int dex = 0;
			for(int i = 0 ; i < greedyChoices.size(); i++){
				if(greedyChoices.get(i).dist(greedyPath.get(0))<greedyChoices.get(dex).dist(greedyPath.get(0)))
					{dex=i;}
			}
			greedyPath.add(0,greedyChoices.remove(dex));
		}
		//make a frame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//load the greedy path data
		frame.getContentPane().add(new MyPanel(greedyPath));
		frame.pack();
		//testing Tour class for bugs
		Tour t = new Tour(greedyPath);
		System.out.println(t.tourLength()+"");
		//check for swap2opts and/or make a method for it.
	}
}
class MyPanel extends JPanel{
	ArrayList<City2> drawpath;
	static int buf = 50;
	static int size = 800;
	public MyPanel(ArrayList<City2> blah){
		drawpath = blah;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		
		for(City2 c : drawpath){//draw cities
			g.drawOval((int)(c.x*size)+buf-3,(int)(size-c.y*size)+buf-3, 6, 6);
			String s = c.id+"";
			g.drawChars(s.toCharArray(),0,s.length(),(int)(c.x*size)+5+buf,(int)(size-c.y*size)+buf);
		}
		
		for(int i = 0; i < drawpath.size()-1; i++){//draw paths based on order. do the last one manually.
			g.drawLine((int)(drawpath.get(i).x*size)+buf,
					(int)(size-drawpath.get(i).y*size)+buf,
					(int)(drawpath.get(i+1).x*size)+buf,
					(int)(size-drawpath.get(i+1).y*size)+buf);
		}
		g.drawLine((int)(drawpath.get(0).x*size)+buf,
				(int)(size-drawpath.get(0).y*size)+buf,
				(int)(drawpath.get(drawpath.size()-1).x*size)+buf,
				(int)(size-drawpath.get(drawpath.size()-1).y*size)+buf);
		
		String str = ""+City2.tourLength(drawpath);
		g.drawChars(str.toCharArray(), 0, str.length(), buf/2, buf/2);
		g.setColor(Color.RED);//display bounds
		g.drawRect(0+buf,0+buf,size,size);
	}
	public Dimension getPreferredSize(){
		return new Dimension(size+(2*buf),size+(2*buf));
	}
}
class Tour{
	public ArrayList<City2> cities;
	public Tour(){
		cities = new ArrayList<City2>();
	}
	public Tour(ArrayList<City2> blah){
		cities = blah;
	}
	public Tour rev(Tour t){//reverse the order of the tour
		Tour out = new Tour();
		for(City2 c : t.cities){
			out.cities.add(0,c);
		}
		return out;
	}
	public double tourLength(){
		double out = 0;
		for(int i = 0 ; i < cities.size()-1; i++){
			out+=cities.get(i).dist(cities.get(i+1));
		}
		out+=cities.get(0).dist(cities.get(cities.size()-1));
		return out;
	}
	//quarter the tour into 4 smaller tours. ez pz
	//left shift and right shift
	//combining tours. ez pzer
}