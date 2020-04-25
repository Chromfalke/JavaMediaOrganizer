package base;

import java.util.ArrayList;

public class Song {
	private String[] genre;		// Genre a song belongs to
	private String artist;		// Artist who created the song
	private String album;		// Album the song belongs to
	private String playlist;	// Playlist the song belongs to
	private String path;		// Path of the file in case it is available digitally
	private String[] tags;		// Array for additional tags one wants to apply
	
	// Constructor with all attributes
		public Song(String[] genre, String artist, String album, String playlist, String path, String[] tags) {
			this.genre = genre;
			this.artist = artist;
			this.album = album;
			this.playlist = playlist;
			this.path = path;
			this.tags = tags;
		}
		
		// Default constructor
		public Song() {
			genre = new String[0];
			artist = "";
			album = "";
			playlist = "";
			path = "";
			tags = new String[0];
		}
		
		// Reassignment of all values (also used for editing a single attribute)
		public void assign(String[] genre, String artist, String album, String playlist, String path, String[] tags) {
			this.genre = genre;
			this.artist = artist;
			this.album = album;
			this.playlist = playlist;
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
			playlist = split1[3];
			path = split1[4];
			ArrayList <String> temp_tags = new ArrayList<String>();
			String[] split4 = split1[5].split("#");
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
			out = out +"|"+ artist +"|"+ album +"|"+ playlist +"|"+ path +"|";
			for (int i=0; i<tags.length; i++) {
				out = out +"#"+tags[i];
			}
			return out;
		}
}
