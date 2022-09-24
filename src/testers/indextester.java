package testers;

import java.io.IOException;


import git.index;

public class indextester {
	public static void main (String [] args) throws IOException{
		index index = new index ();
		index.init();
		
		index.add("foo.txt");
		index.add("Stuff.txt");
		index.add("bar.txt");
		index.add("foobar.txt");
		index.add("anything");
		
//		index.remove("foo.txt");
//		index.remove("bar.txt");
//		index.remove("foobar.txt");
//		index.remove("Stuff.txt");
//		index.remove("bro");
		
	}
}
