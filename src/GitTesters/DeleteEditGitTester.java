package GitTesters;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import git.Commit;
import git.index;

class DeleteEditGitTester {
	private static index index;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		AddGitTester.writeFile ("Test/foo.txt", "foo file");
		AddGitTester.writeFile ("Test/bar.txt", "bar file");
		AddGitTester.writeFile ("Test/stuff.txt", "stuff file");
		AddGitTester.writeFile ("Test/more.txt", "more file");
		index = new index ();
		index.init();
	}
	
	// uncomment this to clear all files
//	@AfterAll
//	static void cleanUp () {
//		File objects = new File ("Test/Objects/");
//		objects.mkdir();
//		if (objects.exists()) {
//			File[] contents = objects.listFiles();
//			for (File f : contents) {
//	            f.delete();
//	        }
//		}
//		
//		File foo = new File ("Test/foo.txt");
//		foo.delete();
//		
//		File bar = new File ("Test/bar.txt");
//		bar.delete();
//		
//		File stuff = new File ("Test/stuff.txt");
//		stuff.delete();
//		
//		File more = new File ("Test/more.txt");
//		more.delete();
//		
//		File index = new File ("Test/index");
//		index.delete();
//	}
	
	// should this automatically check if blob was deleted (how would we check that?!!), or can we manually check?
	@Test
	void testDelete () throws IOException {
		index.add("foo.txt");
		index.add("bar.txt");
		Commit one = new Commit ("adding foo and bar", "Audrey", null);
		
		AddGitTester.writeFile ("Test/foo.txt", "foo file EDITED");
		index.edit("foo.txt");
		index.add("foo.txt");
		index.delete("bar.txt");
		Commit two = new Commit ("edited foo and deleted bar", "Audrey", one.getCommitName());
		one.setChild(two);
		
		index.add("stuff.txt");
		index.add("more.txt");
		Commit three = new Commit ("added stuff and more file", "Audrey", two.getCommitName());
		two.setChild(three);
		
		AddGitTester.writeFile ("Test/stuff.txt", "stuff file EDITED");
		index.edit("stuff.txt");
		index.add("stuff.txt");
		index.delete("foo.txt");
		Commit four = new Commit ("edited stuff file and deleted foo", "Audrey", three.getCommitName());
		three.setChild(four);
		
		index.delete("more.txt");
		Commit five = new Commit ("deleted more file", "Audrey", four.getCommitName());
		four.setChild(five);
	}
}
