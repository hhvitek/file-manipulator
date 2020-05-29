package model.filters.operations.blacklist;

import org.junit.jupiter.api.BeforeEach;

class BlacklistCharacterFilterTest extends BlacklistFilterTest {

    @BeforeEach
    void init() {
        filter = new BlacklistCharacterFilter();
    }
}
