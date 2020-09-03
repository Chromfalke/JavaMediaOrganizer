package media;

import java.sql.Timestamp;
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

@Entity
@Table(name="Image")
@Indexed
public class Image
    implements Media
{
    @Id
    @Column(name="image_id")
    private int image_id;
    @Column(name="title")
    @Field(name="title")
    private String title;
    @Column(name="artist")
    @Field(name="artist")
    private String artist;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="image_genre", joinColumns=@JoinColumn(name="image_id"))
    @Column(name="genre")
    @IndexedEmbedded
    @Field(name="genre")
    private List<String> genre;
    @Column(name="timestamp")
    @Field(name="timestamp")
    private Timestamp time;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="image_tags", joinColumns=@JoinColumn(name="image_id"))
    @Column(name="tags")
    @IndexedEmbedded
    @Field(name="tags")
    private List<String> tags;
    @Column(name="location")
    @Field(name="location")
    private String location;
    
    public Image() {}
    
    public Image( int id )
    {
        this.image_id = id;
    }

    public void setID( int id )
    {
        this.image_id = id;
    }
    public int getID()
    {
        return image_id;
    }
    
    public void setTitle( String title )
    {
        this.title = title;
    }
    public String getTitle()
    {
        return title;
    }

    public void setArtist( String artist )
    {
        this.artist = artist;
    }
    public String getArtist()
    {
        return artist;
    }
    
    public void setGenre( String[] genre )
    {
        this.genre = Arrays.asList(genre);
    }
    public String[] getGenre()
    {
        return genre.toArray(new String[0]);
    }
    
    public void setTimestamp( Timestamp time )
    {
        this.time = time;
    }
    public Timestamp getTimestamp()
    {
        return time;
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
