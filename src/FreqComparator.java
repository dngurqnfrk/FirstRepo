import java.util.Comparator;
import java.util.List;

public class FreqComparator implements Comparator<String> {
    List<String> criteria;

    @Override
    public int compare(String o1, String o2) {
        int i1 = criteria.indexOf(o1);
        int i2 = criteria.indexOf(o2);
        if(i1 == i2)
            return 0;
        else
            return i1 < i2 ? -1 : 1;
    }

    // Constructor
    FreqComparator(List<String> _criteria) {
        criteria = _criteria;
    }
}
