import greenfoot.*;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.net.URL;

/**
 * Write a description of class XMLReader here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpriteSheetReader  
{
    private GreenfootImage spriteSheet;
    private HashMap<String, GreenfootImage> sprites;
    
    public SpriteSheetReader(String sheet) {
        spriteSheet = new GreenfootImage(sheet);
    }

    public boolean readXML(String xml) {
        sprites = new HashMap();
        
        
        
        Document document;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            URL resourceURL = getClass().getClassLoader().getResource(xml);
            InputStream input = resourceURL.openStream();
            
            document = dbf.newDocumentBuilder().parse(input);
            NodeList subTextureNodeList = document.getElementsByTagName("SubTexture");
            
            for(int i = 0; i < subTextureNodeList.getLength(); i++) {
                Element subTextureElement = (Element)subTextureNodeList.item(i);
                String name = subTextureElement.getAttribute("name");
                int x = Integer.parseInt(subTextureElement.getAttribute("x"));
                int y = Integer.parseInt(subTextureElement.getAttribute("y"));
                int width = Integer.parseInt(subTextureElement.getAttribute("width"));
                int height = Integer.parseInt(subTextureElement.getAttribute("height"));
                
                
                GreenfootImage img = new GreenfootImage(width, height);
                img.drawImage(spriteSheet, x * -1, y * -1);
                
                sprites.put(name, img);
            }
            
            return true;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return false;
    }
    
    public GreenfootImage getSprite(String sprite) {
        return sprites.get(sprite);
    }
}
