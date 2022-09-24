package git;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Scanner;

public class Commit {
	
	private String child = "";
	private String parent;
	
	private String commitName = "";
	
	private String pTree;//sha1 name of the file 
	
	
	private String summary;
	private String author;
	private String date;//EX FORMAT: 2022-09-17

	//String tree = tree name, should be sha1 or name of the file
	public Commit(String ptree, String sum, String auth, String par) throws IOException {//par and tree should just be sha1
		
		this.pTree = ptree;
		
		this.summary = sum;
		this.author = auth;
		this.date = "" + java.time.LocalDate.now();
		boolean hasParent = false;
		File parentFile = null;//could be dangerous might change this later 
		if (par!=null) {
			parent = par;
			parentFile = new File ("Test/Objects/" + par);
			hasParent = true;//later in constructor need to set parent child to this commitName
		}
		this.commitName = getSha1();
		
		//write to the current file: 
		writeToFile();
		
		if (hasParent) {
			Scanner parentScanner = new Scanner (new File("Test/Objects/" + par));
			String firstHalf = "";
			firstHalf+=parentScanner.nextLine() + "\n";//intakes the parent name
			firstHalf+=parentScanner.nextLine() + "\n";//intakes parent's parent name
			parentScanner.nextLine();//skips the empty line bc has no child before this one
			String secondHalf = "";
			secondHalf+=parentScanner.nextLine() + "\n";//gets author
			secondHalf+=parentScanner.nextLine() + "\n";//gets date
			secondHalf+=parentScanner.nextLine() + "\n";//gets summary
			FileWriter pWriter = new FileWriter(parentFile);
			pWriter.append(firstHalf + "Object/" + this.commitName + "\n" + secondHalf);//appends name, parent, then child + new line, then second half
			pWriter.close();
		}
	}
	
//	private void createTree () {
//		Tree tree = new Tree ();
//	}
	
	private void writeToFile() throws IOException {
		File f = new File("Test/Objects/" + commitName);
		FileWriter writer = new FileWriter(f);
		writer.append("Object/" + commitName + "\n");
		if (parent!=null) {
			writer.append("Object/" + parent + "\n");	
		}
		else {
			writer.append("\n");
		}
		writer.append("\n");//every added one has no child yet 
		writer.append(author + "\n");
		writer.append(date + "\n");
		writer.append(summary);
		writer.close();
	}
	
	private String getDate() {
		return date;
	}
	
	public String getpTree() {
		return pTree;
	}
	
	public String getCommitName() {
		return this.commitName;
	}
	
	public void setChild(Commit child) {
		this.child = child.getCommitName();
	}
	
	private String getSha1 () {
		String value = "" + summary + date + author + parent;
		String sha1 = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(value.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}

		return sha1;
	}
	
	public String toString() {
		String str = "";
		str += commitName + "\n";
		str += parent + "\n";
		str += child + "\n";
		str += author + "\n";
		str += date + "\n";
		str += summary + "\n";
		return str;
	}
	
	public static void main (String [] args) throws IOException {
		Commit commit = new Commit("sha1","This is a summary","Matthew Ko",null);		
		Commit child = new Commit("sha11","This is the second summary","Steven Ko",commit.getCommitName());
		commit.setChild(child);
		Commit secondChild = new Commit("sha111","This is the third summary","Christian Bach",child.getCommitName());
		child.setChild(secondChild);
	}
	
}
