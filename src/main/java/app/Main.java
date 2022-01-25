package app;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.controller.AbstractController;
import app.controller.ISupportedActionsForViewByController;
import app.controller.SimpleController;
import app.model.Model;
import app.model.simplemodel.ModelImpl;
import app.model.simplemodel.staticdata.IModelStaticData;
import app.model.simplemodel.staticdata.ModelStaticDataFromFileImpl;
import app.view.AbstractView;
import app.view.MainForm;
import app.view.SwingViewUtils;

public final class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("Application started.");

        SwingViewUtils.setLookAndFeelToFlatLaf();

        //IModelStaticData modelStaticData = new ModelStaticDataFromEnumImpl();
        IModelStaticData modelStaticData = new ModelStaticDataFromFileImpl();
        //IModelStaticData modelStaticData = new ModelStaticDataJpaImpl();
        Model model = new ModelImpl(modelStaticData);

        AbstractController controller = new SimpleController(model);
        AbstractView view = new MainForm(model, (ISupportedActionsForViewByController)controller);
        controller.addView(view);

        view.startView();
    }


}