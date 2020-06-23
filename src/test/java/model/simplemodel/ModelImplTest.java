package model.simplemodel;

import model.IModelTest;
import org.junit.jupiter.api.BeforeEach;

class ModelImplTest extends IModelTest {
    @BeforeEach
    void init() {
        model = new ModelImpl();
    }
}
