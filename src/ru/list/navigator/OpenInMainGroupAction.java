package ru.list.navigator;

import com.intellij.ide.actions.BaseNavigateToSourceAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import ru.list.utils.AssistantUtils;

/**
 * Opens file in main view (first group in splitter)
 */
class OpenInMainGroupAction extends BaseNavigateToSourceAction {

    public OpenInMainGroupAction() {
        super(true);
    }

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
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file == null) {
            return;
        }
        EditorWindow editorWindow = e.getData(EditorWindow.DATA_KEY);
        FileEditorManagerEx fileEditorManager = FileEditorManagerEx.getInstanceEx(project);
        AssistantUtils.openFileInEditorGroup(true, file, fileEditorManager, editorWindow);
    }
}