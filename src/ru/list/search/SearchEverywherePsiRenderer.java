//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ru.list.search;

import com.intellij.ide.util.PsiElementListCellRenderer;
import com.intellij.ide.util.PlatformModuleRendererFactory.PlatformModuleRenderer;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex.SERVICE;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.JBColor;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.util.ui.FilePathSplittingPolicy;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.io.File;
import java.util.LinkedList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import org.jetbrains.annotations.Nullable;

class SearchEverywherePsiRenderer extends PsiElementListCellRenderer<PsiElement> {
    private final JList myList;

    public SearchEverywherePsiRenderer(JList list) {
        this.myList = list;
        this.setFocusBorderEnabled(false);
        this.setLayout(new BorderLayout() {
            public void layoutContainer(Container target) {
                super.layoutContainer(target);
                Component right = this.getLayoutComponent("East");
                Component left = this.getLayoutComponent("West");
                if(right != null && left != null && left.getBounds().x + left.getBounds().width > right.getBounds().x) {
                    Rectangle bounds = right.getBounds();
                    int newX = left.getBounds().x + left.getBounds().width;
                    right.setBounds(newX, bounds.y, bounds.width - (newX - bounds.x), bounds.height);
                }

            }
        });
    }

    public String getElementText(PsiElement element) {
        String name = element instanceof PsiNamedElement?((PsiNamedElement)element).getName():null;
        return StringUtil.notNullize(name, "<unnamed>");
    }

    protected String getContainerText(PsiElement element, String name) {
        if(element instanceof PsiFileSystemItem) {
            PsiFileSystemItem parent = ((PsiFileSystemItem)element).getParent();
            PsiDirectory psiDirectory = parent instanceof PsiDirectory?(PsiDirectory)parent:null;
            VirtualFile virtualFile = psiDirectory == null?null:psiDirectory.getVirtualFile();
            if(virtualFile == null) {
                return null;
            } else {
                String relativePath = this.getRelativePath(virtualFile, element.getProject());
                if(relativePath == null) {
                    return "( " + File.separator + " )";
                } else {
                    int width = this.myList.getWidth();
                    if(width == 0) {
                        width += 800;
                    }

                    String path = FilePathSplittingPolicy.SPLIT_BY_SEPARATOR.getOptimalTextForComponent(name, new File(relativePath), this, width - this.myRightComponentWidth - 16 - 10);
                    return "(" + path + ")";
                }
            }
        } else {
            return this.getSymbolContainerText(name, element);
        }
    }

    private String getSymbolContainerText(String name, PsiElement element) {
        String text = SymbolPresentationUtil.getSymbolContainerText(element);
        if(this.myList.getWidth() == 0) {
            return text;
        } else if(text == null) {
            return null;
        } else {
            if(text.startsWith("(") && text.endsWith(")")) {
                text = text.substring(1, text.length() - 1);
            }

            boolean in = text.startsWith("in ");
            if(in) {
                text = text.substring(3);
            }

            FontMetrics fm = this.myList.getFontMetrics(this.myList.getFont());
            int maxWidth = this.myList.getWidth() - fm.stringWidth(name) - 16 - this.myRightComponentWidth - 20;
            String left = in?"(in ":"(";
            String right = ")";
            if(fm.stringWidth(left + text + right) < maxWidth) {
                return left + text + right;
            } else {
                LinkedList parts = new LinkedList(StringUtil.split(text, "."));

                int index;
                do {
                    if(parts.size() <= 1) {
                        return left + "..." + right;
                    }

                    index = parts.size() / 2 - 1;
                    parts.remove(index);
                } while(fm.stringWidth(StringUtil.join(parts, ".") + "...") >= maxWidth);

                parts.add(index, index == 0?"...":".");
                return left + StringUtil.join(parts, ".") + right;
            }
        }
    }

    @Nullable
    String getRelativePath(VirtualFile virtualFile, Project project) {
        String url = virtualFile.getPresentableUrl();
        if(project == null) {
            return url;
        } else {
            VirtualFile root = SERVICE.getInstance(project).getContentRootForFile(virtualFile);
            if(root != null) {
                return root.getName() + File.separatorChar + VfsUtilCore.getRelativePath(virtualFile, root, File.separatorChar);
            } else {
                VirtualFile baseDir = project.getBaseDir();
                if(baseDir != null) {
                    String projectHomeUrl = baseDir.getPresentableUrl();
                    if(url.startsWith(projectHomeUrl)) {
                        String cont = url.substring(projectHomeUrl.length());
                        if(cont.isEmpty()) {
                            return null;
                        }

                        url = "..." + cont;
                    }
                }

                return url;
            }
        }
    }

    protected boolean customizeNonPsiElementLeftRenderer(ColoredListCellRenderer renderer, JList list, Object value, int index, boolean selected, boolean hasFocus) {
        if(!(value instanceof NavigationItem)) {
            return false;
        } else {
            NavigationItem item = (NavigationItem)value;
            TextAttributes attributes = this.getNavigationItemAttributes(item);
            SimpleTextAttributes nameAttributes = attributes != null?SimpleTextAttributes.fromTextAttributes(attributes):null;
            Color color = list.getForeground();
            if(nameAttributes == null) {
                nameAttributes = new SimpleTextAttributes(0, color);
            }

            renderer.append(item + " ", nameAttributes);
            ItemPresentation itemPresentation = item.getPresentation();

            assert itemPresentation != null;

            renderer.setIcon(itemPresentation.getIcon(true));
            String locationString = itemPresentation.getLocationString();
            if(!StringUtil.isEmpty(locationString)) {
                renderer.append(locationString, new SimpleTextAttributes(0, JBColor.GRAY));
            }

            return true;
        }
    }

    protected DefaultListCellRenderer getRightCellRenderer(Object value) {
        DefaultListCellRenderer rightRenderer = super.getRightCellRenderer(value);
        return rightRenderer instanceof PlatformModuleRenderer?null:rightRenderer;
    }

    protected int getIconFlags() {
        return 2;
    }
}
