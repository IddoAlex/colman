package run;

import java.util.HashSet;

import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import demo.Demo;
import search.BFSSearcher;
import search.State;

public class Program {

	public static void main(String[] args) throws Exception {

		//searcher.search(new MyMaze3dGenerator())
		
		Demo demo = new Demo();
		demo.run();
		
	/*	HashSet<State<Position>> closedSet = new HashSet<State<Position>>();
		Position p1 = new Position(0,0,0);
		Position p2 = new Position(0,0,0);
		Position parent = new Position(3,3,3);
		
		State<Position> s1 = new State<>(p1);
		State<Position> s2 = new State<>(p2);
		State<Position> pState = new State<>(parent);
		
		s1.setCameFrom(pState);
		s2.setCameFrom(pState);
		
		s1.setCost(2);
		s2.setCost(1);
		
		closedSet.add(s1);
		
		String str, str2, str3;
		System.out.println("p1 hashcode: " + p1.hashCode());
		System.out.println("s1 hashcode: " + s1.hashCode());
		System.out.println("p2 hashcode: " + p2.hashCode());
		System.out.println("s2 hashcode: " + s2.hashCode());
		str = closedSet.contains(s2) ? "Contains!" : "Doesn't contain";
		str2 = s1.equals(s2) ? "states Equal!" : "states not equal";
		str3 = p1.equals(p2) ? "position Equal!" : "position not equal";
		
		System.out.println(str);
		System.out.println(str2);
		System.out.println(str3);
		
		System.out.println(p1.equals(p2));
		System.out.println(s1.equals(s2));*/
	}

}
