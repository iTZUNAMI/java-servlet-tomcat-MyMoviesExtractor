package servlet;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import database.Database;
import exception.DBProblemException;
import exception.QueryProblemException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mypackage.Ordine;
import mypackage.Utente;



public class Servlet extends HttpServlet {
    
// private Thread thread_extractor=new Thread(new myMoviesExt()); //creo un oggetto thread. Tale oggetto permette l'estrazione dei dati meteo e il salvataggio su db in back groud con un thread

    
    private Object ordine;
    private Database db;
    
  @Override
  public void init(ServletConfig config)
    throws ServletException
    {
       
            super.init(config);
            
            
       
        }

     // creazione e inizializzazione oggetti con scope application
  private void forward(HttpServletRequest request, HttpServletResponse  response,String page) throws ServletException, IOException{
    ServletContext sc = getServletContext();
    RequestDispatcher rd = sc.getRequestDispatcher(page);
    rd.forward(request,response);
  }
  
  private void processRequest(HttpServletRequest request, HttpServletResponse  response) throws ServletException, IOException, ClassNotFoundException, SQLException, DBProblemException{
    String op = request.getParameter("op");
    HttpSession session = request.getSession(true);
    String nick = request.getParameter("account");
    String pass = request.getParameter("password");
    
    
    
    Utente u = new Utente();
   
    if (u==null || op==null || !("login".equals(op))){
       forward(request,response,"/login.jsp");
       return;
    }
    if ("login".equals(op))
    {
       if (u==null || !u.checkLogin(nick, pass)){
            //implementare il login con un messaggio di errore "login non riuscito".
            forward(request,response,"/login.jsp");
            return;
       }
       else
       {
         session.setAttribute("user",u);
         forward(request,response,"/home.jsp");
       }
       return;
     }
        

     if ("inserimento".equals(op))
     {
        Ordine nuovo = new Ordine();
        try
        {
          nuovo.setProgressivo(Integer.parseInt(request.getParameter("progressivo")));
        }
        catch (Exception e)
        {
          forward(request,response,"/inputError.jsp");
          return;
        }
        nuovo.setDescrizione(request.getParameter("descrizione"));
        nuovo.insert();
        forward(request,response,"/home.jsp");
     }
    else if ("moduloInserimento".equals(op))
     {
        forward(request,response,"/moduloInserimento.jsp");
     }
     else if ("mostra".equals(op))
     {
        try
        {
          //ordine=Ordine.load(Integer.parse(request.getParameter("progressivo")));
        }
        catch (Exception e)
        {
          forward(request,response,"/inputError.jsp");
          return;
        }
        request.setAttribute("ordine",ordine);
        forward(request,response,"/mostra.jsp");
    }     
   
  }

  
  
   @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            try {
                processRequest(request, response);
            } catch (DBProblemException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
    
  @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
        {
        try {
                processRequest(request, response);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }catch (DBProblemException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
        }
}


