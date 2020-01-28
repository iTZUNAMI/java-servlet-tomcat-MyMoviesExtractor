package exception;


/**
 *
 * @author GruppoMeteo
 */
public class QueryProblemException extends Exception{

    public QueryProblemException(Exception ex) {
       super(ex);
    }

}
