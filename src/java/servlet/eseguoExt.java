/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import exception.DBProblemException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author iCN
 */
public class eseguoExt {
    
    
 // private Thread thread_extractor=new Thread(new myMoviesExt()); //creo un oggetto thread. Tale oggetto permette l'estrazione dei dati meteo e il salvataggio su db in back groud con un thread

       public static void main(String[] args) throws DBProblemException, SQLException, ClassNotFoundException
    {
        try {
            // System.out.println("AVVIO ESTRAZIONE DAL MAIN");  
               
              myMoviesExt test = new myMoviesExt();
              for (int i=0;i<test.provincia.size();i++)
              {
               
              
                  test.parsePagina(i);
              
              }
              
              
              
              
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(eseguoExt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(eseguoExt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(eseguoExt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(eseguoExt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
  
    
}
