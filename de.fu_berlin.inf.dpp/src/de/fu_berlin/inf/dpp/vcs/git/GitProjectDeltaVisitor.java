package de.fu_berlin.inf.dpp.vcs.git;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IPath;

import de.fu_berlin.inf.dpp.editor.EditorManager;
import de.fu_berlin.inf.dpp.project.ProjectDeltaVisitor;
import de.fu_berlin.inf.dpp.project.SharedProject;
import de.fu_berlin.inf.dpp.session.ISarosSession;
import de.fu_berlin.inf.dpp.vcs.VCSAdapter;
import de.fu_berlin.inf.dpp.vcs.VCSResourceInfo;

public class GitProjectDeltaVisitor extends ProjectDeltaVisitor {
    protected final VCSAdapter vcs;

    public GitProjectDeltaVisitor(EditorManager editorManager,
        ISarosSession sarosSession, SharedProject sharedProject) {
        super(editorManager, sarosSession, sharedProject);
        this.vcs = sharedProject.getVCSAdapter();
    }

    protected void updateInfo(IResource resource) {
        if (!vcs.isManaged(resource)) {
            return;
        }
        VCSResourceInfo info = vcs.getResourceInfo(resource);
        sharedProject.updateVcsUrl(resource, info.getURL());
        sharedProject.updateRevision(resource, info.getRevision());
    }

    @Override
    public boolean visit(IResourceDelta delta) {
        IResource resource = delta.getResource();

        if (resource.isDerived())
            return false;

        assert resource.getProject().isOpen();

        assert vcs != null && vcs.equals(sharedProject.getVCSAdapter());

        if (isSync(delta)) {
            VCSResourceInfo info = vcs.getResourceInfo(resource);
            if (sharedProject.updateBranch(info.getBranch())
                || sharedProject.updateRevision(info.getRevision())) {
                // Switch
                if (info.getRevision() != null && !ignoreChildren(resource)) {
                    addActivity(vcs.getSwitchActivity(sarosSession, resource));
                    setIgnoreChildren(resource);
                }
            }
        }

        final boolean visitChildren = super.visit(delta);
        return visitChildren;
    }

    @Override
    public void add(IResource resource) {
        super.add(resource);
        updateInfo(resource);
    }

    @Override
    protected void move(IResource resource, IPath oldPath, IProject oldProject,
        boolean contentChange) throws IOException {
        super.move(resource, oldPath, oldProject, contentChange);
        updateInfo(resource);
    }

}
