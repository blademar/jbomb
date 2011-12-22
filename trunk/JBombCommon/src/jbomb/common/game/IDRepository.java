package jbomb.common.game;

import java.util.HashMap;
import java.util.Map;
import jbomb.common.exception.NotAvailableId;

public class IDRepository {
    
    private Map<Long, Boolean> idMap = new HashMap<Long, Boolean>();
    
    public void occupyIn(long l) {
        idMap.put(l, false);
    }
    
    public long nextFree() throws NotAvailableId {
        for (long l : idMap.keySet())
            if (idMap.get(l)) {
                idMap.put(l, false);
                return l;
            }
        throw new NotAvailableId();
    }
    
    public void releaseIn(long l) {
        idMap.put(l, true);
    }
}
