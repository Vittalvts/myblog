package com.manu.Controller;

import com.manu.Entity.Post;
import com.manu.Payload.PostDto;
import com.manu.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
//http://localhost:8080/api/posts
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }

    // create blog post rest api
    @PostMapping
                               //@valid                   //BindingResult result
    public ResponseEntity<?>createpost(@Valid @RequestBody PostDto postDto, BindingResult result) {
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
//http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=id&SortDir=desc
    @GetMapping
    public List<Post> getAllPosts(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int
                                                 pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int
                                                 pageSize, @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                  @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String
                                                 sortDir)
{
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));

    }
    @PreAuthorize("hasRole('ADMIN')")

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable(name = "id") long id) {
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        // delete po
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}