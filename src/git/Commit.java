package git;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Commit {

	private String child = "";
	private String parent;

	private String commitName = "";

	private String pTree;//sha1 name of the file 
	private String parentTree;

	private String summary;
	private String author;
	private String date;//EX FORMAT: 2022-09-17
	private static File HEAD = new File ("Test/HEAD");

	private ArrayList <String> list = new ArrayList <String> ();

	//String tree = tree name, should be sha1 or name of the file
	//figure out how to set child
	public Commit(String sum, String auth, String par) throws IOException {//par and tree should just be sha1
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

		if (hasParent) {
			Scanner parentScanner = new Scanner (new File("Test/Objects/" + par));
			String firstHalf = "";
			parentTree = parentScanner.nextLine();
			firstHalf+=parentTree + "\n";//intakes the parent tree
			firstHalf+=parentScanner.nextLine() + "\n";//intakes parent's parent name
			parentScanner.nextLine();//skips the empty line bc has no child before this one
			String secondHalf = "";
			secondHalf+=parentScanner.nextLine() + "\n";//gets author
			secondHalf+=parentScanner.nextLine() + "\n";//gets date
			secondHalf+=parentScanner.nextLine() + "\n";//gets summary
			FileWriter pWriter = new FileWriter(parentFile);
			pWriter.append(firstHalf + "Objects/" + this.commitName + "\n" + secondHalf);//appends name, parent, then child + new line, then second half
			pWriter.close();
		}

		//look through index for deleted/edited and store in arraylist
		File index = new File ("Test/index");
		BufferedReader br = new BufferedReader(new FileReader(index)); 
		ArrayList <String> toDelete = new ArrayList <String> ();
		String line = br.readLine();
		String hasDeleted = "false";
		while (line != null) {
			if (line.contains("*deleted*")) {
				toDelete.add(line.substring(10));
//				System.out.println (line.substring(10));
				System.out.println ("delete: " + line);
				hasDeleted = "true";
			}
			else if (line.contains("*edited*")) {
				toDelete.add(line.substring(9));
				System.out.println ("edit: " + line);
				hasDeleted = "true";
			}
			else {
				list.add(line);
				System.out.println ("store: " + line);
			}
			line = br.readLine();
		}
		
		//add parent tree or delete files
		if (hasDeleted.equals("false")) {
			this.addTreeParent();
			for (String obj : list) {
				System.out.println ("files in list: " + obj);
			}
		}
		else {
			System.out.println ("parent tree path: " +  parentTree);
			File treeF = new File ("Test/" + parentTree);
			this.delete(toDelete, treeF);
			for (String obj : list) {
				System.out.println ("files in list: " + obj);
			}
		}

		Tree tree = new Tree (list);
		pTree = tree.returnSHA();
		//write to the current file: 
		writeToFile();
		FileWriter wr = new FileWriter(HEAD);
		wr.flush();
		wr.append("Test/Objects/" + this.getCommitName ());
		wr.close();

		FileWriter writer = new FileWriter(index);
		writer.flush();

//		System.out.println (this.commitName);
	}

	public void delete (ArrayList <String> arr, File treeF) throws IOException {
		String previousTree = "";

		BufferedReader br = new BufferedReader(new FileReader(treeF)); 

		String line = br.readLine();
		
		while (line != null) {
			System.out.println (line);
			if (line.contains("tree")) {
				previousTree = line;
				System.out.println ("previous tree: " + line);
			}
			else if (arr.size()==0) {
				list.add(line);
			}
			for (int i=0; i<arr.size(); i++) {
				if (!line.contains(arr.get(i))) {
					list.add(line);
					System.out.println ("added when tree traversing: " + line);
				}
				else {
					arr.remove(i);
					i--;
				}
			}
			line = br.readLine();
		}
		if (!arr.isEmpty()) {
			File newF = new File ("Test/Objects/" + previousTree.substring(7));
			this.delete(arr, newF);
		}
		else {
			list.add(previousTree);
			System.out.println ("tree parent from traversing: " + previousTree);
		}

		//		while (foundSHA != true) {
		//			BufferedReader br = new BufferedReader(new FileReader(treeF)); 
		//			
		//			String line = br.readLine();
		//			if (line.contains("tree")) {
		//				previousTree = line;
		//			}
		//			else {
		//				previousTree = null;
		//			}
		//			
		//			while (line != null) {
		//				for (String fileName : arr) {
		//					if (!line.contains(fileName)) {
		//						if (!line.contains("tree")) {
		//							list.add(line);
		//						}
		//						line = br.readLine();
		//					}
		//					else if (line.contains(fileName)) {
		//						if (previousTree != null) {
		//							list.add(previousTree);
		//						}
		//						line = br.readLine();
		//						while (line != null) {
		//							list.add(line);
		//							line = br.readLine();
		//						}
		//						foundSHA = true;
		//					}
		//					}
		//				}
		//			if (line == null) {
		//				treeF = new File ("Test/Objects/" + previousTree.substring(7));
		//			}
		//			
		//			br.close();
		//		}
		//		return pointers;
	}


	private void addTreeParent () throws IOException {
		if (parent != null) {
			System.out.println ("got here");
			File parentF = new File ("Test/Objects/"+ parent);
			System.out.println (parent);
			BufferedReader br = new BufferedReader(new FileReader(parentF)); 
			String line = br.readLine();
			br.close ();
			
			list.add("tree : " + line.substring(8));
			System.out.println ("tree : " + line.substring(8));
//			File treeF = new File ("Test/" + line);
//			System.out.println ("Test/" + line);
//			BufferedReader br2 = new BufferedReader(new FileReader(treeF)); 
//			String line2 = br.readLine();
//			while (line2 != null ) {
//				if (line2.contains("tree")) {
//					list.add("tree : " + line2.substring(8));
//					System.out.println ("tree pointer: " + line2);
//				}
//				else {
//					line2 = br2.readLine();
//				}
//			}
//			br2.close();
		}
		
	}


	private File writeToFile() throws IOException {
		File f = new File("Test/Objects/" + commitName);
		FileWriter writer = new FileWriter(f);
		writer.append("Objects/" + pTree + "\n");
		if (parent!=null) {
			writer.append("Objects/" + parent + "\n");	
		}
		else {
			writer.append("\n");
		}
		writer.append("\n");//every added one has no child yet 
		writer.append(author + "\n");
		writer.append(date + "\n");
		writer.append(summary);
		writer.close();
		return f;
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
		System.out.println ("sha: " + value);
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
		str += pTree + "\n";
		str += parent + "\n";
		str += child + "\n";
		str += author + "\n";
		str += date + "\n";
		str += summary + "\n";
		return str;
	}

}
