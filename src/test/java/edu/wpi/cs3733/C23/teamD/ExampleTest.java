package edu.wpi.cs3733.C23.teamD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExampleTest {

  @Test
  @Disabled("Not implemented yet") /* for tests that have not been implemented yet,
  will throw an error org.junit.platform.launcher.core.EngineDiscoveryOrchestrator lambda$logTestDescriptorExclusionReasons$7 */
  @DisplayName("Should demonstrate a simple assertion") // can annotate tests
  void shouldShowSimpleAssertion() {
    assertEquals(1, 1);
  }

  @Test
  @Disabled
  @DisplayName("Should check all items in the list")
  /* What's the problem with just this? If first one fails, then we don't know
  that the rest of them also fail.
  void shouldCheckAllItemsInTheList() {
    List<Integer> numbers = List.of(2, 3, 5, 7);
    Assertions.assertEquals(1, numbers.get(0));
    Assertions.assertEquals(1, numbers.get(1));
    Assertions.assertEquals(1, numbers.get(2));
    Assertions.assertEquals(1, numbers.get(3));
  }
  // INSTEAD, do this */
  void shouldCheckAllItemsInTheList() {
    List<Integer> numbers = List.of(1, 1, 1, 1);
    // With this, JUnit will run all the assertions for the test
    Assertions.assertAll(
        () -> assertEquals(1, numbers.get(0)),
        () -> assertEquals(1, numbers.get(0)),
        () -> assertEquals(1, numbers.get(1)),
        () -> assertEquals(1, numbers.get(2)),
        () -> assertEquals(1, numbers.get(3)));
  }
}
