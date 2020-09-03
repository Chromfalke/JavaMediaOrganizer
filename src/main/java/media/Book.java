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
import org.hibernate.annotations.NaturalId;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.NumericField;

@Entity
@Table(name="Book")
//@Check(constraints="CASE WHEN isbn IS NOT NULL THEN LENGTH(isbn) = 13 ELSE true END")
@Indexed
public class Book
    implements Media
{
    @Id
    @Column(name="book_id")
    private int book_id;
    @NaturalId
    @Column(name="isbn")
    @DocumentId(name="isbn")
    private String isbn;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="book_authors", joinColumns=@JoinColumn(name="book_id"))
    @Column(name="author")
    @IndexedEmbedded
    @Field(name="author")
    private List<String> book_authors;
    @Column(name="title")
    @Field(name="title")
    private String title;
    @Column(name="series")
    @Field(name="series")
    private String series;
    @Column(name="volume")
    @Field(name="volume")
    @NumericField
    private int volume;
    @Column(name="publisher")
    @Field(name="publisher")
    private String publisher;
    @Column(name="edition")
    @Field(name="edition")
    @NumericField
    private int edition;
    @Column(name="pub_year")
    @Field(name="pub_year")
    @NumericField
    private int pub_year;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="book_genre", joinColumns=@JoinColumn(name="book_id"))
    @Column(name="genre")
    @IndexedEmbedded
    @Field(name="genre")
    private List<String> book_genre;
    @Column(name="blurb")
    @Field(name="blurb")
    private String blurb;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name="book_tags", joinColumns=@JoinColumn(name="book_id"))
    @Column(name="tag")
    @IndexedEmbedded
    @Field(name="tag")
    private List<String> book_tags;
    @Column(name="location")
    @Field(name="location")
    private String location;
    
    public Book () {}
    
    public Book ( int id )
    {
        this.book_id = id;
    }
    
    public void setID( int id )
    {
        this.book_id = id;
    }
    public int getID()
    {
        return book_id;
    }
    
    public void setISBN( String isbn )
    {
        this.isbn = isbn;
    }
    public String getISBN()
    {
        return isbn;
    }
    
    public void setAuthors( String[] authors )
    {
        this.book_authors = Arrays.asList(authors);
    }
    public String[] getAuthors()
    {
        return book_authors.toArray(new String[0]);
    }
    
    public void setTitle( String title )
    {
        this.title = title;
    }
    public String getTitle()
    {
        return title;
    }
    
    public void setSeries( String series )
    {
        this.series = series;
    }
    public String getSeries()
    {
        return series;
    }
    
    public void setVolume( int volume )
    {
        this.volume = volume;
    }
    public int getVolume()
    {
        return volume;
    }
    
    public void setPublisher( String publisher )
    {
        this.publisher = publisher;
    }
    public String getPublisher()
    {
        return publisher;
    }
    
    public void setEdition( int edition )
    {
        this.edition = edition;
    }
    public int getEdition()
    {
        return edition;
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
        this.book_genre = Arrays.asList(genre);
    }
    public String[] getGenre()
    {
        return book_genre.toArray(new String[0]);
    }
    
    public void setBlurb( String blurb )
    {
        this.blurb = blurb;
    }
    public String getBlurb()
    {
        return blurb;
    }
    
    public void setTags( String[] tags )
    {
        this.book_tags = Arrays.asList(tags);
    }
    public String[] getTags()
    {
        return book_tags.toArray(new String[0]);
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
