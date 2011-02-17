package de.fu_berlin.inf.dpp.stf.server.rmiSarosSWTBot.finder.remoteWidgets;

import java.rmi.RemoteException;

import de.fu_berlin.inf.dpp.stf.server.rmiSarosSWTBot.sarosFinder.remoteComponents.EclipseComponent;

public interface STFBotTableItem extends EclipseComponent {

    /**********************************************
     * 
     * states
     * 
     **********************************************/

    public abstract STFBotMenu contextMenu(String text) throws RemoteException;

    public abstract void select() throws RemoteException;

    public abstract void check() throws RemoteException;

    public abstract void uncheck() throws RemoteException;

    public abstract void toggleCheck() throws RemoteException;

    public abstract void click() throws RemoteException;

    public abstract void clickAndWait() throws RemoteException;

    public abstract void setFocus() throws RemoteException;

    /**********************************************
     * 
     * states
     * 
     **********************************************/
    public abstract boolean existsContextMenu(String contextName)
        throws RemoteException;

    public abstract boolean isEnabled() throws RemoteException;

    public abstract boolean isVisible() throws RemoteException;

    public abstract boolean isActive() throws RemoteException;

    public abstract boolean isChecked() throws RemoteException;

    public abstract boolean isGrayed() throws RemoteException;

    public abstract String getText(int index) throws RemoteException;

    public abstract String getText() throws RemoteException;

    public abstract String getToolTipText() throws RemoteException;

    /**********************************************
     * 
     * waits until
     * 
     **********************************************/
    public abstract void waitUntilIsEnabled() throws RemoteException;

}