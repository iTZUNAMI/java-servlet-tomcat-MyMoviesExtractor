package mypackage;

import database.Database;
import exception.DBProblemException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author supremo_zim
 */

public class Utente {
    
    private Database db;
    
    public Utente() throws SQLException, ClassNotFoundException, DBProblemException{
         try {
            this.db = Database.getDb();
        } catch (DBProblemException ex) {
           
            throw ex;
        }
        
    }
    /**
     * Holds value of property nome.
     */
    private String nome;

    /**
     * Getter for property nome.
     * @return Value of property nome.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Setter for property nome.
     * @param nome New value of property nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean checkPassword(String password)
    {
        if ("pass".equals(password))
            return true;
        else
            return false;
    }
    
    public boolean checkLogin(String nick, String pass) throws SQLException, ClassNotFoundException{
        PreparedStatement pst = db.getConnection().prepareStatement("SELECT NICKNAME "
                                        + "FROM APP.METEO_UTENTI "
                                        + "WHERE NICKNAME = ? "
                                        + "AND PASSWORD = ? ");
        pst.setString(1, nick);
        pst.setString(2, pass);
        ResultSet rs = pst.executeQuery();
        try {
            rs.next();
            return rs.getString("NICKNAME").equals(nick);
        }
        catch (Exception e) {
            return false;
        }
    }
}