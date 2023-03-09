package seedu.library.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.library.logic.commands.EditCommand.EditBookmarkDescriptor;
import seedu.library.model.bookmark.Author;
import seedu.library.model.bookmark.Genre;
import seedu.library.model.bookmark.Title;
import seedu.library.model.bookmark.Bookmark;
import seedu.library.model.bookmark.Phone;
import seedu.library.model.tag.Tag;

/**
 * A utility class to help with building EditBookmarkDescriptor objects.
 */
public class EditBookmarkDescriptorBuilder {

    private EditBookmarkDescriptor descriptor;

    public EditBookmarkDescriptorBuilder() {
        descriptor = new EditBookmarkDescriptor();
    }

    public EditBookmarkDescriptorBuilder(EditBookmarkDescriptor descriptor) {
        this.descriptor = new EditBookmarkDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBookmarkDescriptor} with fields containing {@code bookmark}'s details
     */
    public EditBookmarkDescriptorBuilder(Bookmark Bookmark) {
        descriptor = new EditBookmarkDescriptor();
        descriptor.setTitle(Bookmark.getTitle());
        descriptor.setPhone(Bookmark.getPhone());
        descriptor.setGenre(Bookmark.getGenre());
        descriptor.setAuthor(Bookmark.getAuthor());
        descriptor.setTags(Bookmark.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditBookmarkDescriptorBuilder withName(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditBookmarkDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Genre} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditBookmarkDescriptorBuilder withGenre(String genre) {
        descriptor.setGenre(new Genre(genre));
        return this;
    }

    /**
     * Sets the {@code Author} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditBookmarkDescriptorBuilder withAuthor(String author) {
        descriptor.setAuthor(new Author(author));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditBookmarkDescriptor}
     * that we are building.
     */
    public EditBookmarkDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditBookmarkDescriptor build() {
        return descriptor;
    }
}
