package com.belch.Books_spring.controller;

import com.belch.Books_spring.model.Book;
import com.belch.Books_spring.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import  org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class BooksController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping({"/","bookList"})
    public ModelAndView bookList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bookList",bookRepository.findAll());
        modelAndView.setViewName("bookList");
        return modelAndView;
    }


    @GetMapping("book/new")
    public ModelAndView addNewBook() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("book", new Book());
        modelAndView.setViewName("bookEdit");
        return modelAndView;
    }


    @GetMapping("/bookEdit/{id}")
    public ModelAndView bookEdit(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("book", bookRepository.getOne(id));
        modelAndView.setViewName("bookEdit");
        return modelAndView;
    }

    @PostMapping("/bookSave")
    public String saveBook( Book book, @RequestParam Integer id,
                           @RequestParam String title, @RequestParam String author) {

        if(id != null)
        {
            Book editBook = bookRepository.getOne(id);
            editBook.setAuthor(author);
            editBook.setTitle(title);
            bookRepository.save(editBook);
        }
        else
        {
            bookRepository.save(book);
        }

        return "redirect:/bookList";
    }

    @GetMapping("/bookDelete/{id}")
    public String bookDelete(Model model, @PathVariable Integer id) {
        bookRepository.deleteById(id);
        model.addAttribute("bookList", bookRepository.findAll());
        return "bookList";
    }
}
