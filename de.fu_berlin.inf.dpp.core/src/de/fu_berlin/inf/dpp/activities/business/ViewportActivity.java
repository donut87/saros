package de.fu_berlin.inf.dpp.activities.business;

import de.fu_berlin.inf.dpp.activities.SPath;
import de.fu_berlin.inf.dpp.activities.serializable.IActivityDataObject;
import de.fu_berlin.inf.dpp.activities.serializable.ViewportActivityDataObject;
import de.fu_berlin.inf.dpp.session.User;

public class ViewportActivity extends AbstractResourceActivity {

    protected final int startLine;
    protected final int numberOfLines;

    public ViewportActivity(User source, int startLine, int numberOfLines,
        SPath path) {

        super(source, path);

        if (path == null)
            throw new IllegalArgumentException("path must not be null");

        this.startLine = Math.max(0, startLine);
        this.numberOfLines = Math.max(0, numberOfLines);
    }

    /**
     * Returns the number of lines that this viewport activity uses. </p> Note:
     * getStartLine() + getNumberOfLines = first line <b>after</b> viewport
     * range described by this activity.
     * 
     * @return
     */
    public int getNumberOfLines() {
        return this.numberOfLines;
    }

    /**
     * Returns the start line of this viewport activity.
     * 
     * @return
     */
    public int getStartLine() {
        return this.startLine;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + startLine;
        result = prime * result + numberOfLines;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof ViewportActivity))
            return false;

        ViewportActivity other = (ViewportActivity) obj;

        if (this.startLine != other.startLine)
            return false;
        if (this.numberOfLines != other.numberOfLines)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "ViewportActivity(path: " + getPath() + ", range: (" + startLine
            + "," + (startLine + numberOfLines) + "))";
    }

    @Override
    public void dispatch(IActivityReceiver receiver) {
        receiver.receive(this);
    }

    @Override
    public IActivityDataObject getActivityDataObject() {
        return new ViewportActivityDataObject(getSource(), startLine,
            numberOfLines, getPath());
    }
}