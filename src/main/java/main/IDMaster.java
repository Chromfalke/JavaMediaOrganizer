package main;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class IDMaster
{
    private File xml;
    private int maxID;
    private ArrayList<Integer> free;
    
    public IDMaster( String dir )
    {
        this.xml = new File(dir + "idconfig.xml");
        if (xml.exists()) {
            
        }
        else {
            this.maxID = 1;
            this.free = new ArrayList<Integer>();
        }
    }
    
    // get the next id for a new media item
    public int next()
    {
        if ( free.size()==0 )
        {
            maxID++;
            return maxID-1;
        }
        else
        {
            int id = free.get(0);
            free.set(0, id+1);
            reorder(0);
            return id;
        }
    }
    
    // mark an id for reuse
    public void free ( int id )
    {
        Boolean found = false;
        for ( int i=0; i<free.size(); i+=2 )
        {
            // is the id just out of an existing range of free id's
            if ( free.get(i)==id+1 )
            {
                free.set(i, id);
                found = true;
                break;
            }
            else if ( free.get(i+1)==id-1 )
            {
                free.set(i+1, id);
                found = true;
                break;
            }
        }
        // the id is seperate from all other ranges
        if ( !found )
        {
            free.add(id);
            free.add(id);
        }
        else {
            // if it doesnt fit with any other range then no reorder is required
            reorder(1);
        }
    }
    
    // save the current state of ids before shutdown
    public void save( String dir )
    {
        adjustMax();
        String lst = "";
        for (int i=0; i<free.size(); i+=2) {
            if (lst!="") {
                lst += "; ";
            }
            lst += "(" + free.get(i) + ", "  + free.get(i+1) + ")";
        }
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            
            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("idConfig");
            doc.appendChild(root);
            
            Element id = doc.createElement("ID");
            id.appendChild(doc.createTextNode(String.valueOf(maxID)));
            root.appendChild(id);
            
            Element list = doc.createElement("Free");
            list.appendChild(doc.createTextNode(lst));
            root.appendChild(list);
            
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xml);
            
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
    
    // reorder the list of free ids to optimize space
    // 0: partly, 1: full 
    private void reorder( int scale )
    {
        // remove completely reused ranges of ids
        for ( int i=0; i<free.size(); i+=2 )
        {
            if ( free.get(i)>free.get(i+1) )
            {
                free.remove(i);
                free.remove(i+1);
            }
        }
        
        if ( scale==1 )
        {
            // remove completely reused ranges of ids
            for ( int i=0; i<free.size(); i+=2 )
            {
                if ( free.get(i)>free.get(i+1) )
                {
                    free.remove(i);
                    free.remove(i+1);
                }
            }
            // merge ranges if ids if possible
            Boolean end = true;
            while ( end ) {
                end = false;
                for ( int i=0; i<free.size(); i+=2 )
                {
                    for ( int j=0; j<free.size(); j+=2 )
                    {
                        if ( i!=j )
                        {
                            if ( free.get(i)-1==free.get(j+1) )
                            {
                                free.remove(i);
                                free.remove(i+1);
                                free.remove(j);
                                free.remove(j+1);
                                free.add(j);
                                free.add(i+1);
                                end = true;
                                break;
                            }
                            else if ( free.get(i+1)+1==free.get(j) )
                            {
                                free.remove(i);
                                free.remove(i+1);
                                free.remove(j);
                                free.remove(j+1);
                                free.add(i);
                                free.add(j+1);
                                end = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    // adjust the maxID to free up space in the list
    private void adjustMax()
    {
        Boolean done = !free.isEmpty();
        while ( done )
        {
            done = false;
            for ( int i=0; i<free.size(); i+=2 )
            {
                if (free.get(i+1)+1==maxID)
                {
                    maxID = free.get(i);
                    free.remove(i);
                    free.remove(i+1);
                    done = true;
                    break;
                    
                }
            }
        }
    }
    
    // reset the ids
    public void reset()
    {
        if (xml.exists()) {
            xml.delete();
        }
        maxID = 1;
        free.clear();
    }
}
