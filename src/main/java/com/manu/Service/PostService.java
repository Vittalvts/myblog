package com.manu.Service;

import com.manu.Entity.Post;
import com.manu.Payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

  // List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);

    List<Post> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
