package git;
import java.util.*;
import java.io.*;


public class index {
	
	private File indexFile;
	private HashMap<String,String> indexMap = new HashMap<String,String>(); //map of the name(txt) and the sha1 of the contents 
	
	
	public index() throws IOException {
		
	}
	
	//makes an object folder, index file 
	public void init() throws IOException {
		File f = new File("Test/Objects");
		f.mkdir();
		indexFile = new File("Test/index");
		FileWriter writer = new FileWriter(indexFile);
	}
	
	public void add(String fileName) throws IOException {//add blob to objects folder, do the index with the fileName : hashedContents
		File f = new File("Test/" + fileName);
		if (f.exists()) {
			
		Blob b = new Blob("Test/" + fileName);//filename ex = foo.txt
		String sha = b.getHashed();
		indexMap.put(fileName, sha);
		Scanner s = new Scanner(indexFile);
		String alreadyIndexed = "";
		while (s.hasNextLine()) {
			alreadyIndexed+=s.nextLine() + "\n";
		}
		FileWriter fw = new FileWriter(indexFile);
		fw.append(alreadyIndexed+ "blob" + " : " + sha + " " + fileName);
		
		fw.close();
		}
	}
	
	public void remove(String fileName) throws IOException {
		int length = fileName.length();//=7
		String sha = indexMap.get(fileName);
		File f = new File("Test/Objects/" + sha);
		f.delete();//correctly deletes actual file
		String whatsLeft = "";
		Scanner in = new Scanner(indexFile);
		while(in.hasNextLine()) {
			String newLine = in.nextLine();
			if (newLine.substring(0,length).equals(fileName)) {//if the first _ letters = fileName 
				
			}
			else {
				whatsLeft +=newLine;
				whatsLeft += "\n";
			}
		}
		FileWriter fw = new FileWriter(indexFile);
		fw.append(whatsLeft);
		fw.close();
	}
	
	public void delete (String fileName) throws IOException {
		FileWriter fw = new FileWriter(indexFile);
		fw.append("*deleted* " + fileName);
		fw.close();
	}
	
	public void edit (String fileName) throws IOException {
		FileWriter fw = new FileWriter(indexFile);
		fw.append("*edited* " + fileName);
		fw.close();
	}
	
}
