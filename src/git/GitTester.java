package git;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GitTester {
	private static index index;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		GitTester.writeFile ("foo.txt", "foo file");
		GitTester.writeFile ("bar.txt", "bar file");
		GitTester.writeFile ("stuff.txt", "stuff file");
		GitTester.writeFile ("more.txt", "more file");
		GitTester.writeFile ("another.txt", "another file");
		GitTester.writeFile ("test.txt", "test file");
		GitTester.writeFile ("foobar.txt", "foobar file");
		index = new index ();
		index.init();
	}
	
	@Test
	void addCommit () throws IOException {
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
	}
	
	private static void writeFile (String fileName, String content) {
		 Path p = Paths.get(fileName);
	        try {
	            Files.writeString(p, content, StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}

}
