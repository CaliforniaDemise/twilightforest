package twilightforest.util;

import java.util.Set;

public interface ReloadableSet<K> extends Set<K> {
    void setReload();
    void reload();
}
