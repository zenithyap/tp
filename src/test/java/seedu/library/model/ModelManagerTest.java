package seedu.library.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.library.model.Model.PREDICATE_SHOW_ALL_BOOKMARKS;
import static seedu.library.testutil.Assert.assertThrows;
import static seedu.library.testutil.TypicalBookmarks.ALICE;
import static seedu.library.testutil.TypicalBookmarks.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.library.commons.core.GuiSettings;
import seedu.library.model.bookmark.NameContainsKeywordsPredicate;
import seedu.library.testutil.LibraryBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Library(), new Library(modelManager.getLibrary()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setLibraryFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setLibraryFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setLibraryFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setLibraryFilePath(null));
    }

    @Test
    public void setLibraryFilePath_validPath_setsLibraryFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setLibraryFilePath(path);
        assertEquals(path, modelManager.getLibraryFilePath());
    }

    @Test
    public void hasBookmark_nullBookmark_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasBookmark(null));
    }

    @Test
    public void hasBookmark_BookmarkNotInLibrary_returnsFalse() {
        assertFalse(modelManager.hasBookmark(ALICE));
    }

    @Test
    public void hasBookmark_BookmarkInLibrary_returnsTrue() {
        modelManager.addBookmark(ALICE);
        assertTrue(modelManager.hasBookmark(ALICE));
    }

    @Test
    public void getFilteredBookmarkList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredBookmarkList().remove(0));
    }

    @Test
    public void equals() {
        Library Library = new LibraryBuilder().withBookmark(ALICE).withBookmark(BENSON).build();
        Library differentLibrary = new Library();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(Library, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(Library, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different Library -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentLibrary, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getTitle().value.split("\\s+");
        modelManager.updateFilteredBookmarkList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(Library, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setLibraryFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(Library, differentUserPrefs)));
    }
}
