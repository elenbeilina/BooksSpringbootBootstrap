package com.belch.Books_spring.controller;

import com.belch.Books_spring.model.Book;
import com.belch.Books_spring.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import  org.springframework.ui.Model;


@Controller
public class BooksController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value="/")
    public String bookList(Model model) {
        model.addAttribute("bookList", bookRepository.findAll());
        return "bookList";
    }

    @GetMapping({"/bookEdit","/bookEdit/{id}"})
    public String bookEdit(Model model, @PathVariable(required = false, name = "id") Integer id) {

        model.addAttribute("book", bookRepository.findById(id));

        return "bookEdit";
    }

    @PostMapping("/bookEdit")
    public String saveBook(Model model, Book book) {
        bookRepository.save(book);
        model.addAttribute("bookList", bookRepository.findAll());
        return "bookList";
    }

    @GetMapping("/bookDelete/{id}")
    public String bookDelete(Model model, @PathVariable(required = true, name = "id") Integer id) {
        bookRepository.deleteById(id);
        model.addAttribute("bookList", bookRepository.findAll());
        return "bookList";
    }
}
