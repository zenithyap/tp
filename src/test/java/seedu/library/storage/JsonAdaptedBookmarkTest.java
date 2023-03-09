package seedu.library.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.library.storage.JsonAdaptedBookmark.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.library.testutil.Assert.assertThrows;
import static seedu.library.testutil.TypicalBookmarks.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.library.commons.exceptions.IllegalValueException;
import seedu.library.model.bookmark.Author;
import seedu.library.model.bookmark.Genre;
import seedu.library.model.bookmark.Title;
import seedu.library.model.bookmark.Phone;

public class JsonAdaptedBookmarkTest {
    private static final String INVALID_TITLE = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_AUTHOR = " ";
    private static final String INVALID_GENRE = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TITLE = BENSON.getTitle().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_GENRE = BENSON.getGenre().toString();
    private static final String VALID_AUTHOR = BENSON.getAuthor().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validBookmarkDetails_returnsBookmark() throws Exception {
        JsonAdaptedBookmark Bookmark = new JsonAdaptedBookmark(BENSON);
        assertEquals(BENSON, Bookmark.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedBookmark Bookmark =
                new JsonAdaptedBookmark(INVALID_TITLE, VALID_PHONE, VALID_GENRE, VALID_AUTHOR, VALID_TAGS);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, Bookmark::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedBookmark Bookmark = new JsonAdaptedBookmark(null, VALID_PHONE, VALID_GENRE, VALID_AUTHOR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, Bookmark::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedBookmark Bookmark =
                new JsonAdaptedBookmark(VALID_TITLE, INVALID_PHONE, VALID_GENRE, VALID_AUTHOR, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, Bookmark::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedBookmark Bookmark = new JsonAdaptedBookmark(VALID_TITLE, null, VALID_GENRE, VALID_AUTHOR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, Bookmark::toModelType);
    }

    @Test
    public void toModelType_invalidGenre_throwsIllegalValueException() {
        JsonAdaptedBookmark Bookmark =
                new JsonAdaptedBookmark(VALID_TITLE, VALID_PHONE, INVALID_GENRE, VALID_AUTHOR, VALID_TAGS);
        String expectedMessage = Genre.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, Bookmark::toModelType);
    }

    @Test
    public void toModelType_nullGenre_throwsIllegalValueException() {
        JsonAdaptedBookmark Bookmark = new JsonAdaptedBookmark(VALID_TITLE, VALID_PHONE, null, VALID_AUTHOR, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Genre.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, Bookmark::toModelType);
    }

    @Test
    public void toModelType_invalidAuthor_throwsIllegalValueException() {
        JsonAdaptedBookmark Bookmark =
                new JsonAdaptedBookmark(VALID_TITLE, VALID_PHONE, VALID_GENRE, INVALID_AUTHOR, VALID_TAGS);
        String expectedMessage = Author.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, Bookmark::toModelType);
    }

    @Test
    public void toModelType_nullAuthor_throwsIllegalValueException() {
        JsonAdaptedBookmark Bookmark = new JsonAdaptedBookmark(VALID_TITLE, VALID_PHONE, VALID_GENRE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, Bookmark::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedBookmark Bookmark =
                new JsonAdaptedBookmark(VALID_TITLE, VALID_PHONE, VALID_GENRE, VALID_AUTHOR, invalidTags);
        assertThrows(IllegalValueException.class, Bookmark::toModelType);
    }

}
