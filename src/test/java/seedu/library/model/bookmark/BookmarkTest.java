package seedu.library.model.bookmark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.library.logic.commands.CommandTestUtil.VALID_AUTHOR_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_GENRE_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.library.testutil.Assert.assertThrows;
import static seedu.library.testutil.TypicalBookmarks.ALICE;
import static seedu.library.testutil.TypicalBookmarks.BOB;

import org.junit.jupiter.api.Test;

import seedu.library.testutil.BookmarkBuilder;

public class BookmarkTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Bookmark Bookmark = new BookmarkBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> Bookmark.getTags().remove(0));
    }

    @Test
    public void isSameBookmark() {
        // same object -> returns true
        assertTrue(ALICE.isSameBookmark(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameBookmark(null));

        // same name, all other attributes different -> returns true
        Bookmark editedAlice = new BookmarkBuilder(ALICE).withPhone(VALID_PHONE_BOB).withGenre(VALID_GENRE_BOB)
                .withAuthor(VALID_AUTHOR_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameBookmark(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new BookmarkBuilder(ALICE).withTitle(VALID_TITLE_BOB).build();
        assertFalse(ALICE.isSameBookmark(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Bookmark editedBob = new BookmarkBuilder(BOB).withTitle(VALID_TITLE_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameBookmark(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_TITLE_BOB + " ";
        editedBob = new BookmarkBuilder(BOB).withTitle(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameBookmark(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Bookmark aliceCopy = new BookmarkBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different bookmark -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Bookmark editedAlice = new BookmarkBuilder(ALICE).withTitle(VALID_TITLE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new BookmarkBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new BookmarkBuilder(ALICE).withGenre(VALID_GENRE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new BookmarkBuilder(ALICE).withAuthor(VALID_AUTHOR_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new BookmarkBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
