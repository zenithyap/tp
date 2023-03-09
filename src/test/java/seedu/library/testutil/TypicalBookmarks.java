package seedu.library.testutil;

import static seedu.library.logic.commands.CommandTestUtil.VALID_AUTHOR_AMY;
import static seedu.library.logic.commands.CommandTestUtil.VALID_AUTHOR_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_GENRE_AMY;
import static seedu.library.logic.commands.CommandTestUtil.VALID_GENRE_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static seedu.library.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.library.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.library.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.library.model.Library;
import seedu.library.model.bookmark.Bookmark;

/**
 * A utility class containing a list of {@code Bookmark} objects to be used in tests.
 */
public class TypicalBookmarks {

    public static final Bookmark ALICE = new BookmarkBuilder().withTitle("Alice Pauline")
            .withAuthor("123, Jurong West Ave 6, #08-111").withGenre("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Bookmark BENSON = new BookmarkBuilder().withTitle("Benson Meier")
            .withAuthor("311, Clementi Ave 2, #02-25")
            .withGenre("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Bookmark CARL = new BookmarkBuilder().withTitle("Carl Kurz").withPhone("95352563")
            .withGenre("heinz@example.com").withAuthor("wall street").build();
    public static final Bookmark DANIEL = new BookmarkBuilder().withTitle("Daniel Meier").withPhone("87652533")
            .withGenre("cornelia@example.com").withAuthor("10th street").withTags("friends").build();
    public static final Bookmark ELLE = new BookmarkBuilder().withTitle("Elle Meyer").withPhone("9482224")
            .withGenre("werner@example.com").withAuthor("michegan ave").build();
    public static final Bookmark FIONA = new BookmarkBuilder().withTitle("Fiona Kunz").withPhone("9482427")
            .withGenre("lydia@example.com").withAuthor("little tokyo").build();
    public static final Bookmark GEORGE = new BookmarkBuilder().withTitle("George Best").withPhone("9482442")
            .withGenre("anna@example.com").withAuthor("4th street").build();

    // Manually added
    public static final Bookmark HOON = new BookmarkBuilder().withTitle("Hoon Meier").withPhone("8482424")
            .withGenre("stefan@example.com").withAuthor("little india").build();
    public static final Bookmark IDA = new BookmarkBuilder().withTitle("Ida Mueller").withPhone("8482131")
            .withGenre("hans@example.com").withAuthor("chicago ave").build();

    // Manually added - Bookmark's details found in {@code CommandTestUtil}
    public static final Bookmark AMY = new BookmarkBuilder().withTitle(VALID_TITLE_AMY).withPhone(VALID_PHONE_AMY)
            .withGenre(VALID_GENRE_AMY).withAuthor(VALID_AUTHOR_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Bookmark BOB = new BookmarkBuilder().withTitle(VALID_TITLE_BOB).withPhone(VALID_PHONE_BOB)
            .withGenre(VALID_GENRE_BOB).withAuthor(VALID_AUTHOR_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBookmarks() {} // prevents instantiation

    /**
     * Returns an {@code Library} with all the typical Bookmarks.
     */
    public static Library getTypicalLibrary() {
        Library ab = new Library();
        for (Bookmark Bookmark : getTypicalBookmarks()) {
            ab.addBookmark(Bookmark);
        }
        return ab;
    }

    public static List<Bookmark> getTypicalBookmarks() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
