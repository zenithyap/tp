package seedu.library.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.library.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.library.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.library.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.library.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.library.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.library.model.Model.PREDICATE_SHOW_ALL_BOOKMARKS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.library.commons.core.Messages;
import seedu.library.commons.core.index.Index;
import seedu.library.commons.util.CollectionUtil;
import seedu.library.logic.commands.exceptions.CommandException;
import seedu.library.model.Model;
import seedu.library.model.bookmark.Author;
import seedu.library.model.bookmark.Genre;
import seedu.library.model.bookmark.Title;
import seedu.library.model.bookmark.Bookmark;
import seedu.library.model.bookmark.Phone;
import seedu.library.model.tag.Tag;

/**
 * Edits the details of an existing bookmark in the library.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the bookmark identified "
            + "by the index number used in the displayed bookmark list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_GENRE + "GENRE] "
            + "[" + PREFIX_AUTHOR + "AUTHOR] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_GENRE + "johndoe@example.com";

    public static final String MESSAGE_EDIT_BOOKMARK_SUCCESS = "Edited Bookmark: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_BOOKMARK = "This bookmark already exists in the library.";

    private final Index index;
    private final EditBookmarkDescriptor editBookmarkDescriptor;

    /**
     * @param index of the bookmark in the filtered bookmark list to edit
     * @param editBookmarkDescriptor details to edit the bookmark with
     */
    public EditCommand(Index index, EditBookmarkDescriptor editBookmarkDescriptor) {
        requireNonNull(index);
        requireNonNull(editBookmarkDescriptor);

        this.index = index;
        this.editBookmarkDescriptor = new EditBookmarkDescriptor(editBookmarkDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        Bookmark bookmarkToEdit = lastShownList.get(index.getZeroBased());
        Bookmark editedBookmark = createEditedBookmark(bookmarkToEdit, editBookmarkDescriptor);

        if (!bookmarkToEdit.isSameBookmark(editedBookmark) && model.hasBookmark(editedBookmark)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKMARK);
        }

        model.setBookmark(bookmarkToEdit, editedBookmark);
        model.updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);
        return new CommandResult(String.format(MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark));
    }

    /**
     * Creates and returns a {@code Bookmark} with the details of {@code bookmarkToEdit}
     * edited with {@code editBookmarkDescriptor}.
     */
    private static Bookmark createEditedBookmark(Bookmark bookmarkToEdit, EditBookmarkDescriptor editBookmarkDescriptor) {
        assert bookmarkToEdit != null;

        Title updatedTitle = editBookmarkDescriptor.getTitle().orElse(bookmarkToEdit.getTitle());
        Phone updatedPhone = editBookmarkDescriptor.getPhone().orElse(bookmarkToEdit.getPhone());
        Genre updatedGenre = editBookmarkDescriptor.getGenre().orElse(bookmarkToEdit.getGenre());
        Author updatedAuthor = editBookmarkDescriptor.getAuthor().orElse(bookmarkToEdit.getAuthor());
        Set<Tag> updatedTags = editBookmarkDescriptor.getTags().orElse(bookmarkToEdit.getTags());

        return new Bookmark(updatedTitle, updatedPhone, updatedGenre, updatedAuthor, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editBookmarkDescriptor.equals(e.editBookmarkDescriptor);
    }

    /**
     * Stores the details to edit the bookmark with. Each non-empty field value will replace the
     * corresponding field value of the bookmark.
     */
    public static class EditBookmarkDescriptor {
        private Title title;
        private Phone phone;
        private Genre genre;
        private Author author;
        private Set<Tag> tags;

        public EditBookmarkDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBookmarkDescriptor(EditBookmarkDescriptor toCopy) {
            setTitle(toCopy.title);
            setPhone(toCopy.phone);
            setGenre(toCopy.genre);
            setAuthor(toCopy.author);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, phone, genre, author, tags);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setGenre(Genre genre) {
            this.genre = genre;
        }

        public Optional<Genre> getGenre() {
            return Optional.ofNullable(genre);
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public Optional<Author> getAuthor() {
            return Optional.ofNullable(author);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBookmarkDescriptor)) {
                return false;
            }

            // state check
            EditBookmarkDescriptor e = (EditBookmarkDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getPhone().equals(e.getPhone())
                    && getGenre().equals(e.getGenre())
                    && getAuthor().equals(e.getAuthor())
                    && getTags().equals(e.getTags());
        }
    }
}
