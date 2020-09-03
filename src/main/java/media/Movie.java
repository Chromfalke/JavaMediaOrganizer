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
@Table(name="Movie")
@Indexed
public class Movie
    implements Media
{
    @Id
    @Column(name="movie_id")
    private int movie_id;
    @Column(name="title")
    @Field(name="title")
    private String title;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="movie_directors", joinColumns=@JoinColumn(name="movie_id"))
    @Column(name="director")
    @IndexedEmbedded
    @Field(name="director")
    private List<String> director;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="movie_actors", joinColumns=@JoinColumn(name="movie_id"))
    @Column(name="actors")
    @IndexedEmbedded
    @Field(name="actors")
    private List<String> actors;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="movie_studios", joinColumns=@JoinColumn(name="movie_id"))
    @Column(name="studio")
    @IndexedEmbedded
    @Field(name="studio")
    private List<String> studio;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="movie_genre", joinColumns=@JoinColumn(name="movie_id"))
    @Column(name="genre")
    @IndexedEmbedded
    @Field(name="genre")
    private List<String> genre;
    @Column(name="pub_year")
    @Field(name="pub_year")
    @NumericField
    private int pub_year;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="movie_tags", joinColumns=@JoinColumn(name="movie_id"))
    @Column(name="tags")
    @IndexedEmbedded
    @Field(name="tags")
    private List<String> movie_tags;
    @Column(name="location")
    @Field(name="location")
    private String location;
    
    public Movie() {}
    
    public Movie( int id )
    {
        this.movie_id = id;
    }

    public void setID( int id )
    {
        this.movie_id=id;

    }
    public int getID()
    {
        return movie_id;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }
    public String getTitle()
    {
        return title;
    }
    
    public void setDirector( String[] director )
    {
        this.director = Arrays.asList(director);
    }
    public String[] getDirector()
    {
        return director.toArray(new String[0]);
    }
    
    public void setActors( String[] actors )
    {
        this.actors = Arrays.asList(actors);
    }
    public String[] getActors()
    {
        return actors.toArray(new String[0]);
    }
    
    public void setStudio( String[] studio )
    {
        this.studio = Arrays.asList(studio);
    }
    public String[] getStudio()
    {
        return studio.toArray(new String[0]);
    }
    
    public void setGenre( String[] genre )
    {
        this.genre = Arrays.asList(genre);
    }
    public String[] getGenre()
    {
        return genre.toArray(new String[0]);
    }
    
    public void setYear( int year )
    {
        this.pub_year = year;
    }
    public int getYear()
    {
        return pub_year;
    }
    
    public void setTags( String[] tags )
    {
        this.movie_tags = Arrays.asList(tags);
    }
    public String[] getTags()
    {
        return movie_tags.toArray(new String[0]);
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
