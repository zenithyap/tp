package seedu.library.model.bookmark;

import static seedu.library.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.library.model.tag.Tag;

/**
 * Represents a Bookmark in the library.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Bookmark {

    // Identity fields
    private final Title title;
    private final Phone phone;
    private final Genre genre;

    // Data fields
    private final Author author;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Bookmark(Title title, Phone phone, Genre genre, Author author, Set<Tag> tags) {
        requireAllNonNull(title, phone, genre, author, tags);
        this.title = title;
        this.phone = phone;
        this.genre = genre;
        this.author = author;
        this.tags.addAll(tags);
    }

    public Title getTitle() {
        return title;
    }

    public Phone getPhone() {
        return phone;
    }

    public Genre getGenre() {
        return genre;
    }

    public Author getAuthor() {
        return author;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both bookmarks have the same title.
     * This defines a weaker notion of equality between two bookmarks.
     */
    public boolean isSameBookmark(Bookmark otherBookmark) {
        if (otherBookmark == this) {
            return true;
        }

        return otherBookmark != null
                && otherBookmark.getTitle().equals(getTitle());
    }

    /**
     * Returns true if both bookmarks have the same identity and data fields.
     * This defines a stronger notion of equality between two bookmarks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Bookmark)) {
            return false;
        }

        Bookmark otherBookmark = (Bookmark) other;
        return otherBookmark.getTitle().equals(getTitle())
                && otherBookmark.getPhone().equals(getPhone())
                && otherBookmark.getGenre().equals(getGenre())
                && otherBookmark.getAuthor().equals(getAuthor())
                && otherBookmark.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, phone, genre, author, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Genre: ")
                .append(getGenre())
                .append("; Author: ")
                .append(getAuthor());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
