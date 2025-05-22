package test.practice.mcpcise.mountain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import practice.mcpcise.mountain.MountainService;

public class TestMountainService {

  /**
   * 山の一覧を取得するテスト
   */
  @Test
  void testGetDefaultMountainList() {
    var mountainService = new MountainService();
    var result = mountainService.getDefaultMountainList();
    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  void testGetMountainList() {
    var mountainService = new MountainService();
    var prefs = Set.of("静岡県", "山梨県");
    var result = mountainService.getMountainList(5, prefs, "desc",
        1000, "<=");
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertTrue(result.size() == 5);
  }

}
