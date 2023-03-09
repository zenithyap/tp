package seedu.library.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.library.model.bookmark.Bookmark;
import seedu.library.model.bookmark.UniqueBookmarkList;

/**
 * Wraps all data at the library level
 * Duplicates are not allowed (by .isSameBookmark comparison)
 */
public class Library implements ReadOnlyLibrary {

    private final UniqueBookmarkList bookmarks;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        bookmarks = new UniqueBookmarkList();
    }

    public Library() {}

    /**
     * Creates a Library using the Bookmarks in the {@code toBeCopied}
     */
    public Library(ReadOnlyLibrary toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the bookmark list with {@code bookmarks}.
     * {@code bookmarks} must not contain duplicate bookmarks.
     */
    public void setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks.setBookmarks(bookmarks);
    }

    /**
     * Resets the existing data of this {@code Library} with {@code newData}.
     */
    public void resetData(ReadOnlyLibrary newData) {
        requireNonNull(newData);

        setBookmarks(newData.getBookmarkList());
    }

    //// bookmark-level operations

    /**
     * Returns true if a bookmark with the same identity as {@code bookmark} exists in the library.
     */
    public boolean hasBookmark(Bookmark bookmark) {
        requireNonNull(bookmark);
        return bookmarks.contains(bookmark);
    }

    /**
     * Adds a bookmark to the library.
     * The bookmark must not already exist in the library.
     */
    public void addBookmark(Bookmark b) {
        bookmarks.add(b);
    }

    /**
     * Replaces the given bookmark {@code target} in the list with {@code editedBookmark}.
     * {@code target} must exist in the library.
     * The bookmark identity of {@code editedBookmark} must not be the same as another existing bookmark in the library.
     */
    public void setBookmark(Bookmark target, Bookmark editedBookmark) {
        requireNonNull(editedBookmark);

        bookmarks.setBookmark(target, editedBookmark);
    }

    /**
     * Removes {@code key} from this {@code Library}.
     * {@code key} must exist in the library.
     */
    public void removeBookmark(Bookmark key) {
        bookmarks.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return bookmarks.asUnmodifiableObservableList().size() + " bookmarks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Bookmark> getBookmarkList() {
        return bookmarks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Library // instanceof handles nulls
                && bookmarks.equals(((Library) other).bookmarks));
    }

    @Override
    public int hashCode() {
        return bookmarks.hashCode();
    }
}
