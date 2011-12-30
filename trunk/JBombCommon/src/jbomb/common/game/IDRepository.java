package jbomb.common.game;

import java.util.HashMap;
import java.util.Map;

public class IDRepository {
    
    private Map<Long, Boolean> idMap = new HashMap<Long, Boolean>();
    private long count = 0;
    
    public void occupyIn(long l) {
        idMap.put(l, false);
    }
    
    public long nextFree() {
        for (long l : idMap.keySet())
            if (idMap.get(l)) {
                idMap.put(l, false);
                return l;
            }
        occupyIn(count);
        count++;
        return count - 1;
    }
    
    public void releaseIn(long l) {
        idMap.put(l, true);
    }
    
    public void reserveID(byte num) {
        for (int i = 0; i < num; i++) {
            occupyIn(i);
            count++;
        }
    }
}
