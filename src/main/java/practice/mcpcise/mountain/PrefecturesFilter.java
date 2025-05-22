package practice.mcpcise.mountain;

import java.util.Set;

public class PrefecturesFilter {
    private final Set<String> prefectures;

    public PrefecturesFilter(Set<String> prefectures) {
        this.prefectures = prefectures;
    }

    public boolean accept(Mountain mountain) {
      if (prefectures.isEmpty()) {
            return true; // 空のセットは全ての山を受け入れる
        }
        return mountain.prefectures().stream()
                .anyMatch(prefectures::contains);
    }
}
