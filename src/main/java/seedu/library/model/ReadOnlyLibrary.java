package seedu.library.model;

import javafx.collections.ObservableList;
import seedu.library.model.bookmark.Bookmark;

/**
 * Unmodifiable view of a library
 */
public interface ReadOnlyLibrary {

    /**
     * Returns an unmodifiable view of the bookmarks list.
     * This list will not contain any duplicate bookmarks.
     */
    ObservableList<Bookmark> getBookmarkList();

}
