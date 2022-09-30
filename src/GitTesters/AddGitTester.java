package GitTesters;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import git.Commit;
import git.index;

class AddGitTester {
	private static index index;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		AddGitTester.writeFile ("Test/foo.txt", "foo file");
		AddGitTester.writeFile ("Test/bar.txt", "bar file");
		AddGitTester.writeFile ("Test/stuff.txt", "stuff file");
		AddGitTester.writeFile ("Test/more.txt", "more file");
		AddGitTester.writeFile ("Test/another.txt", "another file");
		AddGitTester.writeFile ("Test/test.txt", "test file");
		AddGitTester.writeFile ("Test/foobar.txt", "foobar file");
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
//		File another = new File ("Test/another.txt");
//		another.delete();
//		
//		File test = new File ("Test/test.txt");
//		test.delete();
//		
//		File foobar = new File ("Test/foobar.txt");
//		foobar.delete();
//		
//		File index = new File ("Test/index");
//		index.delete();
//	}
	
	@Test
	void testAddCommit () throws IOException {
		index.add ("foo.txt");
		Commit first = new Commit ("first commit", "Audrey Yang", null);
		
		index.add ("bar.txt");
		index.add("stuff.txt");
		Commit second = new Commit ("second commit", "Audrey Yang", first.getCommitName());
		first.setChild(second);
		
		index.add("more.txt");
		index.add("another.txt");
		Commit third = new Commit ("third commit", "Audrey Yang", second.getCommitName());
		second.setChild(third);
		
		index.add("test.txt");
		index.add("foobar.txt");
		Commit fourth = new Commit ("fourth commit", "Audrey Yang", third.getCommitName());
		third.setChild(fourth);
		
		ArrayList <String> arr = new ArrayList <String> ();
		arr.add("master : eb3b121f3c45e5ab97a9e34a5245fce90008361b");
		arr.add ("anotherBranch : 52e9bc0c838a84c6770b416d603b747a570b9c88");
		fourth.createBranchReference(arr); // is this the correct way of creating a branch reference
	}
	
	static void writeFile (String fileName, String content) {
		 Path p = Paths.get(fileName);
	        try {
	            Files.writeString(p, content, StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}

}
