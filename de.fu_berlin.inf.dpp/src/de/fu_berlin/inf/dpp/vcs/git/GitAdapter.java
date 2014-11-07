package de.fu_berlin.inf.dpp.vcs.git;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.team.core.RepositoryProvider;
import org.eclipse.team.core.RepositoryProviderType;

import de.fu_berlin.inf.dpp.activities.VCSActivity;
import de.fu_berlin.inf.dpp.filesystem.ResourceAdapterFactory;
//import de.fu_berlin.inf.dpp.filesystem.IResource;
import de.fu_berlin.inf.dpp.negotiation.FileList;
import de.fu_berlin.inf.dpp.session.ISarosSession;
import de.fu_berlin.inf.dpp.vcs.VCSAdapter;
import de.fu_berlin.inf.dpp.vcs.VCSResourceInfo;

public class GitAdapter extends VCSAdapter {

    static final String identifier = "org.eclipse.egit.core.GitProvider";

    @SuppressWarnings("hiding")
    protected static final Logger log = Logger.getLogger(GitAdapter.class);

    public GitAdapter(RepositoryProviderType provider) {
        super(provider);
    }

    @Override
    public String getID() {
        return identifier;
    }

    @Override
    public String getRepositoryString(IResource resource) {
        Repository gitRepo = getGitRepoForResource(resource);
        return (gitRepo == null) ? null : gitRepo.getWorkTree().getPath();
    }

    @Override
    public String getUrl(IResource resource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isManaged(org.eclipse.core.resources.IResource resource) {
        return getGitRepoForResource(resource) != null;
    }

    @Override
    public boolean isInManagedProject(
        org.eclipse.core.resources.IResource resource) {
        IProject project = resource.getProject();
        if (!RepositoryProvider.isShared(project)) {
            return false;
        }
        return RepositoryProvider.getProvider(project).getID()
            .equals(identifier);
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
        // In git this is of course 'git checkout'

    }

    @Override
    public void switch_(org.eclipse.core.resources.IResource resource,
        String url, String revision, IProgressMonitor monitor) {
        // TODO Auto-generated method stub
        Repository gitRepo = this.getGitRepoForResource(resource);
        if (gitRepo == null) {
            return;
        }
        Git git = new Git(gitRepo);
        git.checkout().setStartPoint(revision)
            .addPath(resource.getLocation().toOSString());
    }

    @Override
    public void revert(org.eclipse.core.resources.IResource resource,
        SubMonitor monitor) {
        // In git this is of course 'git checkout'
        Repository gitRepo = this.getGitRepoForResource(resource);
        if (gitRepo == null) {
            return;
        }
        Git git = new Git(gitRepo);
        try {
            git.checkout().addPath(resource.getLocation().toOSString()).call();
        } catch (RefAlreadyExistsException e1) {
            // TODO Auto-generated catch block
            log.debug("", e1);
        } catch (RefNotFoundException e1) {
            // TODO Auto-generated catch block
            log.debug("", e1);
        } catch (InvalidRefNameException e1) {
            // TODO Auto-generated catch block
            log.debug("", e1);
        } catch (CheckoutConflictException e1) {
            // TODO Auto-generated catch block
            log.debug("Checkout not possible due to conflicts.", e1);
        } catch (GitAPIException e1) {
            // TODO Auto-generated catch block
            log.debug("", e1);
        }
        try {
            resource.refreshLocal(
                org.eclipse.core.resources.IResource.DEPTH_INFINITE, null);
        } catch (CoreException e) {
            log.error("Refresh failed", e);
        }

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

    private Repository getGitRepoForResource(IResource resource) {
        if (resource == null) {
            log.debug("Null Resource given.");
            return null;
        }
        File f = new File(resource.getLocation().toOSString());
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        builder.findGitDir(f);
        try {
            if (builder.getGitDir() != null) {
                return builder.build();
            }
        } catch (IOException e) {
            log.debug("The resource " + resource.getLocation().toOSString()
                + " was not found in file system.", e);
        }
        return null;
    }

    @Override
    public String getRepositoryString(
        de.fu_berlin.inf.dpp.filesystem.IResource resource) {
        return getRepositoryString(ResourceAdapterFactory.convertBack(resource));
    }

    @Override
    public VCSResourceInfo getResourceInfo(
        de.fu_berlin.inf.dpp.filesystem.IResource resource) {
        return getResourceInfo(ResourceAdapterFactory.convertBack(resource));
    }

    @Override
    public VCSResourceInfo getCurrentResourceInfo(
        de.fu_berlin.inf.dpp.filesystem.IResource resource) {
        return getCurrentResourceInfo(ResourceAdapterFactory
            .convertBack(resource));
    }

    @Override
    public String getUrl(de.fu_berlin.inf.dpp.filesystem.IResource resource) {
        return getUrl(ResourceAdapterFactory.convertBack(resource));
    }
}
