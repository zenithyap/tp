package seedu.library.model.bookmark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.library.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AuthorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Author(null));
    }

    @Test
    public void constructor_invalidAuthor_throwsIllegalArgumentException() {
        String invalidAuthor = "";
        assertThrows(IllegalArgumentException.class, () -> new Author(invalidAuthor));
    }

    @Test
    public void isValidAuthor() {
        // null author
        assertThrows(NullPointerException.class, () -> Author.isValidAuthor(null));

        // invalid author
        assertFalse(Author.isValidAuthor("")); // empty string
        assertFalse(Author.isValidAuthor(" ")); // spaces only

        // valid author
        assertTrue(Author.isValidAuthor("Blk 456, Den Road, #01-355"));
        assertTrue(Author.isValidAuthor("-")); // one character
        assertTrue(Author.isValidAuthor("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
