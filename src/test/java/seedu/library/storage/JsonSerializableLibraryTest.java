package seedu.library.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.library.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.library.commons.exceptions.IllegalValueException;
import seedu.library.commons.util.JsonUtil;
import seedu.library.model.Library;
import seedu.library.testutil.TypicalBookmarks;

public class JsonSerializableLibraryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableLibraryTest");
    private static final Path TYPICAL_BOOKMARKS_FILE = TEST_DATA_FOLDER.resolve("typicalBookmarksLibrary.json");
    private static final Path INVALID_BOOKMARK_FILE = TEST_DATA_FOLDER.resolve("invalidBookmarkLibrary.json");
    private static final Path DUPLICATE_BOOKMARK_FILE = TEST_DATA_FOLDER.resolve("duplicateBookmarkLibrary.json");

    @Test
    public void toModelType_typicalBookmarksFile_success() throws Exception {
        JsonSerializableLibrary dataFromFile = JsonUtil.readJsonFile(TYPICAL_BOOKMARKS_FILE,
                JsonSerializableLibrary.class).get();
        Library LibraryFromFile = dataFromFile.toModelType();
        Library typicalBookmarksLibrary = TypicalBookmarks.getTypicalLibrary();
        assertEquals(LibraryFromFile, typicalBookmarksLibrary);
    }

    @Test
    public void toModelType_invalidBookmarkFile_throwsIllegalValueException() throws Exception {
        JsonSerializableLibrary dataFromFile = JsonUtil.readJsonFile(INVALID_BOOKMARK_FILE,
                JsonSerializableLibrary.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateBookmarks_throwsIllegalValueException() throws Exception {
        JsonSerializableLibrary dataFromFile = JsonUtil.readJsonFile(DUPLICATE_BOOKMARK_FILE,
                JsonSerializableLibrary.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableLibrary.MESSAGE_DUPLICATE_BOOKMARK,
                dataFromFile::toModelType);
    }

}
