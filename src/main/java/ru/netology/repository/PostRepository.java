package ru.netology.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import ru.netology.model.Post;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Stub
@Repository
public class PostRepository {

    private Map<Long, Post> postMap;
    private long id;

    public PostRepository() {
        this.postMap = new ConcurrentHashMap<>();
        this.id = Long.parseLong("0");
    }

    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }

    public List<Post> all() {
        return List.copyOf(postMap.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(postMap.get(id));
    }

    public synchronized Post save(Post post) {
        Long postId = post.getId();
        if (!postId.equals(Long.parseLong("0"))) {
            if (postMap.containsValue(post.getId())) {
                postMap.replace(postId, post);
                return post;
            }
        }
        id++;
        postMap.put(id, post);
        post.setId(id);
        return post;
    }

    public synchronized void removeById(long id) {
        if (postMap.containsKey(id)) {
            postMap.remove(id);
        }
    }

}
