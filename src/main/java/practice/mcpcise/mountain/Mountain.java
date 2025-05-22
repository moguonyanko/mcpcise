package practice.mcpcise.mountain;

import java.util.List;
import java.util.Set;

public record Mountain(String id, String name, String nameKana, String area,
    Set<String> prefectures, int elevation, Location location, List<String> tags) {

}
