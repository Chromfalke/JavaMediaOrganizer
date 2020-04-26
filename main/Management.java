package main;

import base.*;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.time.LocalDateTime;
import java.util.Random;

public class Management {
	private static String loc = "D:\\Christian\\Eclipse\\java\\eclipse-workspace\\Archiver\\src";
	private static int logsize;
	private static String[] files = {"\\data\\books.txt", "\\data\\episodes.txt", "\\data\\movies.txt", "\\data\\pictures.txt", "\\data\\series.txt", "\\data\\roots.txt"};
	
	public static void main(String[] args) throws IOException {
		// get directory of jar file
		/*URL url = Management.class.getProtectionDomain().getCodeSource().getLocation();
		*loc = url.toURI().getPath();
		*get directory of jar
		*/
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		while (true) {
			// loop will be exited, when a log has been found
			try (FileWriter writer = new FileWriter(loc+"\\data\\log.txt", true);) {
				writer.write("["+dtf.format(now)+"] STARTING SESSION LOG");
				
				while (true) {
					// loop will be exited, when the data file has been found
					// load saved values
					Path save = Paths.get(loc+"\\data\\data.txt");
					try (Stream <String> lines = Files.lines(save)){
						ArrayList <String> data = new ArrayList <String> (lines.collect(Collectors.toList()));
						// update location
						if (loc!=data.get(0)) {
							data.set(0, loc);
						}
						// update logsize
						if (data.get(1)!="") {
							logsize = Integer.parseInt(data.get(1));
							if (logsize<=5) {
								data.set(1, Integer.toString(logsize+1));
							}
						}
						else {
							logsize = 0;
							data.set(1, "0");
						}
						
						// save updated values
						try (FileWriter t_writer = new FileWriter(loc+"\\data.txt")) {
							for (String str: data) {
								t_writer.write(str);
							}
							t_writer.close();
						}
						catch (IOException e) {
							// file manipulation during operation
							writer.write("["+dtf.format(now)+"] Fatal Error: Illegal file manipulation on data.txt detected. Session will be aborted.");
							writer.write("");
							System.exit(0);
						}
						break;
					}
					catch (IOException e) {
						// data file does not exist
						// create it and retry
						writer.write("["+dtf.format(now)+"] Error: Data.txt does not exist. Creating file.");
						File missingData = new File(loc+"\\data\\data.txt");
						missingData.createNewFile();
					}
				}
				
				// check other files and create if missing
				checkFiles();
				
				writer.write("["+dtf.format(now)+"] All necessery files are available, continuing");
				writer.close();
				
				// delete oldest log when more than five are saved
				if (logsize>5) {
					removeOldestLog();
				}
				break;
			}
			catch (IOException e) {
				// open error window with "log not found / corrupted directory"
				// create file and restart application
				File missingLog = new File(loc+"\\data\\log.txt");
				missingLog.createNewFile();
			}
		}
		
		guiStartup();
		
	}
	
	// remove the oldest session log from the file if more than five are saved
	static void removeOldestLog() {
		Path log = Paths.get(loc+"\\data\\log.txt");
		try (Stream <String> lines = Files.lines(log)) {
			ArrayList <String> sessionlog = new ArrayList <String> (lines.collect(Collectors.toList()));
			while (sessionlog.get(1).contains("STARTING SESSION LOG")) {
				sessionlog.remove(0);
			}
			sessionlog.remove(0);
			while (true) {
				try (FileWriter writer = new FileWriter(loc+"\\data\\log.txt")) {
					for (String str: sessionlog) {
						writer.write(str);
					}
					writer.close();
					break;
				}
				catch (IOException e) {
					File missingLog = new File(loc+"\\data\\log.txt");
					missingLog.createNewFile();
				}
			}
		}
		catch (IOException e) {
			// log has been deliberetly removed during oparation
			// session will be aborted
			Interface.error("Fatal Error", "Log removed during operation. Aborting Session");
			System.exit(0);
		}
	}
	
	// check needed files and create them when necessary
	static void checkFiles() throws IOException {
		for (String str: files) {
			File missingFile = new File(loc+str);
			if (!missingFile.exists()) {
				missingFile.createNewFile();
			}
		}
	}
	
	// pick random item from the given list (search results)
	static Object pickRandom(Object[] objects) {
		Random rand = new Random();
		int index = rand.nextInt(objects.length);
		return objects[index];
	}
	
	// search all saved objects based on given criteria
	static ArrayList <Object> search(String[] categories, String[] criteria) {
		ArrayList <ArrayList <String>> matches = new ArrayList <ArrayList <String>>();
		for (int i=0; i<6; i++) {
			matches.add(new ArrayList <String>());
		}
		int index = 0;
		for (String stra: categories) {
			try (Scanner scanner = new Scanner(new File(loc+"\\data\\"+stra))) {
				while (scanner.hasNextLine() ) {
					for (String strb: criteria) {
						if (scanner.nextLine().contains(strb)) {
							matches.get(index).add(scanner.nextLine());
							break;
						}
					}
				}
				index++;
			}
			catch (IOException e) {
				try (FileWriter writer = new FileWriter(loc+"\\data\\log.txt")) {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					writer.write("["+dtf.format(now)+"] Error: "+stra+" does not exist. Creating file.");
					writer.close();
				}
				catch (IOException f) {
					// log has been deliberetly removed during oparation
					// session will be aborted
					Interface.error("Fatal Error", "Log removed during operation. Aborting Session");
					System.exit(0);
				}
			}
		}
		ArrayList <Object> results = new ArrayList <Object>();
		for (int i=0; i<6; i++) {
			for (String str: matches.get(i)) {
				if (i==0) {
					Book temp = new Book();
					temp.fromString(str);
					results.add(temp);
				}
				else if (i==1) {
					Episode temp = new Episode();
					temp.fromString(str);
					results.add(temp);
				}
				else if (i==2) {
					Movie temp = new Movie();
					temp.fromString(str);
					results.add(temp);
				}
				else if (i==3) {
					Picture temp = new Picture();
					temp.fromString(str);
					results.add(temp);
				}
				else if (i==4) {
					Song temp = new Song();
					temp.fromString(str);
					results.add(temp);
				}
				else if (i==5) {
					Series temp = new Series();
					temp.fromString(str);
					results.add(temp);
				}
			}
		}
		return results;
	}
	
	// open the given object if it is available
	static void open(Object object) {
		
	}
	
	// start the main gui and handle possible events
	static void guiStartup() {
		
	}
}
