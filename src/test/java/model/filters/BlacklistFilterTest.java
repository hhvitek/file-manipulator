package model.filters;

import model.filters.blacklist.BlacklistFilter;
import org.junit.jupiter.api.BeforeEach;

class BlacklistFilterTest extends FilterTest {

    @BeforeEach
    void init() {
        filter = new BlacklistFilter();
    }
}
