package de.fu_berlin.inf.dpp.activities.serializable;

import org.apache.commons.lang.ObjectUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import de.fu_berlin.inf.dpp.activities.SPath;
import de.fu_berlin.inf.dpp.session.User;

public abstract class AbstractProjectActivityDataObject extends
    AbstractActivityDataObject {

    @XStreamAlias("p")
    protected SPath path;

    public AbstractProjectActivityDataObject(User source, SPath path) {
        super(source);

        this.path = path;
    }

    /**
     * @return may be <code>null</code>, depending on implementation
     */
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
        if (!(obj instanceof AbstractProjectActivityDataObject))
            return false;

        AbstractProjectActivityDataObject other = (AbstractProjectActivityDataObject) obj;

        if (!ObjectUtils.equals(this.path, other.path))
            return false;

        return true;
    }
}