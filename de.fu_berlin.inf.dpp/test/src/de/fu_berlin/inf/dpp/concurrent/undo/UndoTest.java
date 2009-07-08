package de.fu_berlin.inf.dpp.concurrent.undo;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import de.fu_berlin.inf.dpp.concurrent.jupiter.Operation;
import de.fu_berlin.inf.dpp.concurrent.jupiter.internal.text.DeleteOperation;
import de.fu_berlin.inf.dpp.concurrent.jupiter.internal.text.InsertOperation;
import de.fu_berlin.inf.dpp.concurrent.jupiter.internal.text.NoOperation;
import de.fu_berlin.inf.dpp.concurrent.jupiter.internal.text.SplitOperation;
import de.fu_berlin.inf.dpp.concurrent.undo.OperationHistory.Type;

/**
 * testing TextOperationHistory and UndoManager
 */
public class UndoTest extends TestCase {

    // private static Logger log = Logger.getLogger(UndoTest.class.getName());

    protected IPath path1 = new Path("path1");
    protected IPath path2 = new Path("path2");

    protected OperationHistory history;
    protected UndoManager undoManager;

    @Override
    public void setUp() {
        undoManager = new UndoManager();
        history = undoManager.getHistory();
    }

    protected Operation nop() {
        return new NoOperation();
    }

    protected Operation undo(IPath path) {
        return undoManager.calcUndoOperation(path);
    }

    protected Operation redo(IPath path) {
        return undoManager.calcRedoOperation(path);
    }

    public void testEmptyHistory() {
        assertEquals(nop(), undo(path1));
        assertEquals(nop(), undo(path2));
        assertEquals(nop(), redo(path1));
    }

    public void testHistoryWithoutLocals() {
        history.add(path1, Type.REMOTE, new InsertOperation(5, "abc"));
        history.add(path1, Type.REMOTE, new DeleteOperation(11, "ghi"));

        assertEquals(nop(), undo(path1));
        assertEquals(nop(), undo(path2));
        assertEquals(nop(), redo(path1));
    }

    public void testHistoryWithoutRemotes() {
        history.add(path1, Type.LOCAL, new InsertOperation(5, "abc"));
        history.add(path1, Type.LOCAL, new DeleteOperation(11, "ghi"));

        Operation expected = new InsertOperation(11, "ghi");

        assertEquals(nop(), redo(path1));
        assertEquals(expected, undo(path1));
        assertEquals(expected.invert(), redo(path1));

        assertEquals(nop(), undo(path2));
    }

    public void testMixedHistoryWithOnePath() {
        // Text: 0123456789
        history.add(path1, Type.LOCAL, new InsertOperation(8, "first")); // 01234567first89
        history.add(path1, Type.REMOTE, new InsertOperation(2, "XXX")); // 01XXX234567first89
        history.add(path1, Type.LOCAL, new InsertOperation(5, "sec")); // 01XXXsec234567first89
        history.add(path1, Type.REMOTE, new DeleteOperation(8, "234567")); // 01XXXsecfirst89
        history.add(path1, Type.REMOTE, new DeleteOperation(1, "1X")); // 0XXsecfirst89

        Operation expected = new DeleteOperation(3, "sec");

        assertEquals(expected, undo(path1));
        assertEquals(expected.invert(), redo(path1));
        assertEquals(nop(), undo(path2));
    }

    public void testMixedHistoryWithTwoPaths() {
        // Text: 0123456789
        history.add(path1, Type.LOCAL, new InsertOperation(8, "first")); // 01234567first89
        history.add(path1, Type.REMOTE, new InsertOperation(2, "XXX")); // 01XXX234567first89
        history.add(path1, Type.LOCAL, new InsertOperation(5, "sec")); // 01XXXsec234567first89
        history.add(path1, Type.REMOTE, new DeleteOperation(8, "234567")); // 01XXXsecfirst89
        history.add(path1, Type.REMOTE, new DeleteOperation(1, "1X")); // 0XXsecfirst89

        Operation expected1 = new DeleteOperation(3, "sec");

        // Text in path2: 123456
        history.add(path2, Type.REMOTE, new DeleteOperation(0, "123")); // 456
        history.add(path2, Type.LOCAL, new InsertOperation(0, "ace")); // ace456
        history.add(path2, Type.REMOTE, new InsertOperation(1, "b")); // abce456
        history.add(path2, Type.REMOTE, new InsertOperation(3, "d")); // abcde456
        history.add(path2, Type.REMOTE, new DeleteOperation(5, "456")); // abcde

        Operation expected2 = new SplitOperation(new DeleteOperation(0, "a"),
            new SplitOperation(new DeleteOperation(1, "c"),
                new DeleteOperation(2, "e")));

        assertEquals(nop(), redo(path1));
        assertEquals(expected1, undo(path1));
        assertEquals(expected1.invert(), redo(path1));

        assertEquals(nop(), redo(path2));
        assertEquals(expected2, undo(path2));
        assertEquals(expected2.invert(), redo(path2));
    }

    public void testTwoUndos() {
        // Text: 0123456789
        history.add(path1, Type.LOCAL, new InsertOperation(8, "first")); // 01234567first89
        history.add(path1, Type.REMOTE, new InsertOperation(2, "XXX")); // 01XXX234567first89
        history.add(path1, Type.LOCAL, new InsertOperation(5, "sec")); // 01XXXsec234567first89
        history.add(path1, Type.REMOTE, new DeleteOperation(8, "234567")); // 01XXXsecfirst89
        history.add(path1, Type.REMOTE, new DeleteOperation(1, "1XX")); // 0Xsecfirst89

        Operation expected1 = new DeleteOperation(2, "sec");
        // first undo -> 0Xfirst89
        Operation expected2 = new DeleteOperation(2, "first");
        // second undo -> 0X89

        assertEquals(expected1, undo(path1)); // 0Xfirst89
        assertEquals(expected2, undo(path1)); // 0X89
        assertEquals(expected2.invert(), redo(path1)); // 0Xfirst89
        assertEquals(expected1.invert(), redo(path1)); // 0Xsecfirst89
    }

    public void testRedoAfterNewRemotes() {
        // Text: 0123456789
        history.add(path1, Type.LOCAL, new InsertOperation(8, "first")); // 01234567first89
        history.add(path1, Type.REMOTE, new InsertOperation(2, "XXX")); // 01XXX234567first89
        history.add(path1, Type.LOCAL, new InsertOperation(5, "sec")); // 01XXXsec234567first89
        history.add(path1, Type.REMOTE, new DeleteOperation(8, "234567")); // 01XXXsecfirst89
        history.add(path1, Type.REMOTE, new DeleteOperation(1, "1X")); // 0XXsecfirst89

        Operation expected = new DeleteOperation(3, "sec");
        // first undo -> 0XXfirst89
        assertEquals(expected, undo(path1));

        history.add(path1, Type.REMOTE, new InsertOperation(1, "123")); // 0123XXfirst89

        expected = new DeleteOperation(6, "first");
        // second undo -> 0123XX89
        assertEquals(expected, undo(path1));

        history.add(path1, Type.REMOTE, new DeleteOperation(4, "XX")); // 012389

        expected = new InsertOperation(4, "first", 6);
        // first redo (redoes second undo) -> 0123first89
        assertEquals(expected, redo(path1));

        history.add(path1, Type.REMOTE, new DeleteOperation(0, "0123")); // first89

        expected = new InsertOperation(0, "sec", 3);
        // second redo -> secfirst89

        assertEquals(expected, redo(path1));
    }

    public void testUndoRedoUndo() {
        history.add(path1, Type.LOCAL, new InsertOperation(3, "abc"));
        history.add(path1, Type.LOCAL, new InsertOperation(6, "defghi"));

        undo(path1);
        redo(path1);

        Operation expected = new DeleteOperation(6, "defghi");
        assertEquals(expected, undo(path1));
    }

    public void testUndoUndoRedoRedoUndo() {
        history.add(path1, Type.LOCAL, new InsertOperation(3, "abc"));
        history.add(path1, Type.LOCAL, new InsertOperation(6, "defghi")); // abcdefghi
        history.add(path1, Type.LOCAL, new DeleteOperation(5, "cde")); // abfghi

        undo(path1); // abcdefghi
        undo(path1); // abc
        redo(path1); // abcdefghi

        Operation expected = new DeleteOperation(5, "cde");
        assertEquals(expected, redo(path1));

        expected = new InsertOperation(5, "cde");
        assertEquals(expected, undo(path1));
    }
}
