import controller.IController;
import controller.SimpleController;
import model.IModel;
import model.simplemodel.SimpleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.IView;
import view.SimpleView;

import javax.swing.*;
import java.awt.*;

public final class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
            throws ClassNotFoundException,
            UnsupportedLookAndFeelException,
            InstantiationException,
            IllegalAccessException {

        logger.info("Application started.");

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setUIFont(new javax.swing.plaf.FontUIResource("Segoe UI", Font.PLAIN, 14));

        IModel model = new SimpleModel();
        IController controller = new SimpleController(model);
        IView view = new SimpleView(model, controller);
        view.createView();
    }


    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }
}
