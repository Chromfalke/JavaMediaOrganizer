package base;

import java.util.ArrayList;

public class Series {
	private String[] genre;		// Genre of the movie
	private String studio;		// Studio which produced the movie
	private String title;		// Title of the movie
	private String director;	// Director of the movie
	private String[] actors;	// Notable actors starring in the movie
	private Integer[] episodes;		// List index are the seasons, saved integers the number of episodes
	private String path;		// Path of the directory in case it is available digitally
	private String[] tags;		// Array for additional tags one wants to apply
	
	// Constructor with all attributes
	public Series(String[] genre, String studio, String title, String director, String[] actors, Integer[] episodes, String path, String[] tags) {
		this.genre = genre;
		this.studio = studio;
		this.title = title;
		this.director = director;
		this.actors = actors;
		this.episodes = episodes;
		this.path = path;
		this.tags = tags;
	}
	
	// Default constructor
	public Series() {
		genre = new String[0];
		studio = "";
		title = "";
		director = "";
		actors = new String[0];
		episodes = new Integer[0];
		path = "";
		tags = new String[0];
	}
	
	// Reassignment of all values (also used for editing a single attribute)
	public void assign(String[] genre, String studio, String title, String director, String[] actors, Integer[] episodes, String path, String[] tags) {
		this.genre = genre;
		this.studio = studio;
		this.title = title;
		this.director = director;
		this.actors = actors;
		this.episodes = episodes;
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
		studio = split1[1];
		title = split1[2];
		director = split1[3];
		path = split1[4];
		ArrayList <String> temp_actors = new ArrayList<String>();
		ArrayList <String> temp_tags = new ArrayList<String>();
		ArrayList <Integer> temp_episodes = new ArrayList <Integer>();
		String[] split3 = split1[5].split("#");
		String[] split4 = split1[6].split("#");
		String[] split5 = split1[7].split("#");
		for (int i=0; i<split3.length; i++) {
			temp_actors.add(split3[i]);
		}
		actors = temp_actors.toArray(new String[0]);
		for (int i=0; i<split4.length; i++) {
			temp_episodes.add(Integer.parseInt(split4[i]));
		}
		episodes = temp_episodes.toArray(new Integer[0]);
		for (int i=0; i<split5.length; i++) {
			temp_tags.add(split5[i]);
		}
		tags = temp_tags.toArray(new String[0]);
	}
	
	//Output all information as a string to save in a file
	public String toString() {
		String out = genre +"|"+ studio +"|"+ title +"|"+ director +"|"+ path;
		for (int i=0; i<actors.length; i++) {
			out = out +"#"+ actors[i];
		}
		out = out + "|";
		for (int i=0; i<episodes.length; i++) {
			out = out +"#"+episodes[i];
		}
		out = out + "|";
		for (int i=0; i<tags.length; i++) {
			out = out +"#"+tags[i];
		}
		return out;
	}
	
	// get-methods for all attributes
	public String[] getGenre() {
		return this.genre;
	}
	public String getStudio() {
		return this.studio;
	}
	public String getTitle() {
		return this.title;
	}
	public String getDirector() {
		return this.director;
	}
	public String[] getPersons() {
		return this.actors;
	}
	public Integer[] getEpisodes() {
		return this.episodes;
	}
	public String getPath() {
		return this.path;
	}
	public String[] getTags() {
		return this.tags;
	}
}
