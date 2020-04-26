package main;

import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import base.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Collection of methods for interaction with the text files
public class Interaction {
	private static Object[] deleted = new Object[0];	// list for objects that will be deleted
	private static Object[] written = new Object[0];	// list for objects that will be written
	private static Object[] changes = new Object[0];	// list for all changes made in this session
	private static String[] paths = {"books.txt", "episodes.txt", "movies.txt", "music.txt", "pictures.txt", "series.txt"};	// quick reference for paths to text files
	
	// add collection of objects to the written list
	static void write(ArrayList <Object> objects) {
		ArrayList <Object> temp = new ArrayList <Object> (Arrays.asList(written));
		for (int i=0; i<objects.size(); i++) {
			temp.add(objects.get(i));
		}
		written = temp.toArray(new Object[0]);
	}
	
	// add collection of objects to the deleted list
	static void delete(ArrayList <Object> objects) {
		ArrayList <Object> temp = new ArrayList <Object> (Arrays.asList(deleted));
		for (int i=0; i<objects.size(); i++) {
			temp.add(objects.get(i));
		}
		deleted = temp.toArray(new Object[0]);
	}
	
	static void undo(String action, Object object) {
		if (action=="delete") {
			ArrayList <Object> temp = new ArrayList <Object> (Arrays.asList(deleted));
			temp.remove(object);
			deleted = temp.toArray(new Object[0]);
		}
		else if (action=="write") {
			ArrayList <Object> temp = new ArrayList <Object> (Arrays.asList(written));
			temp.remove(object);
			written = temp.toArray(new Object[0]);
		}
	}
	
	// apply all changes from the deleted and written lists
	// deleted will be applied first then written to avoid 
	// issues when overwriting existing items
	static void apply() {
		// lists to store content from text files
		// content will only be loaded when the file is
		// affected by either deleting or writing
		ArrayList <Object> temp = new ArrayList <Object> (Arrays.asList(changes));
		ArrayList <String> books = new ArrayList <String>();
		ArrayList <String> episodes = new ArrayList <String>();
		ArrayList <String> movies = new ArrayList <String>();
		ArrayList <String> pictures = new ArrayList <String>();
		ArrayList <String> songs = new ArrayList <String>();
		ArrayList <String> series = new ArrayList <String>();
		int[] marked = {0, 0, 0, 0, 0, 0};
		// path to the directory of the text files
		String path = "D:\\Christian\\Eclipse\\java\\eclipse-workspace\\Archiver\\src\\data\\";
		for (int i=0; i<deleted.length; i++) {
			if (deleted[i] instanceof Book) {
				if (marked[0]==0) {
					marked[0] = 1;
					Path text = Paths.get(path+paths[0]);
					try (Stream <String> lines = Files.lines(text)){
						books = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				books.remove(deleted[i].toString());
			}
			else if (deleted[i] instanceof Episode) {
				if (marked[1]==0) {
					marked[1] = 1;
					Path text = Paths.get(path+paths[1]);
					try (Stream <String> lines = Files.lines(text)){
						episodes = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				episodes.remove(deleted[i].toString());
			}
			else if (deleted[i] instanceof Movie) {
				if (marked[2]==0) {
					marked[2] = 1;
					Path text = Paths.get(path+paths[2]);
					try (Stream <String> lines = Files.lines(text)){
						movies = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				movies.remove(deleted[i].toString());
			}
			else if (deleted[i] instanceof Picture) {
				if (marked[3]==0) {
					marked[3] = 1;
					Path text = Paths.get(path+paths[3]);
					try (Stream <String> lines = Files.lines(text)){
						pictures = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				pictures.remove(deleted[i].toString());
			}
			else if (deleted[i] instanceof Song) {
				if (marked[4]==0) {
					marked[4] = 1;
					Path text = Paths.get(path+paths[4]);
					try (Stream <String> lines = Files.lines(text)){
						songs = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				songs.remove(deleted[i].toString());
			}
			else if (deleted[i] instanceof Series) {
				if (marked[5]==0) {
					marked[5] = 1;
					Path text = Paths.get(path+paths[5]);
					try (Stream <String> lines = Files.lines(text)){
						series = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				series.remove(deleted[i].toString());
			}
			else {
				System.out.println("Error: no matching type found");
			}
			temp.add(deleted[i]);
		}
		for (int i=0; i<written.length; i++) {
			if (written[i] instanceof Book) {
				if (marked[0]==0) {
					marked[0] = 1;
					Path text = Paths.get(path+paths[0]);
					try (Stream <String> lines = Files.lines(text)){
						books = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				books.add(written[i].toString());
			}
			else if (written[i] instanceof Episode) {
				if (marked[1]==0) {
					marked[1] = 1;
					Path text = Paths.get(path+paths[1]);
					try (Stream <String> lines = Files.lines(text)){
						episodes = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				episodes.add(written[i].toString());
			}
			else if (written[i] instanceof Movie) {
				if (marked[2]==0) {
					marked[2] = 1;
					Path text = Paths.get(path+paths[2]);
					try (Stream <String> lines = Files.lines(text)){
						movies = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				movies.add(written[i].toString());
			}
			else if (written[i] instanceof Picture) {
				if (marked[3]==0) {
					marked[3] = 1;
					Path text = Paths.get(path+paths[3]);
					try (Stream <String> lines = Files.lines(text)){
						pictures = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				pictures.add(written[i].toString());
			}
			else if (written[i] instanceof Song) {
				if (marked[4]==0) {
					marked[4] = 1;
					Path text = Paths.get(path+paths[4]);
					try (Stream <String> lines = Files.lines(text)){
						songs = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				songs.add(written[i].toString());
			}
			else if (written[i] instanceof Series) {
				if (marked[5]==0) {
					marked[5] = 1;
					Path text = Paths.get(path+paths[5]);
					try (Stream <String> lines = Files.lines(text)){
						series = new ArrayList <String> (lines.collect(Collectors.toList()));
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				series.add(written[i].toString());
			}
			else {
				System.out.println("Error: no matching type found");
			}
			temp.add(written[i]);
		}
		deleted = new String[0];
		written = new String[0];
		changes = temp.toArray(new Object[0]);
		
		// write all made changes back to the files
		for (int i=0; i<marked.length; i++) {
			if (marked[i]!=0) {
				if (i==0) {
					try (FileWriter writer = new FileWriter(path+paths[i]);) {
						for (String str: books) {
							writer.write(str+System.lineSeparator());
						}
						writer.close();
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				else if (i==1) {
					try (FileWriter writer = new FileWriter(path+paths[i]);) {
						for (String str: episodes) {
							writer.write(str+System.lineSeparator());
						}
						writer.close();
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				else if (i==2) {
					try (FileWriter writer = new FileWriter(path+paths[i]);) {
						for (String str: movies) {
							writer.write(str+System.lineSeparator());
						}
						writer.close();
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				else if (i==3) {
					try (FileWriter writer = new FileWriter(path+paths[i]);) {
						for (String str: pictures) {
							writer.write(str+System.lineSeparator());
						}
						writer.close();
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				else if (i==4) {
					try (FileWriter writer = new FileWriter(path+paths[i]);) {
						for (String str: songs) {
							writer.write(str+System.lineSeparator());
						}
						writer.close();
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
				else if (i==5) {
					try (FileWriter writer = new FileWriter(path+paths[i]);) {
						for (String str: series) {
							writer.write(str+System.lineSeparator());
						}
						writer.close();
					}
					catch (IOException e) {
						System.out.println("Error: file not found");
					}
				}
			}
		}
	}
}
