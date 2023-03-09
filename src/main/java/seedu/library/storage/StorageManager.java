package seedu.library.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.library.commons.core.LogsCenter;
import seedu.library.commons.exceptions.DataConversionException;
import seedu.library.model.ReadOnlyLibrary;
import seedu.library.model.ReadOnlyUserPrefs;
import seedu.library.model.UserPrefs;

/**
 * Manages storage of Library data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private LibraryStorage libraryStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code LibraryStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(LibraryStorage libraryStorage, UserPrefsStorage userPrefsStorage) {
        this.libraryStorage = libraryStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Library methods ==============================

    @Override
    public Path getLibraryFilePath() {
        return libraryStorage.getLibraryFilePath();
    }

    @Override
    public Optional<ReadOnlyLibrary> readLibrary() throws DataConversionException, IOException {
        return readLibrary(libraryStorage.getLibraryFilePath());
    }

    @Override
    public Optional<ReadOnlyLibrary> readLibrary(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return libraryStorage.readLibrary(filePath);
    }

    @Override
    public void saveLibrary(ReadOnlyLibrary library) throws IOException {
        saveLibrary(library, libraryStorage.getLibraryFilePath());
    }

    @Override
    public void saveLibrary(ReadOnlyLibrary library, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        libraryStorage.saveLibrary(library, filePath);
    }

}
