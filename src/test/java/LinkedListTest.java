import org.junit.jupiter.api.DisplayName;

import java.util.List;

@DisplayName("LinkedList tests")
public class LinkedListTest extends ListTestBase {
    @Override
    protected List<Integer> createList() {
        return new LinkedList<>();
    }
}
