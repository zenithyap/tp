package seedu.library.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.library.commons.core.LogsCenter;
import seedu.library.commons.exceptions.DataConversionException;
import seedu.library.commons.exceptions.IllegalValueException;
import seedu.library.commons.util.FileUtil;
import seedu.library.commons.util.JsonUtil;
import seedu.library.model.ReadOnlyLibrary;

/**
 * A class to access Library data stored as a json file on the hard disk.
 */
public class JsonLibraryStorage implements LibraryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonLibraryStorage.class);

    private Path filePath;

    public JsonLibraryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getLibraryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLibrary> readLibrary() throws DataConversionException {
        return readLibrary(filePath);
    }

    /**
     * Similar to {@link #readLibrary()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyLibrary> readLibrary(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableLibrary> jsonLibrary = JsonUtil.readJsonFile(
                filePath, JsonSerializableLibrary.class);
        if (!jsonLibrary.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonLibrary.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveLibrary(ReadOnlyLibrary library) throws IOException {
        saveLibrary(library, filePath);
    }

    /**
     * Similar to {@link #saveLibrary(ReadOnlyLibrary)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveLibrary(ReadOnlyLibrary library, Path filePath) throws IOException {
        requireNonNull(library);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableLibrary(library), filePath);
    }

}
