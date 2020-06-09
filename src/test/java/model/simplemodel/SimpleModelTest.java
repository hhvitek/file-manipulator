package model.simplemodel;

import model.IModelTest;
import org.junit.jupiter.api.BeforeEach;

class SimpleModelTest extends IModelTest {
    @BeforeEach
    void init() {
        model = new SimpleModel();
    }
}