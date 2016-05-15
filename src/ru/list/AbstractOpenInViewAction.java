package ru.list;

import com.intellij.ide.actions.BaseNavigateToSourceAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;

/**
 * Opens file in assisteng view
 */
abstract class AbstractOpenInViewAction extends BaseNavigateToSourceAction {


    public AbstractOpenInViewAction() {
        super(true);
    }

    protected AbstractOpenInViewAction(boolean b) {
        super(b);
    }

    abstract int getViewIndex();

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (e == null) {
            super.actionPerformed(e);
            return;
        }
        Project project = e.getData(CommonDataKeys.PROJECT);
        if (project == null) {
            return;
        }
        FileEditorManagerEx fileEditorManager = FileEditorManagerEx.getInstanceEx(project);

        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file == null) {
            return;
        }

        if (fileEditorManager.isInSplitter()) {
            fileEditorManager.getWindows()[getViewIndex()].setAsCurrentWindow(true);
            fileEditorManager.openFile(file, true);
        } else {
            EditorWindow editorWindow = e.getData(EditorWindow.DATA_KEY);
            fileEditorManager.createSplitter(SwingConstants.VERTICAL, editorWindow);

            VirtualFile currentFile = fileEditorManager.getCurrentFile();
            if (currentFile == null) {
                return;
            }

            fileEditorManager.openFile(file, true);
//            fileEditorManager.getCurrentWindow().closeFile(currentFile);
        }
    }
}