package ru.list.navigator;

/**
 * Opens file in main view (first group in splitter)
 */
class OpenInMainGroupAction extends AbstractOpenInGroupAction {

    public OpenInMainGroupAction() {
    }

    @Override
    int getViewIndex() {
        return 0;
    }
}