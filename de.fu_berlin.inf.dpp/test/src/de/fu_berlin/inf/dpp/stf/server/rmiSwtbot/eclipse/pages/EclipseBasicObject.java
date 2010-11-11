package de.fu_berlin.inf.dpp.stf.server.rmiSwtbot.eclipse.pages;

import java.rmi.RemoteException;

import de.fu_berlin.inf.dpp.stf.server.rmiSwtbot.eclipse.EclipseObject;
import de.fu_berlin.inf.dpp.stf.server.rmiSwtbot.eclipse.saros.SarosRmiSWTWorkbenchBot;

public class EclipseBasicObject extends EclipseObject implements
    IEclipseBasicObject {

    public static EclipseBasicObject classVariable;
    private static final boolean SCREENSHOTS = true;

    public EclipseBasicObject(SarosRmiSWTWorkbenchBot rmiBot) {
        super(rmiBot);
    }

    public void sleep(long millis) throws RemoteException {
        bot.sleep(millis);
    }

    public void captureScreenshot(String filename) throws RemoteException {
        if (SCREENSHOTS)
            bot.captureScreenshot(filename);
    }

    // // FIXME If the file doesn't exist, this method hits the
    // // SWTBotPreferences.TIMEOUT (5000ms) while waiting on a tree node.
    // public boolean isJavaClassExistInGui(String projectName, String pkg,
    // String className) throws RemoteException {
    // showViewPackageExplorer();
    // activatePackageExplorerView();
    // return isTreeItemExist(SarosConstant.VIEW_TITLE_PACKAGE_EXPLORER,
    // projectName, "src", pkg, className + ".java");
    // }

    public boolean isTextWithLabelEqualWithText(String label, String text)
        throws RemoteException {
        return bot.textWithLabel(label).getText().equals(text);
    }

    public void clickButton(String mnemonicText) throws RemoteException {
        bot.button(mnemonicText).click();
    }

}
