import org.junit.jupiter.api.DisplayName;

import java.util.List;

@DisplayName("ArrayList tests")
public class ArrayListTest extends ListTestBase {
    @Override
    protected List<Integer> createList() {
        return new ArrayList<>();
    }
}
