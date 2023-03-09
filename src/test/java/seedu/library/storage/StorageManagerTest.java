package seedu.library.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.library.testutil.TypicalBookmarks.getTypicalLibrary;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.library.commons.core.GuiSettings;
import seedu.library.model.Library;
import seedu.library.model.ReadOnlyLibrary;
import seedu.library.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonLibraryStorage LibraryStorage = new JsonLibraryStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(LibraryStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void LibraryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonLibraryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonLibraryStorageTest} class.
         */
        Library original = getTypicalLibrary();
        storageManager.saveLibrary(original);
        ReadOnlyLibrary retrieved = storageManager.readLibrary().get();
        assertEquals(original, new Library(retrieved));
    }

    @Test
    public void getLibraryFilePath() {
        assertNotNull(storageManager.getLibraryFilePath());
    }

}
