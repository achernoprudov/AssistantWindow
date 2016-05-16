package ru.list;

/**
 * Opens file in assistant view (second window in splitter)
 */
class OpenInMainWindowAction extends AbstractOpenInWindowAction {

    @Override
    int getViewIndex() {
        return 0;
    }
}