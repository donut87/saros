package de.fu_berlin.inf.dpp.stf.client.test.testcases.initialising;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.fu_berlin.inf.dpp.stf.client.Musician;
import de.fu_berlin.inf.dpp.stf.client.test.helpers.InitMusician;
import de.fu_berlin.inf.dpp.stf.server.SarosConstant;

public class TestHandleContacts {

    protected static Musician bob;
    protected static Musician alice;

    @BeforeClass
    public static void initMusicians() {
        bob = InitMusician.newBob();
        alice = InitMusician.newAlice();
    }

    @AfterClass
    public static void resetSaros() throws RemoteException {
        bob.bot.resetSaros();
        alice.bot.resetSaros();
    }

    @Before
    public void setUp() throws RemoteException {
        alice.rosterV.addContact(bob.jid, bob.bot);
        bob.rosterV.addContact(alice.jid, alice.bot);
    }

    @After
    public void cleanUp() throws RemoteException {
        alice.bot.resetWorkbench();
        bob.bot.resetWorkbench();
    }

    // FIXME these testAddContact assumes that testRemoveContact succeeds
    // FIXME all the other tests in the suite would fail if testAddContact fails

    @Test
    public void testBobRemoveContactAlice() throws RemoteException {
        assertTrue(alice.rosterV.hasContactWith(bob.jid));
        assertTrue(bob.rosterV.hasContactWith(alice.jid));
        bob.rosterV.deleteContact(alice.jid, alice.bot);
        assertFalse(bob.rosterV.hasContactWith(alice.jid));
        assertFalse(alice.rosterV.hasContactWith(bob.jid));
    }

    @Test
    public void testAliceRemoveContactBob() throws RemoteException {
        assertTrue(alice.rosterV.hasContactWith(bob.jid));
        assertTrue(bob.rosterV.hasContactWith(alice.jid));
        alice.rosterV.deleteContact(bob.jid, bob.bot);
        assertFalse(bob.rosterV.hasContactWith(alice.jid));
        assertFalse(alice.rosterV.hasContactWith(bob.jid));
    }

    @Test
    public void testAliceAddContactBob() throws RemoteException {
        alice.rosterV.deleteContact(bob.jid, bob.bot);
        alice.rosterV.addContact(bob.jid, bob.bot);
        assertTrue(bob.rosterV.hasContactWith(alice.jid));
        assertTrue(alice.rosterV.hasContactWith(bob.jid));
    }

    @Test
    public void testBobAddContactAlice() throws RemoteException {
        bob.rosterV.deleteContact(alice.jid, alice.bot);
        bob.rosterV.addContact(alice.jid, alice.bot);
        assertTrue(bob.rosterV.hasContactWith(alice.jid));
        assertTrue(alice.rosterV.hasContactWith(bob.jid));
    }

    @Test
    public void testAddNoValidContact() throws RemoteException {
        alice.rosterV.clickTBAddANewContactInRosterView();
        alice.popupWindow.confirmNewContactWindow("bob@bla");
        alice.eclipseWindow.waitUntilShellActive("Contact look-up failed");
        assertTrue(alice.eclipseWindow.isShellActive("Contact look-up failed"));
        alice.eclipseWindow.confirmWindow("Contact look-up failed",
            SarosConstant.BUTTON_NO);
    }

    @Test
    public void testAddExistedContact() throws RemoteException {
        alice.rosterV.clickTBAddANewContactInRosterView();
        alice.popupWindow.confirmNewContactWindow(bob.getBaseJid());
        alice.eclipseWindow.waitUntilShellActive("Contact already added");
        assertTrue(alice.eclipseWindow.isShellActive("Contact already added"));
        alice.eclipseWindow.closeShell("Contact already added");
    }

}
