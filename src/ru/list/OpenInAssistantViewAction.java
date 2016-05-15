package ru.list;

/**
 * Opens file in assistant view (second window in splitter)
 */
class OpenInAssistantViewAction extends AbstractOpenInViewAction {

    @Override
    int getViewIndex() {
        return 1;
    }
}