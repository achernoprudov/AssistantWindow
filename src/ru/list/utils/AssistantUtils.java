package ru.list.utils;

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.OpenSourceUtil;

import javax.swing.*;

/**
 * Created by andrey on 26/09/16.
 */
public class AssistantUtils {

    public static void openFileInEditorGroup(boolean inMainWindow, VirtualFile file, FileEditorManagerEx fileEditorManager, EditorWindow editorWindow) {
        int viewIndex = inMainWindow ? 0 : 1;
        if (fileEditorManager.isInSplitter()) {
            fileEditorManager.getWindows()[viewIndex].setAsCurrentWindow(true);
            fileEditorManager.openFile(file, true);
            return;
        }
        fileEditorManager.createSplitter(SwingConstants.VERTICAL, editorWindow);
        VirtualFile currentFile = fileEditorManager.getCurrentFile();
        if (currentFile == null) {
            return;
        }
        fileEditorManager.openFile(file, true);
        OpenSourceUtil.navigate();
    }
}
