package testers;

import java.io.IOException;

import git.Commit;
import git.index;

public class CommitTest {

	public static void main(String[] args) throws IOException {
		index index = new index ();
		index.init();
		
		index.add("foo.txt");
		index.add("Stuff.txt");
		index.add("bar.txt");
		index.add("foobar.txt");
		index.add("anything");
		Commit commit = new Commit("This is a summary","Matthew Ko",null);		
		index index2 = new index ();
		index2.init();
		
		index.add("oneMore");
		Commit child = new Commit("This is the second summary","Steven Ko",commit.getCommitName());
		commit.setChild(child);

	}

}
