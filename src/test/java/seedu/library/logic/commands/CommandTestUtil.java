package seedu.library.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.library.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.library.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.library.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.library.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.library.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.library.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.library.commons.core.index.Index;
import seedu.library.logic.commands.exceptions.CommandException;
import seedu.library.model.Library;
import seedu.library.model.Model;
import seedu.library.model.bookmark.NameContainsKeywordsPredicate;
import seedu.library.model.bookmark.Bookmark;
import seedu.library.testutil.EditBookmarkDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TITLE_AMY = "Amy Bee";
    public static final String VALID_TITLE_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_GENRE_AMY = "amy@example.com";
    public static final String VALID_GENRE_BOB = "bob@example.com";
    public static final String VALID_AUTHOR_AMY = "Block 312, Amy Street 1";
    public static final String VALID_AUTHOR_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String TITLE_DESC_AMY = " " + PREFIX_TITLE + VALID_TITLE_AMY;
    public static final String TITLE_DESC_BOB = " " + PREFIX_TITLE + VALID_TITLE_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String GENRE_DESC_AMY = " " + PREFIX_GENRE + VALID_GENRE_AMY;
    public static final String GENRE_DESC_BOB = " " + PREFIX_GENRE + VALID_GENRE_BOB;
    public static final String AUTHOR_DESC_AMY = " " + PREFIX_AUTHOR + VALID_AUTHOR_AMY;
    public static final String AUTHOR_DESC_BOB = " " + PREFIX_AUTHOR + VALID_AUTHOR_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_GENRE_DESC = " " + PREFIX_GENRE + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_AUTHOR_DESC = " " + PREFIX_AUTHOR; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditBookmarkDescriptor DESC_AMY;
    public static final EditCommand.EditBookmarkDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditBookmarkDescriptorBuilder().withName(VALID_TITLE_AMY)
                .withPhone(VALID_PHONE_AMY).withGenre(VALID_GENRE_AMY).withAuthor(VALID_AUTHOR_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditBookmarkDescriptorBuilder().withName(VALID_TITLE_BOB)
                .withPhone(VALID_PHONE_BOB).withGenre(VALID_GENRE_BOB).withAuthor(VALID_AUTHOR_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the library, filtered bookmark list and selected bookmark in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Library expectedLibrary = new Library(actualModel.getLibrary());
        List<Bookmark> expectedFilteredList = new ArrayList<>(actualModel.getFilteredBookmarkList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedLibrary, actualModel.getLibrary());
        assertEquals(expectedFilteredList, actualModel.getFilteredBookmarkList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the bookmark at the given {@code targetIndex} in the
     * {@code model}'s library.
     */
    public static void showBookmarkAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBookmarkList().size());

        Bookmark bookmark = model.getFilteredBookmarkList().get(targetIndex.getZeroBased());
        final String[] splitName = bookmark.getTitle().value.split("\\s+");
        model.updateFilteredBookmarkList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredBookmarkList().size());
    }

}
