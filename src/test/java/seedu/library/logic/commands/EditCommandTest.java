package seedu.library.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.library.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.library.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.library.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.library.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.library.logic.commands.CommandTestUtil.showBookmarkAtIndex;
import static seedu.library.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;
import static seedu.library.testutil.TypicalIndexes.INDEX_SECOND_BOOKMARK;
import static seedu.library.testutil.TypicalBookmarks.getTypicalLibrary;

import org.junit.jupiter.api.Test;

import seedu.library.commons.core.Messages;
import seedu.library.commons.core.index.Index;
import seedu.library.logic.commands.EditCommand.EditBookmarkDescriptor;
import seedu.library.model.Library;
import seedu.library.model.Model;
import seedu.library.model.ModelManager;
import seedu.library.model.UserPrefs;
import seedu.library.model.bookmark.Bookmark;
import seedu.library.testutil.EditBookmarkDescriptorBuilder;
import seedu.library.testutil.BookmarkBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalLibrary(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Bookmark editedBookmark = new BookmarkBuilder().build();
        EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder(editedBookmark).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOKMARK, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new Library(model.getLibrary()), new UserPrefs());
        expectedModel.setBookmark(model.getFilteredBookmarkList().get(0), editedBookmark);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastBookmark = Index.fromOneBased(model.getFilteredBookmarkList().size());
        Bookmark lastBookmark = model.getFilteredBookmarkList().get(indexLastBookmark.getZeroBased());

        BookmarkBuilder bookmarkInList = new BookmarkBuilder(lastBookmark);
        Bookmark editedBookmark = bookmarkInList.withTitle(VALID_TITLE_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder().withName(VALID_TITLE_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastBookmark, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new Library(model.getLibrary()), new UserPrefs());
        expectedModel.setBookmark(lastBookmark, editedBookmark);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOKMARK, new EditCommand.EditBookmarkDescriptor());
        Bookmark editedBookmark = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new Library(model.getLibrary()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);

        Bookmark bookmarkInFilteredList = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        Bookmark editedBookmark = new BookmarkBuilder(bookmarkInFilteredList).withTitle(VALID_TITLE_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOKMARK,
                new EditBookmarkDescriptorBuilder().withName(VALID_TITLE_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new Library(model.getLibrary()), new UserPrefs());
        expectedModel.setBookmark(model.getFilteredBookmarkList().get(0), editedBookmark);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBookmarkUnfilteredList_failure() {
        Bookmark firstBookmark = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder(firstBookmark).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_BOOKMARK, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BOOKMARK);
    }

    @Test
    public void execute_duplicateBookmarkFilteredList_failure() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);

        // edit bookmark in filtered list into a duplicate in address book
        Bookmark bookmarkInList = model.getLibrary().getBookmarkList().get(INDEX_SECOND_BOOKMARK.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOKMARK,
                new EditBookmarkDescriptorBuilder(bookmarkInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BOOKMARK);
    }

    @Test
    public void execute_invalidBookmarkIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkList().size() + 1);
        EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder().withName(VALID_TITLE_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of library
     */
    @Test
    public void execute_invalidBookmarkIndexFilteredList_failure() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);
        Index outOfBoundIndex = INDEX_SECOND_BOOKMARK;
        // ensures that outOfBoundIndex is still in bounds of library list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLibrary().getBookmarkList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditBookmarkDescriptorBuilder().withName(VALID_TITLE_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_BOOKMARK, DESC_AMY);

        // same values -> returns true
        EditCommand.EditBookmarkDescriptor copyDescriptor = new EditBookmarkDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_BOOKMARK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_BOOKMARK, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_BOOKMARK, DESC_BOB)));
    }

}
