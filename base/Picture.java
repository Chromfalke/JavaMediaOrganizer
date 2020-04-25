package base;

import java.util.ArrayList;

public class Picture {
	private String[] genre;		// Genre a picture or artwork belongs to
	private String artist;		// Artist who created the artwork/who took the picture
	private String album;		// Album the picture belongs to. For comics this would be the name of the comic
	private String event;		// Specific event the picture might be associated with
	private String[] persons;	// Persons or characters visible in the picture
	private String path;		// Path of the file in case it is available digitally
	private String[] tags;		// Array for additional tags one wants to apply
	
	// Constructor with all attributes
		public Picture(String[] genre, String artist, String album, String event, String[] persons, String path, String[] tags) {
			this.genre = genre;
			this.artist = artist;
			this.album = album;
			this.event = event;
			this.persons = persons;
			this.path = path;
			this.tags = tags;
		}
		
		// Default constructor
		public Picture() {
			genre = new String[0];
			artist = "";
			album = "";
			event = "";
			persons = new String[0];
			path = "";
			tags = new String[0];
		}
		
		// Reassignment of all values (also used for editing a single attribute)
		public void assign(String[] genre, String artist, String album, String event, String[] persons, String path, String[] tags) {
			this.genre = genre;
			this.artist = artist;
			this.album = album;
			this.event = event;
			this.persons = persons;
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
			artist = split1[1];
			album = split1[2];
			event = split1[3];
			ArrayList <String> temp_persons = new ArrayList<String>();
			String[] split3 = split1[7].split("#");
			for (int i=0; i<split3.length; i++) {
				temp_persons.add(split3[i]);
			}
			persons = temp_persons.toArray(new String[0]);
			path = split1[6];
			ArrayList <String> temp_tags = new ArrayList<String>();
			String[] split4 = split1[8].split("#");
			for (int i=0; i<split4.length; i++) {
				temp_tags.add(split4[i]);
			}
			tags = temp_tags.toArray(new String[0]);
		}
		
		//Output all information as a string to save in a file
		public String toString() {
			String out = "";
			for (int i=0; i<genre.length; i++) {
				out = out +"#"+ genre[i];
			}
			out = out +"|"+ artist +"|"+ album +"|"+ event +"|";
			for (int i=0; i<persons.length; i++) {
				out = out +"#"+ persons[i];
			}
			out = out + "|"+path+"|";
			for (int i=0; i<tags.length; i++) {
				out = out +"#"+tags[i];
			}
			return out;
		}
}
