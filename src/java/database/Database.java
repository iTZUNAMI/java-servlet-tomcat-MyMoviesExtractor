

package database;

import exception.DBProblemException;
import exception.QueryProblemException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GruppoMeteo
 */
public final class Database {

    private static Database db;
    private String dbname;
    
    private String driver ="org.apache.derby.jdbc.EmbeddedDriver";
    private Connection connection = null;
    private Statement statement = null;
    
//private
    public Database() throws DBProblemException, SQLException, ClassNotFoundException {
        this.dbname = "MyMoviesDB";
        
        this.driver = "org.apache.derby.jdbc.EmbeddedDriver";
        this.checkDb();

        try
            {
                
           Class.forName(driver).newInstance();
           this.connection = DriverManager.getConnection("jdbc:derby:"+dbname);
           
           this.statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
           System.out.println("DB trovato");
            }
        catch(Exception e) 
            {
             System.out.println("DB non trovato");
             this.createDb();           //crea il db
            }
    }

    /**
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     * prova a collegarsi al db. se la connessione va a buon fine esegue le operazioni di inserimento o aggiornamento dei dati
     */
    public void checkDb() throws ClassNotFoundException, SQLException              //controlla la presenza del db. Se non è presente il db lo crea
        {
         java.sql.Connection con=null;
         Class.forName(driver);
         ResultSet rs=null;
         try
            {
             con=DriverManager.getConnection("jdbc:derby:"+dbname);
             System.out.println("DB trovato");
            }
           catch (java.sql.SQLException sqle)  //se il tentativo di connessione provoca una eccezione (database non esistente) esegui la creazione del db
            {
             System.out.println("DB non trovato");
             this.createDb();           //crea il db
            }
        }


    /**
     * 
     * @throws SQLException
     * @throws ClassNotFoundException
     * Crea il database
     */
    public void createDb() throws SQLException, ClassNotFoundException
        {
        System.out.println("Creazione DB in corso...");
        String creaTabellaUtenti="CREATE TABLE MYMOVIES_UTENTI(NICKNAME VARCHAR(250), COGNOME VARCHAR(250),NOME VARCHAR(250), PASSWORD VARCHAR(250), EMAIL VARCHAR(250), CONTRATTO INT NOT NULL)";

        String inserisciUtente1="insert into MYMOVIES_UTENTI values ('admin','Rossi','Mario','admin','administrator@email.it',1)";
      //  String inserisciUtente2="insert into METEO_UTENTI values ('normale','Gialli','Marco','normale','normale@email.it',2)";

        java.sql.Connection con =null;
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        
        con = DriverManager.getConnection("jdbc:derby:"+dbname+";create=true;");
         con.createStatement().execute(creaTabellaUtenti);
        //creazione altre tabelle
          con.createStatement().execute(inserisciUtente1);
              //il database viene pre-popolato con alcuni utenti di prova
               //il database viene pre-popolato con alcuni utenti di prova
        System.out.println("Db Creato con successo");
        }

    /**
     * 
     * @return
     * @throws DBProblemException
     * @throws SQLException
     * @throws ClassNotFoundException
     * Se il database non c'è,lo crea poi lo ritorna
     */
    public static Database getDb() throws DBProblemException, SQLException, ClassNotFoundException {
            if(db == null)
                    db = new Database();
                            
        return db;
    }

    /**
     * 
     * @param query
     * @return il risultato della query passata per parametro
     * @throws QueryProblemException
     */
    public ResultSet executeQuery(String query) throws QueryProblemException {
        try {
            return statement.executeQuery(query); //controlla se c'è già un record per la località
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            throw new QueryProblemException(ex);
        }
    }

    /**
     * 
     * @param UpdateQuery
     * @return true se l'operazione di update è andata a buon fine
     * @throws SQLException
     */
    public boolean updateQuery(String UpdateQuery) throws SQLException {
        int res = statement.executeUpdate(UpdateQuery);
        return res < 0;
        }

    /**
     * 
     * @return connection
     */
    public Connection getConnection(){
        return(this.connection);
    }
    
    

}
