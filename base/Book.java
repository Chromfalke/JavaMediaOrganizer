package base;

import java.util.ArrayList;

public class Book {
	private String[] genre;	// Genre of the book
	private String author;	// Author of the book
	private String series;	// Series that the book belongs to. Use title if book does not belong to a series named differently
	private int volume;		// Number of the book in the series
	private String title;	// Title of the book
	private String isbn;	// ISBN number of the book
	private String path;	// Path of the file in case it is saved as an ebook
	private String[] tags;	// Array for additional tags one wants to apply
	
	// Constructor with all attributes
	public Book(String[] genre, String author, String series, int volume, String title, String isbn, String path, String[] tags) {
		this.genre = genre;
		this.author = author;
		this.series = series;
		this.volume = volume;
		this.title = title;
		this.isbn = isbn;
		this.path = path;
		this.tags = tags;
	}
	
	// Default constructor
	public Book() {
		genre = new String[0];
		author = "";
		series = "";
		volume = 1;
		title = "";
		isbn = "";
		path = "";
		tags = new String[0];
	}
	
	// Reassignment of all attributes (also used for editing a single attribute)
	public void assign(String[] genre, String author, String series, int volume, String title, String isbn, String path, String[] tags) {
		this.genre = genre;
		this.author = author;
		this.series = series;
		this.volume = volume;
		this.title = title;
		this.isbn = isbn;
		this.path = path;
		this.tags = tags;
	}
	
	//Input information from a given string
	public void fromString(String information) {
		String[] split1 = information.split("|");
		ArrayList <String> temp_genre = new ArrayList <String>();
		String[] split2 = split1[0].split("#");
		for (int i=0; i<split2.length; i++) {
			temp_genre.add(split2[i]);
		}
		genre = temp_genre.toArray(new String[0]);
		author = split1[1];
		series = split1[2];
		volume = Integer.parseInt(split1[3]);
		title = split1[4];
		isbn = split1[5];
		path = split1[6];
		ArrayList <String> temp_tags = new ArrayList <String>();
		String[] split3 = split1[7].split("#");
		for (int i=0; i<split3.length; i++) {
			temp_tags.add(split3[i]);
		}
		tags = temp_tags.toArray(new String[0]);
	}
	
	//Output all information as a string to save in a file
	public String toString() {
		String out = "";
		for (int i=0; i<genre.length; i++) {
			out = out +"#"+genre[i];
		}
		out = out +"|"+ author +"|"+ series +"|"+ String.format("%d", volume) +"|"+ title +"|"+ isbn +"|"+ path +"|";
		for (int i=0; i<tags.length; i++) {
			out = out +"#"+tags[i];
		}
		return out;
	}
	
}
