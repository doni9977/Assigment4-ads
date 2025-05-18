package src;
import java.util.List;

public interface Search<V> {
    List<V> pathTo(V target);

    double distanceTo(V target);
}