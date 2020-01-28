/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.util.ArrayList;

/**
 *
 * @author Achille
 */
public class mySchedaFilm 
{
 private String titolo;
 private String url;
 private String stella;
 private String anno;
 private String genere;
 private String trama;
 private String minuti;
 private String attori;
 private String regia;
 private String paese;
 private String locandina;
 private ArrayList cinema;
 private ArrayList cinema_orario;
 
 public mySchedaFilm ()
 {
        this.cinema=new ArrayList();
        this.cinema_orario=new ArrayList();
 }

 public void svuota()
 {
        cinema.clear();
        cinema_orario.clear();
        titolo=null;
        url=null;
        stella=null;
        anno=null;
        genere=null;
        trama=null;
        minuti=null;
        attori=null;
        regia=null;
        paese=null;
        locandina=null;
 }
 public int conta()
    {
            return cinema.size();
    }
 
 public void setStella(String val)
    {
    this.stella=val;
    }

 public String getStella()
    {
    return this.stella;
    }
 
 
 
  public void setTitolo(String val)
    {
    this.titolo=val;
    }

 public String getTitolo()
    {
    return this.titolo;
    }

 
 
 
   public void setAnno(String val)
    {
    this.anno=val;
    }

 public String getAnno()
    {
    return this.anno;
    }

 
 
   public void setMinuti(String val)
    {
    this.minuti=val;
    }

 public String getMinuti()
    {
    return this.minuti;
    }

 
 
   public void setAttori(String val)
    {
    this.attori=val;
    }

 public String getAttori()
    {
    return this.attori;
    }

 
 
   public void setRegia(String val)
    {
    this.regia=val;
    }

 public String getRegia()
    {
    return this.regia;
    }

 
 
   public void setPaese(String val)
    {
    this.paese=val;
    }

 public String getPaese()
    {
    return this.paese;
    }

 
 
   public void setTrama(String val)
    {
    this.trama=val;
    }

 public String getTrama()
    {
    return this.trama;
    }

 
   public void setUrl(String val)
    {
    this.url=val;
    }

 public String getUrl()
    {
    return this.url;
    }

   public void setGenere(String val)
    {
    this.genere=val;
    }

 public String getGenere()
    {
    return this.genere;
    }
 
 
   public void setLocandina(String val)
    {
    this.locandina=val;
    }

 public String getLocandina()
    {
    return this.locandina;
    }
 
 
   public void setCinema(String val1,String val2)
    {
    cinema.add(val1);
    cinema_orario.add(val2);

    }

 public String getCinema_Nome(int x)
 {
    return (String)cinema.get(x);
 
 
}
  public String getCinema_Orario(int x)
 {
  return (String)cinema_orario.get(x);
 }

}