package app.utilities.operations.whitelist;

import app.utilities.string_filters.operations.whitelist.WhitelistCharacterFilter;
import org.junit.jupiter.api.BeforeEach;

class WhitelistCharacterFilterTest extends WhitelistFilterTest {

    @BeforeEach
    void init() {
        operation = new WhitelistCharacterFilter();
    }

}