package de.fu_berlin.inf.dpp.intellij.editor.annotations;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import de.fu_berlin.inf.dpp.activities.SPath;
import de.fu_berlin.inf.dpp.filesystem.IFile;
import de.fu_berlin.inf.dpp.intellij.editor.AbstractStoppableDocumentListener;
import de.fu_berlin.inf.dpp.intellij.editor.EditorManager;
import de.fu_berlin.inf.dpp.intellij.editor.ProjectAPI;
import org.jetbrains.annotations.NotNull;

/**
 * Tracks modifications of Documents and adjusts the local annotations accordingly if the document
 * is not currently open in an editor.
 *
 * @see com.intellij.openapi.editor.event.DocumentListener
 */
public class AnnotationDocumentListener extends AbstractStoppableDocumentListener {
  private final ProjectAPI projectAPI;
  private final AnnotationManager annotationManager;

  public AnnotationDocumentListener(
      @NotNull EditorManager editorManager,
      @NotNull ProjectAPI projectAPI,
      @NotNull AnnotationManager annotationManager) {

    super(editorManager);

    this.projectAPI = projectAPI;
    this.annotationManager = annotationManager;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Adjusts the annotations for the resource represented by the changed document if it is not
   * currently open in an editor. If it is currently open in an editor, this will be done
   * automatically by Intellij.
   *
   * @param event {@inheritDoc}
   */
  @Override
  public void beforeDocumentChange(DocumentEvent event) {
    Document document = event.getDocument();

    SPath path = getSPath(document);

    if (path == null) {
      return;
    }

    int offset = event.getOffset();
    String newText = event.getNewFragment().toString();
    String replacedText = event.getOldFragment().toString();

    if (!projectAPI.isOpen(document)) {
      IFile file = path.getFile();

      int replacedTextLength = replacedText.length();
      if (replacedTextLength > 0) {
        annotationManager.moveAnnotationsAfterDeletion(file, offset, offset + replacedTextLength);
      }

      int newTextLength = newText.length();
      if (newTextLength > 0) {
        annotationManager.moveAnnotationsAfterAddition(file, offset, offset + newTextLength);
      }
    }
  }
}
