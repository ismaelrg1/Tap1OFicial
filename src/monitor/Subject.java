package monitor;

public interface Subject {
    public void subscribe(String name);

    public void subscribeAll();
    public void unSubscribe(String name);
    public void notifyChange(Events e);
}
