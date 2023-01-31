package monitor;

import actor.MessageInterface;

import java.util.Map;

public interface Observer {
    public void subjectNotification(Events e);

    public void subjectNotification(Events e, MessageInterface msg);

    public int getNumMessages();

    public String getName();

}
