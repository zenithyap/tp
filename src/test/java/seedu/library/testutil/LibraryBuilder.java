package seedu.library.testutil;

import seedu.library.model.Library;
import seedu.library.model.bookmark.Bookmark;

/**
 * A utility class to help with building Library objects.
 * Example usage: <br>
 *     {@code Library ab = new LibraryBuilder().withBookmark("John", "Doe").build();}
 */
public class LibraryBuilder {

    private Library Library;

    public LibraryBuilder() {
        Library = new Library();
    }

    public LibraryBuilder(Library Library) {
        this.Library = Library;
    }

    /**
     * Adds a new {@code Bookmark} to the {@code Library} that we are building.
     */
    public LibraryBuilder withBookmark(Bookmark Bookmark) {
        Library.addBookmark(Bookmark);
        return this;
    }

    public Library build() {
        return Library;
    }
}
