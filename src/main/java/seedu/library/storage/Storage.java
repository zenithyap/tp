package seedu.library.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.library.commons.exceptions.DataConversionException;
import seedu.library.model.ReadOnlyLibrary;
import seedu.library.model.ReadOnlyUserPrefs;
import seedu.library.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends LibraryStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getLibraryFilePath();

    @Override
    Optional<ReadOnlyLibrary> readLibrary() throws DataConversionException, IOException;

    @Override
    void saveLibrary(ReadOnlyLibrary library) throws IOException;

}
