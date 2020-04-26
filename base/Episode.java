package base;

import java.util.ArrayList;

public class Episode {
	private String[] genre;		// Genre of the episode/series
	private String studio;		// Studio which produced the series
	private String series;		// Series that the episode belongs to.
	private int season;			// Season of the series the episode belongs to
	private int episode;		// Number of the episode in the series
	private String title;		// Title of the episode
	private String director;	// Director of the episode/season/series
	private String[] actors;	// Notable actors starring in the episode
	private String path;		// Path of the file in case it is available digitally
	private String[] tags;		// Array for additional tags one wants to apply
	
	// Constructor with all attributes
	public Episode(String[] genre, String studio, String series, int season, int episode, String title, String director, String[] actors, String path, String[] tags) {
		this.genre = genre;
		this.studio = studio;
		this.series = series;
		this.season = season;
		this.episode = episode;
		this.title = title;
		this.director = director;
		this.actors = actors;
		this.path = path;
		this.tags = tags;
	}
	
	// Default constructor
	public Episode() {
		genre = new String[0];
		studio = "";
		series = "";
		season = 1;
		episode = 1;
		title = "";
		director = "";
		actors = new String[0];
		path = "";
		tags = new String[0];
	}
	
	// Reassignment of all values (also used for editing a single attribute)
	public void assign(String[] genre, String studio, String series, int season, int episode, String title, String director, String[] actors, String path, String[] tags) {
		this.genre = genre;
		this.studio = studio;
		this.series = series;
		this.season = season;
		this.episode = episode;
		this.title = title;
		this.director = director;
		this.actors = actors;
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
		series = split1[2];
		season = Integer.parseInt(split1[3]);
		episode = Integer.parseInt(split1[4]);
		title = split1[5];
		director = split1[6];
		ArrayList <String> temp_actors = new ArrayList<String>();
		String[] split3 = split1[7].split("#");
		for (int i=0; i<split3.length; i++) {
			temp_actors.add(split3[i]);
		}
		actors = temp_actors.toArray(new String[0]);
		path = split1[8];
		ArrayList <String> temp_tags = new ArrayList<String>();
		String[] split4 = split1[9].split("#");
		
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
		out = out +"|"+ studio +"|"+ series +"|"+ String.format("%d", season) +"|"+ String.format("%d", episode) +"|"+ title +"|"+ director +"|";
		for (int i=0; i<actors.length; i++) {
			out = out +"#"+ actors[i];
		}
		out = out +"|"+ path +"|";
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
	public String getSeries() {
		return this.series;
	}
	public int getSeason() {
		return this.season;
	}
	public int getEpisode() {
		return this.episode;
	}
	public String getTitle() {
		return this.title;
	}
	public String getDirector() {
		return this.director;
	}
	public String[] getActors() {
		return this.actors;
	}
	public String getPath() {
		return this.path;
	}
	public String[] getTags() {
		return this.tags;
	}
}
