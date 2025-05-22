package practice.mcpcise.mountain;

import java.util.List;

public record MountainList(List<Mountain> mountains) {
  
  public int size() {
    return mountains.size();
  }

  public boolean isEmpty() {
    return mountains.isEmpty();
  }

}
