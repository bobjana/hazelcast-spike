package mu.cibecs.spike.hazelcast;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class AppEventListener implements MessageListener<AppEvent> {


    public void onMessage(Message<AppEvent> message) {
        AppEvent myEvent = message.getMessageObject();
        System.out.println("Message received = " + myEvent.toString());
    }
}
