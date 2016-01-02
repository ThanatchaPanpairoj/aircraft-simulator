import java.util.Comparator;

/**
 * Write a description of class FaceDistanceComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FaceDistanceComparator implements Comparator<Face>
{
    public int compare(Face t1, Face t2) {
        return t2.getDistance() - t1.getDistance();
    }
}
