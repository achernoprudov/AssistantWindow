package ru.list.navigator;

/**
 * Opens file in assistant view (second window in splitter)
 */
class OpenInSecondGroupAction extends AbstractOpenInGroupAction {

    public OpenInSecondGroupAction() {
    }

    @Override
    int getViewIndex() {
        return 1;
    }
}