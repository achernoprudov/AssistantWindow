package ru.list;

/**
 * Opens file in assistant view (second window in splitter)
 */
class OpenInAssistantWindowAction extends AbstractOpenInWindowAction {

    @Override
    int getViewIndex() {
        return 1;
    }
}