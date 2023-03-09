package seedu.library.model.bookmark;

import static java.util.Objects.requireNonNull;
import static seedu.library.commons.util.AppUtil.checkArgument;

/**
 * Represents a Bookmark's author in the library.
 * Guarantees: immutable; is valid as declared in {@link #isValidAuthor(String)}
 */
public class Author {

    public static final String MESSAGE_CONSTRAINTS = "Author can take any values, and it should not be blank";

    /*
     * The first character of the author must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Author}.
     *
     * @param author A valid author.
     */
    public Author(String author) {
        requireNonNull(author);
        checkArgument(isValidAuthor(author), MESSAGE_CONSTRAINTS);
        value = author;
    }

    /**
     * Returns true if a given string is a valid author.
     */
    public static boolean isValidAuthor(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Author // instanceof handles nulls
                && value.equals(((Author) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
