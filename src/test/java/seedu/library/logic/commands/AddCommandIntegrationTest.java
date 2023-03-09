package seedu.library.logic.commands;

import static seedu.library.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.library.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.library.testutil.TypicalBookmarks.getTypicalLibrary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.library.model.Model;
import seedu.library.model.ModelManager;
import seedu.library.model.UserPrefs;
import seedu.library.model.bookmark.Bookmark;
import seedu.library.testutil.BookmarkBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalLibrary(), new UserPrefs());
    }

    @Test
    public void execute_newBookmark_success() {
        Bookmark validBookmark = new BookmarkBuilder().build();

        Model expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
        expectedModel.addBookmark(validBookmark);

        assertCommandSuccess(new AddCommand(validBookmark), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validBookmark), expectedModel);
    }

    @Test
    public void execute_duplicateBookmark_throwsCommandException() {
        Bookmark bookmarkInList = model.getLibrary().getBookmarkList().get(0);
        assertCommandFailure(new AddCommand(bookmarkInList), model, AddCommand.MESSAGE_DUPLICATE_BOOKMARK);
    }

}
