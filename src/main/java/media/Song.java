package media;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.NumericField;

@Entity
@Table(name="Song")
@Indexed
public class Song
    implements Media
{
    @Id
    @Column(name="song_id")
    private int song_id;
    @Column(name="title")
    @Field(name="title")
    private String title;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="song_artist", joinColumns=@JoinColumn(name="song_id"))
    @Column(name="artist")
    @IndexedEmbedded
    @Field(name="artist")
    private List<String> artist;
    @Column(name="album")
    @Field(name="album")
    private String album;
    @Column(name="pub_year")
    @Field(name="pub_year")
    @NumericField
    private int pub_year;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="song_genre", joinColumns=@JoinColumn(name="song_id"))
    @Column(name="genre")
    @IndexedEmbedded
    @Field(name="genre")
    private List<String> genre;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="song_tags", joinColumns=@JoinColumn(name="song_id"))
    @Column(name="tags")
    @IndexedEmbedded
    @Field(name="tags")
    private List<String> tags;
    @Column(name="location")
    @Field(name="location")
    private String location;
    
    public Song() {}
    
    public Song( int id )
    {
        this.song_id = id;
    }

    public void setID( int id )
    {
        this.song_id = id;
    }
    public int getID()
    {
        return song_id;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }
    public String getTitle()
    {
        return title;
    }
    
    public void setArtist( String[] artist )
    {
        this.artist = Arrays.asList(artist);
    }
    public String[] getArtist()
    {
        return artist.toArray(new String[0]);
    }
    
    public void setAlbum( String album )
    {
        this.album = album;
    }
    public String getAlbum()
    {
        return album;
    }
    
    public void setYear( int year )
    {
        this.pub_year = year;
    }
    public int getYear()
    {
        return pub_year;
    }
    
    public void setGenre( String[] genre )
    {
        this.genre = Arrays.asList(genre);
    }
    public String[] getGenre()
    {
        return genre.toArray(new String[0]);
    }
    
    public void setTags( String[] tags )
    {
        this.tags = Arrays.asList(tags);
    }
    public String[] getTags()
    {
        return tags.toArray(new String[0]);
    }
    
    public void setLocation( String location )
    {
        this.location = location;
    }
    public String getLocation()
    {
        return location;
    }
}
