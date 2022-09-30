package testers;

import java.io.IOException;
import java.util.ArrayList;

import git.Commit;
import git.index;

public class CommitTest {

	public static void main(String[] args) throws IOException {
		index index = new index ();
		index.init();
		
		// make blobs
		index.add("foo.txt");
		index.add("Stuff.txt");
		
		//commit
		Commit commit = new Commit("This is a summary","Matthew Ko",null);	
		System.out.println ();
		
		index.add("oneMore");
		Commit child = new Commit("This is the second summary","Steven Ko",commit.getCommitName());
		commit.setChild(child);
		System.out.println ();
		
		index.add("bar.txt");
		index.add("foobar.txt");
		index.add("anything");
		Commit child2 = new Commit ("This is third commit", "Audrey Yang", child.getCommitName());
		child.setChild(child2);
		
		System.out.println ();
		index.delete("bar.txt");
		index.edit("oneMore");
		index.add("oneMore");
//		System.out.println ("deleted");
		Commit four = new Commit ("fourth", "Audrey", child2.getCommitName());
		child2.setChild(four);
		
		
		// deleting things:
		//index file now stores *deleted* filename
		//tree points to one blob
		
	}

}
