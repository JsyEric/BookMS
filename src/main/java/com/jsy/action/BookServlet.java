package com.jsy.action;

import com.jsy.SpringConfig.springConfiguration;
import com.jsy.bean.Book;
import com.jsy.biz.BookBiz;
import com.jsy.util.DateHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/book.let")
public class BookServlet extends HttpServlet {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(springConfiguration.class);
    BookBiz bookBiz = app.getBean(BookBiz.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String type = req.getParameter("type");
        switch (type){
            case "add":
                //构建文件工厂
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(1024*9);
                File file = new File("c:\\temp");
                if(!file.exists()){
                    file.mkdir();
                }
                factory.setRepository(file);
                //文件上传+表单数据
                ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
                List<FileItem> fileItems = null;
                try {
                    fileItems = servletFileUpload.parseRequest(req);
                } catch (FileUploadException e) {
                    throw new RuntimeException(e);
                }
                Book book = new Book();
                for(FileItem fileItem:fileItems){
                    if(fileItem.isFormField()){

                        String name = fileItem.getFieldName();
                        String value = fileItem.getString("utf-8");
                        switch (name){
                            case "typeId":
                                book.setTypeId(Long.parseLong(value));
                                break;
                            case "name":
                                book.setName(value);
                                break;
                            case "price":
                                book.setPrice(Double.parseDouble(value));
                                break;
                            case "desc":
                                book.setDesc(value);
                                break;
                            case "publish":
                                book.setPublish(value);
                                break;
                            case "author":
                                book.setAuthor(value);
                                break;
                            case "stock":
                                book.setStock(Long.parseLong(value));
                                break;
                            case "address":
                                book.setAddress(value);
                                break;
                        }
                    }else{
                        String fileName = fileItem.getName();
                        String filterName = fileName.substring(fileName.lastIndexOf("."));
                        String prefix = DateHelper.getImageName();
                        //将图片名改成系统时间.png 防止重复名字的图片被替换
                        fileName = prefix + filterName;
                        String path = req.getServletContext().getRealPath("/Images/cover");
                        String filePath = path + '/'+fileName;
                        String dbPath = "Images/cover/" + fileName;
                        book.setPic(dbPath);
                        try {
                            fileItem.write(new File(filePath));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                int count = bookBiz.add(book.getTypeId(),book.getName(),(float) book.getPrice(),book.getDesc(),book.getPic(),book.getPublish(),book.getAuthor(),book.getStock(),book.getAddress());
                if(count>0){
                    out.println("<script>alert('书籍添加成功');location.href='book.let?type=query&pageIndex=1';</script>");
                }else{
                    out.println("<script>alert('书籍添加失败');location.href='book_add.jsp';</script>");
                }
                break;
            case "query":
                try {
                    query(req,resp,out);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "details":
                try {
                    details(req,resp,out);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "remove":
                try {
                    remove(req,resp,out);
                }catch (Exception e) {
                    out.println("<script>alert('该书籍被借走，无法删除');location.href='book.let?type=query&pageIndex=1';</script>");
                }
                break;
            case "modifypre":
                try {
                    modifypre(req,resp,out);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "modify":
                try {
                    modify(req,resp,out);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    private void modify(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws UnsupportedEncodingException, SQLException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024*9);
        File file = new File("c:\\temp");
        if(!file.exists()){
            file.mkdir();
        }
        factory.setRepository(file);
        //文件上传+表单数据
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        List<FileItem> fileItems = null;
        try {
            fileItems = servletFileUpload.parseRequest(req);
        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        }
        Book book = new Book();
        for(FileItem fileItem:fileItems){
            if(fileItem.isFormField()){

                String name = fileItem.getFieldName();
                String value = fileItem.getString("utf-8");
                switch (name){
                    case "id":
                        book.setId(Long.parseLong(value));
                        break;
                    case "pic":
                        book.setPic(value);
                        break;
                    case "typeId":
                        book.setTypeId(Long.parseLong(value));
                        break;
                    case "name":
                        book.setName(value);
                        break;
                    case "price":
                        book.setPrice(Double.parseDouble(value));
                        break;
                    case "desc":
                        book.setDesc(value);
                        break;
                    case "publish":
                        book.setPublish(value);
                        break;
                    case "author":
                        book.setAuthor(value);
                        break;
                    case "stock":
                        book.setStock(Long.parseLong(value));
                        break;
                    case "address":
                        book.setAddress(value);
                        break;
                }
            }else{
                String fileName = fileItem.getName();
                if(fileName.trim().length()>0) {
                    String filterName = fileName.substring(fileName.lastIndexOf("."));
                    String prefix = DateHelper.getImageName();
                    //将图片名改成系统时间.png 防止重复名字的图片被替换
                    fileName = prefix + filterName;
                    String path = req.getServletContext().getRealPath("/Images/cover");
                    String filePath = path + '/' + fileName;
                    String dbPath = "Images/cover/" + fileName;
                    book.setPic(dbPath);
                    try {
                        fileItem.write(new File(filePath));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        int count = bookBiz.modify(book.getId(),book.getTypeId(),book.getName(),(float) book.getPrice(),book.getDesc(),book.getPic(),book.getPublish(),book.getAuthor(),book.getStock(),book.getAddress());
        if(count>0){
            out.println("<script>alert('书籍修改成功');location.href='book.let?type=query&pageIndex=1';</script>");
        }else{
            out.println("<script>alert('书籍修改失败');location.href='book.let?type=query&pageIndex=1';</script>");
        }
    }

    private void modifypre(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws SQLException, ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Book book = bookBiz.getById(id);
        req.setAttribute("book",book);
        req.getRequestDispatcher("book_modify.jsp").forward(req,resp);
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws Exception {
        long id = Long.parseLong(req.getParameter("id"));
        int count = bookBiz.remove(id);
        if(count>0){
            out.println("<script>alert('书籍删除成功');location.href='book.let?type=query&pageIndex=1';</script>");
        }else{
            out.println("<script>alert('书籍删除失败');location.href='book.let?type=query&pageIndex=1';</script>");
        }
    }

    private void details(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws SQLException, ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Book book = bookBiz.getById(id);
        req.setAttribute("book", book);
        req.getRequestDispatcher("book_details.jsp").forward(req,resp);
    }

    /**
     * book.let?type=query&pageIndex=1
     * @param req
     * @param resp
     * @param out
     */
    private void query(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws SQLException, ServletException, IOException {
        int pageIndex = Integer.parseInt(req.getParameter("pageIndex"));
        int pageNum = bookBiz.getPageCount(3);
        List<Book> bookList = bookBiz.getByPage(pageIndex,3);
        if(pageIndex<1){
            pageIndex=1;
        }
        if(pageIndex>pageNum){
            pageIndex = pageNum;
        }
        //存储信息
        req.setAttribute("pageCount",pageNum);
        req.setAttribute("bookList",bookList);

        req.getRequestDispatcher("book_list.jsp?pageIndex="+pageIndex).forward(req,resp);

    }
}
