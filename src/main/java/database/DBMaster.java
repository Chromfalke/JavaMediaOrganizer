package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import media.*;

public class DBMaster
{
    private SessionFactory sessionFactory;
    
    // open a new connection to the database
    public DBMaster()
    {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
        if ( sessionFactory==null ) {
            System.out.println("Error sessionFactory is null");
        }
    }
    
    // add a new item to the database
    public void put( List<Media> entry )
    {
        System.out.println("Putting a list of " + entry.size() + " items.");
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            for (Media m: entry) {
                session.saveOrUpdate(m);
            }
            session.getTransaction().commit();
            session.close();
            System.out.println("Done");
        }
        catch (ConstraintViolationException e) {
            System.out.println("Item with identical key already exists");
            session.close();
        }
    }
    
    // search for items in the database
    public List<Media> search( int[] types, String query )
    {
        System.out.println("Searching for:" + query);
        List<Media> result = new ArrayList<Media>();
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        SearchFactory searchFactory = fullTextSession.getSearchFactory();
        List<String> keywords = Arrays.asList(query.split(" "));
        List<String> preNumbers = Arrays.asList(query.replaceAll("\\D+", "").split(" "));
        List<Integer> numbers = new ArrayList<Integer>();
        for (String s: preNumbers) {
            if (!s.isEmpty()) {
                numbers.add(Integer.valueOf(s));
            }
        }
        // Book
        if (types[0]==1) {
            QueryBuilder bookQB = searchFactory.buildQueryBuilder().forEntity(Book.class).get();
            BooleanJunction<?> bool = bookQB.bool();
            for (int i=0; i<keywords.size(); i++) {
                Query q = bookQB.keyword()
                                .onFields("isbn", "author", "title", "series", "publisher", "genre", "blurb", "tag", "location")
                                .matching(keywords.get(i))
                                .createQuery();
                bool.should(q);
            }
            for (int i=0; i<numbers.size(); i++) {
                Query q = bookQB.keyword()
                                .onFields("volume", "edition", "pub_year")
                                .matching(numbers.get(i))
                                .createQuery();
                bool.should(q);
            }
            FullTextQuery sQuery = fullTextSession.createFullTextQuery(bool.createQuery(), Book.class);
            for (Object o: sQuery.list()) {
                result.add((Book) o);
            }
        }
        // Movie
        if (types[1]==1) {
            QueryBuilder bookQB = searchFactory.buildQueryBuilder().forEntity(Movie.class).get();
            BooleanJunction<?> bool = bookQB.bool();
            for (int i=0; i<keywords.size(); i++) {
                Query q = bookQB.keyword()
                                .onFields("title", "director", "actors", "studio", "genre", "tags", "location")
                                .matching(keywords.get(i))
                                .createQuery();
                bool.must(q);
            }
            for (int i=0; i<numbers.size(); i++) {
                Query q = bookQB.keyword()
                                .onFields("pub_year")
                                .matching(numbers.get(i))
                                .createQuery();
                bool.should(q);
            }
            FullTextQuery sQuery = fullTextSession.createFullTextQuery(bool.createQuery(), Movie.class);
            for (Object o: sQuery.list()) {
                result.add((Movie) o);
            }
        }
        // Song
        if (types[2]==1) {
            QueryBuilder bookQB = searchFactory.buildQueryBuilder().forEntity(Song.class).get();
            BooleanJunction<?> bool = bookQB.bool();
            for (int i=0; i<keywords.size(); i++) {
                Query q = bookQB.keyword()
                                .onFields("title", "artist", "album", "genre", "tags", "location")
                                .matching(keywords.get(i))
                                .createQuery();
                bool.must(q);
            }
            for (int i=0; i<numbers.size(); i++) {
                Query q = bookQB.keyword()
                                .onFields("pub_year")
                                .matching(numbers.get(i))
                                .createQuery();
                bool.should(q);
            }
            FullTextQuery sQuery = fullTextSession.createFullTextQuery(bool.createQuery(), Song.class);
            for (Object o: sQuery.list()) {
                result.add((Song) o);
            }
        }
        // Image
        if (types[3]==1) {
            QueryBuilder bookQB = searchFactory.buildQueryBuilder().forEntity(Image.class).get();
            BooleanJunction<?> bool = bookQB.bool();
            for (int i=0; i<keywords.size(); i++) {
                Query q = bookQB.keyword()
                                .onFields("title", "artist", "genre", "tags", "location")
                                .matching(keywords.get(i))
                                .createQuery();
                bool.must(q);
            }
            FullTextQuery sQuery = fullTextSession.createFullTextQuery(bool.createQuery(), Image.class);
            for (Object o: sQuery.list()) {
                result.add((Image) o);
            }
        }
        fullTextSession.close();
        session.close();
        return result;
    }
    
    // remove an item from the database
    public void remove( Media media )
    {
        System.out.println("Removing 1 item");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(media);
        session.getTransaction().commit();
        session.close();
        System.out.println("Done");
    }
    
    // reset the content of the database
    public void reset()
    {
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.purgeAll(Book.class);
        fullTextSession.purgeAll(Movie.class);
        fullTextSession.purgeAll(Song.class);
        fullTextSession.purgeAll(Image.class);
        session.createQuery("delete from Book").executeUpdate();
        session.createQuery("delete from Book_Authors").executeUpdate();
        session.createQuery("delete from Book_Genre").executeUpdate();
        session.createQuery("delete from Book_Tags").executeUpdate();
        
        session.createQuery("delete from Movie").executeUpdate();
        session.createQuery("delete from Movie_Actors").executeUpdate();
        session.createQuery("delete from Movie_Director").executeUpdate();
        session.createQuery("delete from Movie_Genre").executeUpdate();
        session.createQuery("delete from Movie_Studios").executeUpdate();
        session.createQuery("delete from Movie_Tags").executeUpdate();
        
        session.createQuery("delete from Song").executeUpdate();
        session.createQuery("delete from Song_Artist").executeUpdate();
        session.createQuery("delete from Song_Genre").executeUpdate();
        session.createQuery("delete from Song_Tags").executeUpdate();
        
        session.createQuery("delete from Image").executeUpdate();
        session.createQuery("delete from Image_Genre").executeUpdate();
        session.createQuery("delete from Image_Tags").executeUpdate();
        
        session.close();
    }
    
    // do all procedures necessary for shutdown
    public void shutdown()
    {
        sessionFactory.close();
    }
}
