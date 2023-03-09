package seedu.library.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.library.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX;
import static seedu.library.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.library.logic.commands.CommandTestUtil.AUTHOR_DESC_AMY;
import static seedu.library.logic.commands.CommandTestUtil.GENRE_DESC_AMY;
import static seedu.library.logic.commands.CommandTestUtil.TITLE_DESC_AMY;
import static seedu.library.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.library.testutil.Assert.assertThrows;
import static seedu.library.testutil.TypicalBookmarks.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.library.logic.commands.AddCommand;
import seedu.library.logic.commands.CommandResult;
import seedu.library.logic.commands.ListCommand;
import seedu.library.logic.commands.exceptions.CommandException;
import seedu.library.logic.parser.exceptions.ParseException;
import seedu.library.model.Model;
import seedu.library.model.ModelManager;
import seedu.library.model.ReadOnlyLibrary;
import seedu.library.model.UserPrefs;
import seedu.library.model.bookmark.Bookmark;
import seedu.library.storage.JsonLibraryStorage;
import seedu.library.storage.JsonUserPrefsStorage;
import seedu.library.storage.StorageManager;
import seedu.library.testutil.BookmarkBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonLibraryStorage libraryStorage =
                new JsonLibraryStorage(temporaryFolder.resolve("library.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(libraryStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonLibraryIoExceptionThrowingStub
        JsonLibraryStorage libraryStorage =
                new JsonLibraryIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionLibrary.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(libraryStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + TITLE_DESC_AMY + PHONE_DESC_AMY + GENRE_DESC_AMY
                + AUTHOR_DESC_AMY;
        Bookmark expectedBookmark = new BookmarkBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addBookmark(expectedBookmark);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredBookmarkList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredBookmarkList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonLibraryIoExceptionThrowingStub extends JsonLibraryStorage {
        private JsonLibraryIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveLibrary(ReadOnlyLibrary library, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
