package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exceptions.BookShelfLoginException;
import org.example.app.exceptions.FileIsNullException;
import org.example.app.exceptions.WrongRegexException;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;

@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookById(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByRegex")
    public String removeBookByRegex(@RequestParam(value = "queryRegex") String queryRegex) throws SQLException, WrongRegexException {
        if (bookService.removeBookByRegex(queryRegex)) {
            return "redirect:/books/shelf";
        } else {
            throw new WrongRegexException("Invalid regex, or it is null!");
        }
    }


    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") @Valid MultipartFile file) throws Exception {

        try {
            String name = file.getOriginalFilename();
            byte[] bytes = file.getBytes();


            //create Dir
            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "external_uploads");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            //create File
            File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

            logger.info("new file saved at:" + serverFile.getAbsolutePath());

            return "redirect:/books/shelf";
        }
        catch (Exception e){
            e.printStackTrace();
            throw new FileIsNullException("You need to pick a file!");
        }
    }

    @ExceptionHandler(FileIsNullException.class)
    public String handleError(Model model, FileIsNullException exception){
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/500";
    }

    @ExceptionHandler(WrongRegexException.class)
    public String handleError(Model model, WrongRegexException exception){
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/wre";
    }

}




//
//    @PostMapping("/removeByAuthor")
//    public String removeBookByAuthor(@RequestParam(value = "author") String author) {
//        if (bookService.removeBookByAuthor(author)) {
//            return "redirect:/books/shelf";
//        } else {
//            return "redirect:/books/shelf";
//        }
//    }
//
//    @PostMapping("/removeByTitle")
//    public String removeBookByTitle(@RequestParam(value = "title") String title) {
//        if (bookService.removeBookByTitle(title)) {
//            return "redirect:/books/shelf";
//        } else {
//            return "redirect:/books/shelf";
//        }
//    }
//
//    @PostMapping("/removeBySize")
//    public String removeBookBySize(@RequestParam(value = "size") int size) {
//        if (bookService.removeBookBySize(size)) {
//            return "redirect:/books/shelf";
//        } else {
//            return "redirect:/books/shelf";
//        }
//    }
