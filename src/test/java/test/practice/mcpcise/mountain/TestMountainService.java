package test.practice.mcpcise.mountain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}
