package git;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Tree {
	private String sha;
	public Tree (ArrayList <String> arr) throws IOException {
		writingToFile(arr,generateAll(arr)); 
	}
	
	public String generateAll (ArrayList <String> arr) {
		String str = ""; 
		for(int i =0; i < arr.size(); i++) {
			str += arr.get(i) + "\n";
		}
//		System.out.println(sha1(str));
		return sha1(str);
		
	}
	
	public String returnSHA () {
		return sha;
	}
	
	public String sha1 (String input) {
		String value = input;
		String sha1 = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(value.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}
		sha = sha1;
		return sha1;
	}
	
	public void writingToFile (ArrayList <String> arr, String str) throws IOException {
		FileWriter fw = new FileWriter(new File("Test/Objects/", str));//correct folder, name
		for(String stringe: arr) {
		  fw.write(stringe + System.lineSeparator());
		}
		fw.close();
	}
}
