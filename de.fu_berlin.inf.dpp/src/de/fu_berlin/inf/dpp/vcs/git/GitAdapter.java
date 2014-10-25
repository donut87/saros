package de.fu_berlin.inf.dpp.vcs.git;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.team.core.RepositoryProviderType;

import de.fu_berlin.inf.dpp.activities.VCSActivity;
import de.fu_berlin.inf.dpp.filesystem.IResource;
import de.fu_berlin.inf.dpp.negotiation.FileList;
import de.fu_berlin.inf.dpp.session.ISarosSession;
import de.fu_berlin.inf.dpp.vcs.VCSAdapter;
import de.fu_berlin.inf.dpp.vcs.VCSResourceInfo;

public class GitAdapter extends VCSAdapter {

    public GitAdapter(RepositoryProviderType provider) {
        super(provider);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRepositoryString(IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VCSResourceInfo getResourceInfo(IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VCSResourceInfo getCurrentResourceInfo(IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUrl(IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isManaged(org.eclipse.core.resources.IResource resource) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInManagedProject(
        org.eclipse.core.resources.IResource resource) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getRepositoryString(
        org.eclipse.core.resources.IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUrl(org.eclipse.core.resources.IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IProject checkoutProject(String newProjectName, FileList fileList,
        IProgressMonitor monitor) throws OperationCanceledException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(org.eclipse.core.resources.IResource resource,
        String targetRevision, IProgressMonitor monitor) {
        // TODO Auto-generated method stub

    }

    @Override
    public void switch_(org.eclipse.core.resources.IResource resource,
        String url, String revision, IProgressMonitor monitor) {
        // TODO Auto-generated method stub

    }

    @Override
    public void revert(org.eclipse.core.resources.IResource resource,
        SubMonitor monitor) {
        // TODO Auto-generated method stub

    }

    @Override
    public VCSResourceInfo getResourceInfo(
        org.eclipse.core.resources.IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VCSResourceInfo getCurrentResourceInfo(
        org.eclipse.core.resources.IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void connect(IProject project, String repositoryRoot,
        String directory, IProgressMonitor progress) {
        // TODO Auto-generated method stub

    }

    @Override
    public void disconnect(IProject project, boolean deleteContent,
        IProgressMonitor progress) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean hasLocalCache(IProject project) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public VCSActivity getUpdateActivity(ISarosSession sarosSession,
        org.eclipse.core.resources.IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VCSActivity getSwitchActivity(ISarosSession sarosSession,
        org.eclipse.core.resources.IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

}
