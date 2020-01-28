package mypackage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author supremo_zim
 */
public class Ordine
{
   static java.util.Map memory = new java.util.HashMap();
   int progressivo=-1;
   String descrizione=null;

   public int getProgressivo()
   {
      return progressivo;
   }

   public void setProgressivo(int progressivo)
   {
      this.progressivo=progressivo;
   }

   public String getDescrizione()
   {
      return descrizione;
   }

   public void setDescrizione(String descrizione)
   {
      this.descrizione=descrizione;
   }
   public void insert()
   {
     //... sostituire con accesso DB
     memory.put(new Integer(progressivo),this);
   }
   public void update()
   {
     //... sostituire con accesso DB
     memory.put(new Integer(progressivo),this);
   }
   static public Ordine getOrdine(int id)
   {
     //... sostituire con accesso DB
     //return (Ordine) memory.get(new Integer(progressivo));
       Ordine o = new Ordine();
       return o;
   }
   static public java.util.Set getOrdini()
   {
     //... sostituire con accesso DB
      return memory.entrySet();
   }
}