package com.driver.services;

import com.driver.models.Blog;
import com.driver.models.Image;
import com.driver.models.User;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import com.driver.repositories.UserRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository1;

    @Autowired
    ImageService imageService1;

    @Autowired
    UserRepository userRepository1;

    public List<Blog> showBlogs(){
        //find all blogs
        return blogRepository1.findAll();
    }

    public void createAndReturnBlog(Integer userId, String title, String content) {
        //create a blog at the current time
        Date date = new Date();
        Blog blog = new Blog();
        User user = userRepository1.findById(userId).get();
        //updating the blog details
        blog.setContent(content);
        blog.setTitle(title);
        blog.setPubDate(date);
        blog.setUser(user);
        //Updating the userInformation and changing its blogs
        user.getBlogList().add(blog);
        userRepository1.save(user);

    }

    public Blog findBlogById(int blogId){
        //find a blog
        Blog blog = blogRepository1.findById(blogId).get();
        return blog;
    }

    public void addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog after creating it
        Blog blog = findBlogById(blogId);
        Image image = imageService1.createAndReturn(blog, description, dimensions);
    }

    public void deleteBlog(int blogId){
        //delete blog and corresponding images
        Blog blog = findBlogById(blogId);
        User user = blog.getUser();
        blogRepository1.deleteById(blogId);
        user.getBlogList().remove(blog);
        userRepository1.save(user);

    }
}
