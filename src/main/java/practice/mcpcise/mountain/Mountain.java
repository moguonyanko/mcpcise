package practice.mcpcise.mountain;

import java.util.List;

public record Mountain(String id, String name, String nameKana, String area,
    List<String> prefectures, int elevation, Location location, List<String> tags) {

}
