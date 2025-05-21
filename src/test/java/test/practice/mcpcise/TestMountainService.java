package test.practice.mcpcise;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import practice.mcpcise.MountainService;

public class TestMountainService {

  /**
   * 山の一覧を取得するテスト
   */
  @Test
  void testGetMountainList() {
    var mountainService = new MountainService();
    var result = mountainService.getMountainList(5, "desc");
    assertNotNull(result);
    assertTrue(result.contains("槍ヶ岳"));
  }  
}
