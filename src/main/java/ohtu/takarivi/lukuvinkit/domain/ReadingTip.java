package ohtu.takarivi.lukuvinkit.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

/**
 * This class represents a reading tip, consisting of a title, description, URL and other associated data.
 * Each reading tip is also associated with an user as a CustomUser instance; this user represents the author of the
 * reading tip.
 */
@Entity
@Getter
@Setter
public class ReadingTip extends AbstractPersistable<Long> {

    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotEmpty
    private String url;
    @ManyToOne
    private CustomUser customUser;

    /**
     * Constructs a new empty ReadingTip instance.
     */
    public ReadingTip() {
        super();
    }

    /**
     * Constructs a new ReadingTip with the given parameters.
     *
     * @param title       The title of the work to be read.
     * @param description A description to the work, added by the user who adds the reading tip.
     * @param url         The URL of the reading tip; this is the video link for YouTube links, audio link for
     *                    podcasts and blog post link for blog posts. It is customizable for books.
     * @param customUser  The CustomUser instance representing the user who added this reading tip.
     */
    public ReadingTip(String title, String description, String url, CustomUser customUser) {
        this();
        this.title = title;
        this.description = description;
        this.url = url;
        this.customUser = customUser;
    }

}
