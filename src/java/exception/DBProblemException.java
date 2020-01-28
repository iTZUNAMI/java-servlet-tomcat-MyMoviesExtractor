package exception;

/**
 *
 * @author GruppoMeteo
 */
public class DBProblemException extends Exception{

    public DBProblemException(Exception ex) {
        super(ex);
    }


}
