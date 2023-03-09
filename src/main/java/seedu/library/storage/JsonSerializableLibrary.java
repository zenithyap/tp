package seedu.library.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.library.commons.exceptions.IllegalValueException;
import seedu.library.model.Library;
import seedu.library.model.ReadOnlyLibrary;
import seedu.library.model.bookmark.Bookmark;

/**
 * An Immutable Library that is serializable to JSON format.
 */
@JsonRootName(value = "library")
class JsonSerializableLibrary {

    public static final String MESSAGE_DUPLICATE_BOOKMARK = "Bookmarks list contains duplicate bookmark(s).";

    private final List<JsonAdaptedBookmark> bookmarks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableLibrary} with the given bookmarks.
     */
    @JsonCreator
    public JsonSerializableLibrary(@JsonProperty("bookmarks") List<JsonAdaptedBookmark> bookmarks) {
        this.bookmarks.addAll(bookmarks);
    }

    /**
     * Converts a given {@code ReadOnlyLibrary} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableLibrary}.
     */
    public JsonSerializableLibrary(ReadOnlyLibrary source) {
        bookmarks.addAll(source.getBookmarkList().stream().map(JsonAdaptedBookmark::new).collect(Collectors.toList()));
    }

    /**
     * Converts this library into the model's {@code Library} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Library toModelType() throws IllegalValueException {
        Library library = new Library();
        for (JsonAdaptedBookmark jsonAdaptedBookmark : bookmarks) {
            Bookmark bookmark = jsonAdaptedBookmark.toModelType();
            if (library.hasBookmark(bookmark)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BOOKMARK);
            }
            library.addBookmark(bookmark);
        }
        return library;
    }

}
