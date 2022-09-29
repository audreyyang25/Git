package git;
import java.security.*;
import java.math.*;
import java.util.*;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * Note to self: 
 * changes for zipped: added zip and hashedZip variables, 
 * zip: variable contianing the zipped version of contents
 * hashedZip: variable containing the hashed version of zip
 * 
 * chnages in code: 
 * 1) In createFile method, replaced hashedContents with hashedZip
 * 2) in createFIle method, replaced contents with zip
 * 3) in getHashed method, replaced hashedContents with hashedZip
 */

public class Blob {
	
	private String contents;
		
	private String hashedContents;//sha1 of contents
	
	private String zip;
	
	private String hashedZip; 
	
	public String getHashed() {
		return hashedZip;
	}
	
	public Blob (String file) throws IOException {
		String ret = "";//read in contents into this variable
		try {//takes in file, reads in to contents
		      File f = new File(file);
		      Scanner in = new Scanner(f);
		      while (in.hasNextLine()) {
		        ret+= in.nextLine();
		      }
		      in.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		contents = ret;//contents = what was read in
		//HERE WOULD BE: CONTENTS = GETZIP(CONTENTS);
		hashedContents = getSha1(contents);//call the hash method on the contents(now zipped contents), save it in hashedContents
		this.zip = zip();
		this.hashedZip = getSha1(zip());
		createFileNormal();
		//createFileZip();
//		System.out.println("File name: " + file);
//		System.out.println("File Contents: " + contents);
//		System.out.println("Sha1 of Contents: " + hashedContents);
//		System.out.println("Zipped Contents: " + zip);
//		System.out.println("Sha1 of Zipped Contents: " + hashedZip);
//		System.out.println();

	}
	//method for writing with the zip
	private void createFileZip () throws IOException{//Goal: create file in the objects folder with a certain name of the hashed contents 
		File f = new File("Test/Objects/" + hashedZip);//DEPENDENT ON OBJECTS FOLDER NAME = OBJECTS 
		FileWriter writer = new FileWriter(f);
		System.out.println("ADDING: " + zip);
		writer.append(zip);
		writer.close();
	}
	//method for writing with regular sha1
	private void createFileNormal () throws IOException{//Goal: create file in the objects folder with a certain name of the hashed contents 
		File f = new File("Test/Objects/" + hashedContents);//DEPENDENT ON OBJECTS FOLDER NAME = OBJECTS 
		FileWriter writer = new FileWriter(f);
		writer.append(contents);
		writer.close();
	}
	

	private String zip() throws IOException {//returns zipped version of contents
		String str = contents;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
	}
	
	public static String getSha1 (String input) {
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

		return sha1;
	}
}
