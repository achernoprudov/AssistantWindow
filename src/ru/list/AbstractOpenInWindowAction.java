package ru.list;

import com.intellij.ide.actions.BaseNavigateToSourceAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.OpenSourceUtil;

import javax.swing.*;

/**
 * Abstract action class. Allows to open files in different windows. Like Assistant view in Xcode.
 */
abstract class AbstractOpenInWindowAction extends BaseNavigateToSourceAction {

    public AbstractOpenInWindowAction() {
        super(true);
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
            return;
        }
        EditorWindow editorWindow = e.getData(EditorWindow.DATA_KEY);
        fileEditorManager.createSplitter(SwingConstants.VERTICAL, editorWindow);
        VirtualFile currentFile = fileEditorManager.getCurrentFile();
        if (currentFile == null) {
            return;
        }
        fileEditorManager.openFile(file, true);
        OpenSourceUtil.navigate();
    }
}