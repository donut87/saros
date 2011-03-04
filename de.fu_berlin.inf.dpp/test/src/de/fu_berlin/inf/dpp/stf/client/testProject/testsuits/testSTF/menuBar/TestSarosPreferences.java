package de.fu_berlin.inf.dpp.stf.client.testProject.testsuits.testSTF.menuBar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.fu_berlin.inf.dpp.stf.STF;
import de.fu_berlin.inf.dpp.stf.client.testProject.testsuits.STFTest;
import de.fu_berlin.inf.dpp.stf.server.rmiSarosSWTBot.finder.remoteWidgets.STFBotShell;

public class TestSarosPreferences extends STFTest {

    @BeforeClass
    public static void runBeforeClass() throws RemoteException {
        initTesters(TypeOfTester.ALICE);
        setUpWorkbench();
        setUpSaros();
    }

    @After
    public void runAfterEveryTest() throws RemoteException {
        resetDefaultAccount();
    }

    @Test(expected = RuntimeException.class)
    public void createExistedAccountWithMenuSarosCreateAccount()
        throws RemoteException {
        alice.sarosBot().menuBar().saros()
            .creatAccount(alice.getJID(), PASSWORD);
    }

    /*
     * NOTE: createAccount can not be repeated tested. The reasons are:
     * 
     * 1.registered user can not be automatically deleted.
     * 
     * 2. It's not allowed to register account so fast.
     */
    @Test
    @Ignore
    public void createAccountWithButtonAddAccountInShellSarosPeferences()
        throws RemoteException {
        alice.sarosBot().menuBar().saros().preferences()
            .createAccount(JID_TO_CREATE, PASSWORD);
    }

    @Test
    public void createAccountWhichAlreadyExisted() throws RemoteException {
        alice.bot().menu(MENU_SAROS).menu(MENU_CREATE_ACCOUNT).click();

        alice.bot().waitUntilShellIsOpen(SHELL_CREATE_XMPP_JABBER_ACCOUNT);
        STFBotShell shell = alice.bot().shell(SHELL_CREATE_XMPP_JABBER_ACCOUNT);
        shell.activate();
        shell.bot().comboBoxWithLabel(LABEL_XMPP_JABBER_SERVER).setText(SERVER);
        shell.bot().textWithLabel(LABEL_USER_NAME)
            .setText(REGISTERED_USER_NAME);
        shell.bot().textWithLabel(LABEL_PASSWORD).setText(PASSWORD);
        shell.bot().textWithLabel(LABEL_REPEAT_PASSWORD).setText(PASSWORD);
        shell.bot().button(FINISH).click();

        // wait a minute,so that bot can get the error message.
        shell.bot().button(FINISH).waitUntilIsEnabled();
        assertTrue(shell.isActive());
        String errorMessage = shell.getErrorMessage();
        assertTrue(errorMessage.matches(ERROR_MESSAGE_ACCOUNT_ALREADY_EXISTS));
        shell.confirm(CANCEL);
        assertFalse(alice.bot().isShellOpen(SHELL_CREATE_XMPP_JABBER_ACCOUNT));

    }

    /**
     * TODO there are bugs: can't correctly check if the given passwords are
     * same or not
     * 
     * @throws RemoteException
     */
    @Test
    public void createAccountWithDismatchedPassword() throws RemoteException {
        alice.bot().menu(MENU_SAROS).menu(MENU_CREATE_ACCOUNT).click();

        alice.bot().waitUntilShellIsOpen(SHELL_CREATE_XMPP_JABBER_ACCOUNT);
        STFBotShell shell_alice = alice.bot().shell(
            SHELL_CREATE_XMPP_JABBER_ACCOUNT);
        shell_alice.activate();
        shell_alice.bot().comboBoxWithLabel(LABEL_XMPP_JABBER_SERVER)
            .setText(SERVER);
        shell_alice.bot().textWithLabel(LABEL_USER_NAME)
            .setText(NEW_XMPP_JABBER_ID);
        shell_alice.bot().textWithLabel(LABEL_PASSWORD).setText(PASSWORD);
        shell_alice.bot().textWithLabel(LABEL_REPEAT_PASSWORD)
            .setText(NO_MATCHED_REPEAT_PASSWORD);

        assertFalse(shell_alice.bot().button(FINISH).isEnabled());
        String errorMessage = shell_alice.getErrorMessage();
        assertTrue(errorMessage.equals(ERROR_MESSAGE_PASSWORDS_NOT_MATCH));
        shell_alice.confirm(CANCEL);
        assertFalse(alice.bot().isShellOpen(SHELL_CREATE_XMPP_JABBER_ACCOUNT));
    }

    /**
     * FIXME: by fist run you will get the error message
     * {@link STF#ERROR_MESSAGE_NOT_CONNECTED_TO_SERVER}, but by second run you
     * will get anther error message {@link STF#ERROR_MESSAGE_COULD_NOT_CONNECT}
     * 
     * 
     * @throws RemoteException
     */
    @Test
    @Ignore
    public void createAccountWithInvalidServer() throws RemoteException {
        alice.bot().menu(MENU_SAROS).menu(MENU_CREATE_ACCOUNT).click();
        alice.bot().waitUntilShellIsOpen(SHELL_CREATE_XMPP_JABBER_ACCOUNT);
        STFBotShell shell_alice = alice.bot().shell(
            SHELL_CREATE_XMPP_JABBER_ACCOUNT);
        shell_alice.activate();

        Map<String, String> labelsAndTexts = new HashMap<String, String>();
        labelsAndTexts.put(LABEL_XMPP_JABBER_SERVER, INVALID_SERVER_NAME);
        labelsAndTexts.put(LABEL_USER_NAME, NEW_XMPP_JABBER_ID);
        labelsAndTexts.put(LABEL_PASSWORD, PASSWORD);
        labelsAndTexts.put(LABEL_REPEAT_PASSWORD, PASSWORD);

        shell_alice.confirmWithTextFieldAndWait(labelsAndTexts, FINISH);

        shell_alice.bot().button(FINISH).waitLongUntilIsEnabled();

        String errorMessage = shell_alice.getErrorMessage();
        assertTrue(errorMessage.matches(ERROR_MESSAGE_COULD_NOT_CONNECT));
        shell_alice.confirm(CANCEL);
        assertFalse(alice.bot().isShellOpen(SHELL_CREATE_XMPP_JABBER_ACCOUNT));
    }

    @Test
    public void addAndActivateAccountNoGUI() throws RemoteException {
        assertFalse(alice.sarosBot().state()
            .isAccountExistNoGUI(JID_TO_ADD, PASSWORD));
        alice.sarosBot().menuBar().saros().preferences()
            .addAccount(JID_TO_ADD, PASSWORD);
        assertTrue(alice.sarosBot().state()
            .isAccountExistNoGUI(JID_TO_ADD, PASSWORD));
        assertTrue(alice.sarosBot().state()
            .isAccountActiveNoGUI(alice.getJID()));
        assertFalse(alice.sarosBot().state().isAccountActiveNoGUI(JID_TO_ADD));
        alice.noBot().activateAccountNoGUI(JID_TO_ADD);
        assertTrue(alice.sarosBot().state().isAccountActiveNoGUI(JID_TO_ADD));
        assertFalse(alice.sarosBot().state()
            .isAccountActiveNoGUI(alice.getJID()));
    }

    @Test
    public void addAndActivateAcount() throws RemoteException {
        assertFalse(alice.sarosBot().menuBar().saros().preferences()
            .existsAccount(JID_TO_ADD));
        alice.sarosBot().menuBar().saros().preferences()
            .addAccount(JID_TO_ADD, PASSWORD);
        assertTrue(alice.sarosBot().menuBar().saros().preferences()
            .existsAccount(JID_TO_ADD));
        assertTrue(alice.sarosBot().menuBar().saros().preferences()
            .isAccountActive(alice.getJID()));
        assertFalse(alice.sarosBot().menuBar().saros().preferences()
            .isAccountActive(JID_TO_ADD));
        alice.sarosBot().menuBar().saros().preferences()
            .activateAccount(JID_TO_ADD);
        assertTrue(alice.sarosBot().menuBar().saros().preferences()
            .isAccountActive(JID_TO_ADD));
        assertFalse(alice.sarosBot().menuBar().saros().preferences()
            .isAccountActive(alice.getJID()));
    }

    @Test
    public void editAccount() throws RemoteException {
        assertTrue(alice.sarosBot().menuBar().saros().preferences()
            .existsAccount(alice.getJID()));
        assertTrue(alice.sarosBot().menuBar().saros().preferences()
            .isAccountActive(alice.getJID()));
        alice.sarosBot().menuBar().saros().preferences()
            .changeAccount(alice.getJID(), NEW_XMPP_JABBER_ID, PASSWORD);
        assertFalse(alice.sarosBot().menuBar().saros().preferences()
            .existsAccount(alice.getJID()));
        assertFalse(alice.sarosBot().menuBar().saros().preferences()
            .isAccountActive(alice.getJID()));
        assertTrue(alice.sarosBot().menuBar().saros().preferences()
            .existsAccount(JID_TO_CHANGE));
        assertTrue(alice.sarosBot().menuBar().saros().preferences()
            .isAccountActive(JID_TO_CHANGE));
    }

    /*
     * FIXME After running the test the single active account would be deleted.
     * But you can still connect with the deleted account by clicking the
     * toolbar buton "connect".
     */
    @Test
    @Ignore("There may be some bugs existed.")
    public void deleteActiveAccountNoGUI() throws RemoteException {
        assertTrue(alice.sarosBot().state()
            .isAccountExistNoGUI(alice.getJID(), alice.getPassword()));
        alice.noBot().deleteAccountNoGUI(alice.getJID());
        assertFalse(alice.sarosBot().state()
            .isAccountExistNoGUI(alice.getJID(), alice.getPassword()));
    }

    @Test(expected = RuntimeException.class)
    @Ignore
    public void deleteActiveAccount() throws RemoteException {
        assertTrue(alice.sarosBot().menuBar().saros().preferences()
            .existsAccount(alice.getJID()));
        alice.sarosBot().menuBar().saros().preferences()
            .deleteAccount(alice.getJID(), alice.getPassword());
        assertTrue(alice.sarosBot().menuBar().saros().preferences()
            .isAccountActive(alice.getJID()));
        assertTrue(alice.sarosBot().menuBar().saros().preferences()
            .existsAccount(alice.getJID()));
    }

    @Test
    public void deleteInactiveAccount() throws RemoteException {
        assertFalse(alice.sarosBot().menuBar().saros().preferences()
            .existsAccount(JID_TO_ADD));
        alice.sarosBot().menuBar().saros().preferences()
            .addAccount(JID_TO_ADD, PASSWORD);
        assertTrue(alice.sarosBot().menuBar().saros().preferences()
            .existsAccount(JID_TO_ADD));
        alice.sarosBot().menuBar().saros().preferences()
            .deleteAccount(JID_TO_ADD, PASSWORD);
        assertFalse(alice.sarosBot().menuBar().saros().preferences()
            .existsAccount(JID_TO_ADD));
    }
}
