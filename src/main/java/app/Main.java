package app;

import app.controller.AbstractController;
import app.controller.ISupportedActionsForViewByController;
import app.controller.SimpleController;
import app.model.Model;
import app.model.simplemodel.ModelImpl;
import app.model.simplemodel.staticdata.IModelStaticData;
import app.model.simplemodel.staticdata.jpa.ModelStaticDataJpaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import app.view.AbstractView;
import app.view.MainForm;
import app.view.SwingViewUtils;

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

        //IModelStaticData modelStaticData = new ModelStaticDataFromEnumImpl();
        //IModelStaticData modelStaticData = new ModelStaticDataFromFileImpl();
        IModelStaticData modelStaticData = new ModelStaticDataJpaImpl();
        Model model = new ModelImpl(modelStaticData);

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