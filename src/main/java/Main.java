import controller.AbstractController;
import controller.ISupportedActionsForViewByController;
import controller.SimpleController;
import model.Model;
import model.simplemodel.ModelImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.AbstractView;
import view.MainForm;
import view.SwingViewUtils;

import javax.swing.*;

public final class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
            throws ClassNotFoundException,
            UnsupportedLookAndFeelException,
            InstantiationException,
            IllegalAccessException {

        logger.info("Application started.");

        SwingViewUtils.setLookAndFeelToSystemDefault();
        SwingViewUtils.setDefaultFont();

        Model model = new ModelImpl();

        AbstractController controller = new SimpleController(model);
        AbstractView view = new MainForm(model, (ISupportedActionsForViewByController)controller);
        controller.addView(view);

        view.startView();
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
