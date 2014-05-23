package de.fu_berlin.inf.dpp.activities.business;

import org.apache.commons.lang.ObjectUtils;

import de.fu_berlin.inf.dpp.activities.SPath;
import de.fu_berlin.inf.dpp.session.User;

public abstract class AbstractResourceActivity extends AbstractActivity
    implements IResourceActivity {

    /*
     * TODO path must never be null for IResourceActivities. Add a
     * StatusActivity for informing remote users that no shared resource is
     * active anymore.
     */
    private final SPath path;

    public AbstractResourceActivity(User source, SPath path) {
        super(source);

        this.path = path;
    }

    @Override
    public SPath getPath() {
        return path;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ObjectUtils.hashCode(path);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof AbstractResourceActivity))
            return false;

        AbstractResourceActivity other = (AbstractResourceActivity) obj;

        if (!ObjectUtils.equals(this.path, other.path))
            return false;

        return true;
    }
}