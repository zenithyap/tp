package seedu.library.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.library.commons.exceptions.DataConversionException;
import seedu.library.model.Library;
import seedu.library.model.ReadOnlyLibrary;

/**
 * Represents a storage for {@link Library}.
 */
public interface LibraryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getLibraryFilePath();

    /**
     * Returns Library data as a {@link ReadOnlyLibrary}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyLibrary> readLibrary() throws DataConversionException, IOException;

    /**
     * @see #getLibraryFilePath()
     */
    Optional<ReadOnlyLibrary> readLibrary(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyLibrary} to the storage.
     * @param library cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLibrary(ReadOnlyLibrary library) throws IOException;

    /**
     * @see #saveLibrary(ReadOnlyLibrary)
     */
    void saveLibrary(ReadOnlyLibrary Library, Path filePath) throws IOException;

}
