/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import context.DBContext;
import model.Blog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author namp0
 */


public class BlogDAO extends DBContext {

    public List<Blog> getAllActiveBlogs() {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT * FROM Blog WHERE status = 1 ORDER BY createdAt DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blogId"));
                blog.setStaffId(rs.getString("staffId"));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setImage(rs.getString("image"));
                blog.setCreatedAt(rs.getTimestamp("createdAt"));
                blog.setStatus(rs.getInt("status"));

                blogs.add(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return blogs;
    }

    public Blog getBlogById(int blogId) {
        String sql = "SELECT * FROM Blog WHERE blogId = ? AND status = 1";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, blogId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blogId"));
                blog.setStaffId(rs.getString("staffId"));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setImage(rs.getString("image"));
                blog.setCreatedAt(rs.getTimestamp("createdAt"));
                blog.setStatus(rs.getInt("status"));

                return blog;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
