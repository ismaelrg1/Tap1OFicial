package dynamic;


public interface InsultService {

    /**
     * Method Intercepted by a Proxy that sends an AddInsultMessage to an Actor
     * @param s -> String Message to add
     */
    public void addInsult(String s);

    /**
     * Method Intercepted by a Proxy that sends a GetAllInsultsMessage to an Actor
     * @return String with All Insults
     */
    public String getAllInsults();

    /**
     * Method Intercepted by a Proxy that sends a GetInsultMessage to an Actor
     * @return String with a random Insult
     */
    public String getInsult();
}
