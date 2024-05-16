package com.manu.Service.Impl;

import com.manu.Entity.Post;
import com.manu.Payload.PostDto;
import com.manu.Repository.PostRepository;
import com.manu.Service.PostService;
import com.manu.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
        this.modelMapper= modelMapper;
    }


    @Override
    public PostDto createPost(PostDto postDto) {
        Post post =mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }

   // @Override
   // public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
       // List<Post> posts = postRepository.findAll();
       // Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
              //  Sort.by(sortBy).ascending()
               // : Sort.by(sortBy).descending();
        // create Pageable instance
        //Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      //  Page<Post> posts = postRepository.findAll(pageable);
        // get content for page object
        //List<Post> listOfPosts = posts.getContent();
       // List<PostDto> content= listOfPosts.stream().map(post ->
               // mapToDTO(post)).collect(Collectors.toList());
       // Post post = new Post();
       // post.setContent(content.toString());
       // post.setPageNo(posts.getNumber());
      //  post.setPageSize(posts.getSize());
        //postResponse.setTotalElements(posts.getTotalElements());
       // postResponse.setTotalPages(posts.getTotalPages());
        //postResponse.setLast(posts.isLast());
       // return postResponse;

      //  return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    //}

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new
                ResourceNotFoundException("Post not found by id"+id));
        return mapToDTO(post);

    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new
                ResourceNotFoundException("Post by not found"+id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        postRepository.findById(id).orElseThrow(

                ()->new ResourceNotFoundException("POST NOT FOUND WITH ID:" + id)
        );
        postRepository.deleteById(id);

    }

    @Override
    public List<Post> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        List<Post> posts = postRepository.findAll();
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Post> listOfPosts = postRepository.findAll(pageable);
        List<Post> postDtos =listOfPosts.getContent();
        posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        return postDtos;

    }



    private PostDto mapToDTO(Post post) {
       PostDto Dto =modelMapper.map(post,PostDto.class);
       //PostDto postDto = new PostDto();
       //postDto.setId(post.getId());
       //postDto.setTitle(post.getTitle());
       // postDto.setDescription(post.getDescription());
       //postDto.setContent(post.getContent());
        return Dto;
    }
    private Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        //Post post = new Post();
        // post.setTitle(postDto.getTitle());
        //  post.setDescription(postDto.getDescription());
        // post.setContent(postDto.getContent());
        return post;
}
}