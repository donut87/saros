package de.fu_berlin.inf.dpp.activities.serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import de.fu_berlin.inf.dpp.activities.business.IActivity;
import de.fu_berlin.inf.dpp.activities.business.StopFollowingActivity;
import de.fu_berlin.inf.dpp.session.User;

/**
 * DataObject for the event that a user stopped following another user
 */
@XStreamAlias("stopFollowingActivity")
public class StopFollowingActivityDataObject extends AbstractActivityDataObject {

    public StopFollowingActivityDataObject(User source) {
        super(source);
    }

    @Override
    public IActivity getActivity() {
        return new StopFollowingActivity(getSource());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof StopFollowingActivityDataObject))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "StopFollowingActivityDO(source: " + getSource() + ")";
    }
}