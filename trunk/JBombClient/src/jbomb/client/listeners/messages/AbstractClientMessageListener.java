package jbomb.client.listeners.messages;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.concurrent.Callable;
import jbomb.client.game.ClientContext;
import jbomb.client.messages.task.Task;

public abstract class AbstractClientMessageListener<T extends Task<U>, U extends Message>
implements MessageListener<Client> {
    
    protected void doTask(final T task, final U message) {
        
        ClientContext.APP.enqueue(new Callable<Void>(){
            
            @Override
            public Void call() throws Exception {
                task.doThis(message);
                return null;
            }
            
        });
    }
}
