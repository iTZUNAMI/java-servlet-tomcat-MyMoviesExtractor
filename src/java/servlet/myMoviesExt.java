
package servlet;
import database.Database;
import exception.DBProblemException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;




public class myMoviesExt implements Runnable
    {
    private String sito;
    private String path;
    private int refresh_time; 
    ArrayList<String> provincia ;
     myMoviesExt()
        {
        this.sito="http://www.mymovies.it";
        this.path="/cinema/xml/rss/?id=";
        this.refresh_time=200000;     //tempo in millisecondi di ciclo di estrazione dati dal sito
        this.provincia= new ArrayList<String>();
        this.provincia.add("padova");
        this.provincia.add("venezia");
        }


     /**
      *
      * @param millisecondi
      * modifica il timeout di estrazione dei dati
      */
public void aggiornamento(int millisecondi)   //modifica il tempo di refresh del thread
        {
        this.refresh_time=millisecondi;
        }
  


public String extStella(String xml)
{
    String stella = grabba(xml, "pubblico: ", " stelle");
     if (stella==null || stella.equals("")){stella="0";}
     stella = stella.replace(",",".");
     return stella;
}


public String extGenere(String xml)
{
      String genere = grabba(xml, "Genere .*?>", "</a>");
      if (genere.equals("")) genere="-"; 
      return genere;
}
public String extTrama(String xml)
{
      String trama = grabba(xml, "text-align:justify; margin:0px; padding:0px;\" >", "</b><br /><br />");
      if (trama.equals("")) trama="-";  
       return trama;
}
public String extMinuti(String xml)
{
        String minuti = grabba(xml, "Durata .*?", " minuti");
        if (minuti.equals("")) minuti="0";
        return minuti;
}
public String extPaese(String xml)
{
        String paese = grabba(xml, "   - ", "<");
        //paese=paese.replace(",", "");
        String paese_ok=paese.substring(0, paese.length()-11);
         if (paese_ok.equals("")) paese_ok="-"; 
        return paese_ok;
}
public String extAnno(String xml)
{

        String anno = grabba(xml, "anno=", "\" title=\"Film");
        if (anno.equals("")) anno="-"; 
        return anno;
}
public String extRegia(String xml)
{
        String regia= grabba(xml,"Regia di ","</a>.");
        if (regia.equals("")) regia="-"; 
        return html2text(regia);
}
public String extLocandina(String xml)
{
        String loc= grabba(xml,"img src= \"","\" align=");
        if (loc.equals("")) loc="#"; 
        return loc;
}
public String extAttori(String xml)
{
        String attori= grabba(xml,"Con ","<br />");
        if (attori.equals("")) attori="-"; 
        return html2text(attori);
}
public void extCinema(String xml,mySchedaFilm box)
{
   String cinema= grabba(xml,"<br /><b>A ","<div style");
   String[] arr = cinema.split("<br /> - ");
   
        for (int e = 0; e < arr.length; e++) {
            String filtracinema = arr[e];
            String pulita = html2text(filtracinema);
            //per ogni risultato prendo solo quelli con orario
            String regex="(.*)(\\(.*\\))";    
            Pattern pattern = Pattern.compile(regex);   
             Matcher matcher = pattern.matcher(pulita);  
  
   // Tutti i match  
   while (matcher.find()) 
   {   
   box.setCinema(matcher.group(1), matcher.group(2));     
   }
   
  }
      
    
}





public void parsePagina(int itera) throws MalformedURLException, IOException, ParserConfigurationException, SAXException  {


mySchedaFilm box = new mySchedaFilm(); 

URL service = new URL(sito+path+provincia.get(itera));
URLConnection sc = service.openConnection();
InputStream is = sc.getInputStream();

//Uso InputSource per poter specificare l'encoding specifico
InputSource inputSource = new InputSource(is);
inputSource.setEncoding("ISO-8859-1");

//Parsing DOM
DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
Document doc = docBuilder.parse(inputSource);     
        
// Prendiamo il primo nodo - come suggerisce il metodo - la radice
Node rss = doc.getFirstChild();
    for (int i=0; i <rss.getChildNodes().getLength();i++)
       {
           Node channel = rss.getChildNodes().item(i);
           for (int j=0;j<channel.getChildNodes().getLength();j++)
            {
                Node item = channel.getChildNodes().item(j);              
                //ora itero ogni item
                for (int z=0;z<item.getChildNodes().getLength();z++)
                    {
                      Node element = item.getChildNodes().item(z);
                //se effettivammente un elemento perchè prende anche spazi vuoti
                        if (element.getNodeType() == Node.ELEMENT_NODE) {

                            //qua devo filtrare tutti gli elementi e filtro per item 
                            if ( (element.getParentNode().getNodeName()).equals("item")){

                                //titolo
                                if (element.getNodeName().equals("title"))
                                {
                                box.setTitolo(element.getTextContent());
                                }
                                //link url univoco
                                if (element.getNodeName().equals("link"))
                                {
                                box.setUrl(element.getTextContent());
                                }
                                //elementi descrittivi
                                if (element.getNodeName().equals("description"))
                                {
                                
                                box.setAnno(extAnno(element.getTextContent()));
                                box.setAttori(extAttori(element.getTextContent()));
                                box.setGenere(extGenere(element.getTextContent()));
                                box.setMinuti(extMinuti(element.getTextContent()));
                                box.setPaese(extPaese(element.getTextContent()));
                                box.setRegia(extRegia(element.getTextContent()));
                                box.setStella(extStella(element.getTextContent()));
                                box.setTrama(extTrama(element.getTextContent()));
                                box.setLocandina(extLocandina(element.getTextContent()));
                                this.extCinema(element.getTextContent(),box);
                                
                                
                                
                                //inserimento nel database qua
                                
                                System.out.println(box.getTitolo()) ;
                                System.out.println(box.getUrl()) ;
                                System.out.println(box.getLocandina()) ;
                                System.out.println(box.getAnno()) ;
                                System.out.println(box.getStella()) ;
                                System.out.println(box.getAttori()) ;
                                System.out.println(box.getGenere()) ;
                                System.out.println(box.getMinuti()) ;
                                System.out.println(box.getPaese()) ;
                                System.out.println(box.getTrama()) ;
                                for (int h=0;h<box.conta();h++)
                                {
                                    System.out.println(provincia.get(itera)+" "+box.getCinema_Nome(h)+"  "+box.getCinema_Orario(h));
                                }
                                System.out.println("--------------\n");
                                
                                box.svuota();
                                //fine inserimento
                                }
                                
                                
                            }
                        
                    }
            }
 
       }

    }
    
   // stampatutto(buffer_film,buffer_cinema);
   
}

//converte html in testo rimuovendo i tag comuni
    public static String html2text(String html) {
    return Jsoup.parse(html).text();
}

    
// seleziona html    
private static String grabba (String testo, String inizio, String fine)
{   
    
    String result="";
    String contenuto=".*?";
    Pattern pattern = Pattern.compile(inizio + contenuto + fine, Pattern.DOTALL);
    Matcher matcher = pattern.matcher(testo);
    if (matcher.find()) {
      String testoTrovato = matcher.group();

      Pattern patternInizio = Pattern.compile(inizio, Pattern.DOTALL);
      Matcher matcherInizio = patternInizio.matcher(testoTrovato);
      String testoInizio = "";
      if (matcherInizio.find()) {
        testoInizio = matcherInizio.group();
      }

      Pattern patternFine = Pattern.compile(fine, Pattern.DOTALL);
      Matcher matcherFine = patternFine.matcher(testoTrovato);
      String testoFine = "";
      if (matcherFine.find()) {
        testoFine = matcherFine.group();
      }

      result = testoTrovato.substring(testoInizio.length(), testoTrovato.length() - testoFine.length());
}
    return result;
}
    
    
    @Override
    public void run()
        {
        while(true)         //ciclo infinito
          
         try {
                
          //lo devo eseguire dalla servelt !!!!!!!!!!!!!!!!!
             
             System.out.println("è stato generato il theread"+Thread.currentThread().getName());
             Database db=Database.getDb();             //controlla la presenza del db
           
             for (int i=0;i<provincia.size();i++)
             {
             this.parsePagina(i);
             }
             Thread.sleep(refresh_time);
             //UNA VOLTA
            // break;
             
             
            } catch (InterruptedException ex) {
            Logger.getLogger(myMoviesExt.class.getName()).log(Level.SEVERE, null, ex);
        }
         //classe URL e parsing
          catch (MalformedURLException ex) {
            Logger.getLogger(myMoviesExt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(myMoviesExt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(myMoviesExt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(myMoviesExt.class.getName()).log(Level.SEVERE, null, ex);
        }

         
         //di database sql etc
            catch (DBProblemException ex)
                {
                Logger.getLogger(myMoviesExt.class.getName()).log(Level.SEVERE, null, ex);
                }
            catch (ClassNotFoundException ex)
                {
                Logger.getLogger(myMoviesExt.class.getName()).log(Level.SEVERE, null, ex);
                }
            catch (SQLException ex)
                {
                Logger.getLogger(myMoviesExt.class.getName()).log(Level.SEVERE, null, ex);
                }
           

            }
    }
