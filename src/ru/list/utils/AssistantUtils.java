package ru.list.utils;

import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.util.OpenSourceUtil;

import javax.swing.*;

/**
 * Created by andrey on 26/09/16.
 */
public class AssistantUtils {

    public static void openFileInEditorGroup(int windowIndex, VirtualFile file, FileEditorManagerEx fileEditorManager, EditorWindow editorWindow) {
        splitWindowIfNeeded(windowIndex, fileEditorManager, editorWindow);
        setCurrentWindow(windowIndex, fileEditorManager);
        fileEditorManager.openFile(file, true);
    }

    public static void setCurrentWindow(int viewIndex, FileEditorManagerEx fileEditorManager) {
        fileEditorManager.getWindows()[viewIndex].setAsCurrentWindow(true);
    }

    public static void splitWindowIfNeeded(int windowIndex, FileEditorManagerEx fileEditorManager, EditorWindow editorWindow) {
        // split if file needs to be in Assistant Window and FileEditor is not Splitter
        if (windowIndex == WindowIndex.ASSISTANT && !fileEditorManager.isInSplitter()) {
            fileEditorManager.createSplitter(SwingConstants.VERTICAL, editorWindow);
        }
    }

    public static void openPsiElement(int winowIndex, PsiElement psiElement, FileEditorManagerEx fileEditorManager, EditorWindow editorWindow) {
        splitWindowIfNeeded(winowIndex, fileEditorManager, editorWindow);
        setCurrentWindow(winowIndex, fileEditorManager);
        NavigationUtil.activateFileWithPsiElement(psiElement);
    }

    public static void openNavigatable(int windowIndex, Navigatable navigatable, FileEditorManagerEx fileEditorManager, EditorWindow editorWindow) {
        splitWindowIfNeeded(windowIndex, fileEditorManager, editorWindow);
        setCurrentWindow(windowIndex, fileEditorManager);
        OpenSourceUtil.navigate(true, navigatable);
    }
}
