package dto;

import entities.Joke;
import java.util.Objects;

/**
 *
 * @author runin
 */
public class JokeDTO {
    
    private Long id;
    private String joke;
    private int rating;

    public JokeDTO(Joke joke) {
        this.id = joke.getId();
        this.joke = joke.getJoke();
        this.rating = joke.getRating();
    }

    public JokeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JokeDTO other = (JokeDTO) obj;
        if (this.rating != other.rating) {
            return false;
        }
        if (!Objects.equals(this.joke, other.joke)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
