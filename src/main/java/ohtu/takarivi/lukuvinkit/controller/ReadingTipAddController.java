package ohtu.takarivi.lukuvinkit.controller;

import ohtu.takarivi.lukuvinkit.domain.CustomUser;
import ohtu.takarivi.lukuvinkit.domain.ReadingTip;
import ohtu.takarivi.lukuvinkit.domain.ReadingTipCategory;
import ohtu.takarivi.lukuvinkit.forms.*;
import ohtu.takarivi.lukuvinkit.repository.CustomUserRepository;
import ohtu.takarivi.lukuvinkit.repository.ReadingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * The Spring controller for activity relating to adding reading tips.
 */
@Controller
public class ReadingTipAddController {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private ReadingTipRepository readingTipRepository;

    /**
     * The endpoint for adding a reading tip.
     *
     * @param auth              An Authentication object representing the currently authenticated user.
     * @param readingTipAddForm The form used to add the reading tip.
     * @param result            The BindingResult of the reading tip form.
     * @param attributes        The attributes for the redirect.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/readingTips/add")
    public String addReadingTip(Authentication auth, HttpServletRequest request,
                                @Valid @ModelAttribute ReadingTipAddForm readingTipAddForm, BindingResult result,
                                RedirectAttributes attributes) {
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        String referer = request.getHeader("Referer");
        readingTipAddForm.validateRest(result);
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.readingTipAddForm", result);
            attributes.addFlashAttribute("readingTipAddForm", readingTipAddForm);
            return "redirect:" + (referer == null ? "/" : referer);
        }
        readingTipRepository.save(readingTipAddForm.createReadingTip(customUser));
        return "redirect:" + (referer == null ? "/" : referer);
    }

    /**
     * The page containing the form that allows the user to create a new article reading tip.
     *
     * @param model          The model to feed the information into.
     * @param articleAddForm The instance of the form.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/readingTips/articles/add")
    public String viewAddArticle(Model model, @ModelAttribute ArticleAddForm articleAddForm) {
        model.addAttribute("title", "Lisää artikkeli");
        model.addAttribute("view", "addarticle");
        return "layout";
    }

    /**
     * The page that interprets and handles the form used to create a new article reading tip.
     *
     * @param auth           An Authentication object representing the currently authenticated user.
     * @param articleAddForm The instance of the form.
     * @param result         The binding result of the form.
     * @param model          The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/readingTips/articles/add")
    public String addArticle(Authentication auth, @Valid @ModelAttribute ArticleAddForm articleAddForm,
                             BindingResult result, Model model) {
        articleAddForm.validateRest(result);
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        if (result.hasErrors()) {
            model.addAttribute("title", "Lisää artikkeli");
            model.addAttribute("view", "addarticle");
            return "layout";
        }
        readingTipRepository.save(new ReadingTip(articleAddForm.getTitle(),
                ReadingTipCategory.ARTICLE,
                articleAddForm.getDescription(),
                "",
                articleAddForm.getAuthor(),
                "",
                customUser));
        return "redirect:/";
    }

    /**
     * The page containing the form that allows the user to create a new book reading tip.
     *
     * @param model       The model to feed the information into.
     * @param bookAddForm The instance of the form.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/readingTips/books/add")
    public String viewAddBook(Model model, @ModelAttribute BookAddForm bookAddForm) {
        model.addAttribute("title", "Lisää kirja");
        model.addAttribute("view", "addbook");
        return "layout";
    }

    /**
     * The page that interprets and handles the form used to create a new book reading tip.
     *
     * @param auth        An Authentication object representing the currently authenticated user.
     * @param bookAddForm The instance of the form.
     * @param result      The binding result of the form.
     * @param model       The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/readingTips/books/add")
    public String addBook(Authentication auth, @Valid @ModelAttribute BookAddForm bookAddForm,
                          BindingResult result, Model model) {
        bookAddForm.validateRest(result);
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        if (result.hasErrors()) {
            model.addAttribute("title", "Lisää kirja");
            model.addAttribute("view", "addbook");
            return "layout";
        }
        readingTipRepository.save(new ReadingTip(bookAddForm.getTitle(),
                ReadingTipCategory.BOOK,
                bookAddForm.getDescription(),
                "",
                bookAddForm.getAuthor(),
                bookAddForm.getIsbn(),
                customUser));
        return "redirect:/";
    }

    /**
     * The page containing the form that allows the user to create a new book reading tip.
     *
     * @param model       The model to feed the information into.
     * @param linkAddForm The instance of the form.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/readingTips/links/add")
    public String viewAddLink(Model model, @ModelAttribute LinkAddForm linkAddForm) {
        model.addAttribute("title", "Lisää linkki");
        model.addAttribute("view", "addlink");
        return "layout";
    }

    /**
     * The page that interprets and handles the form used to create a new link reading tip.
     *
     * @param auth        An Authentication object representing the currently authenticated user.
     * @param linkAddForm The instance of the form.
     * @param result      The binding result of the form.
     * @param model       The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/readingTips/links/add")
    public String addLink(Authentication auth, @Valid @ModelAttribute LinkAddForm linkAddForm,
                          BindingResult result, Model model) {
        linkAddForm.validateRest(result);
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        if (result.hasErrors()) {
            model.addAttribute("title", "Lisää linkki");
            model.addAttribute("view", "addlink");
            return "layout";
        }
        readingTipRepository.save(new ReadingTip(linkAddForm.getTitle(),
                ReadingTipCategory.LINK,
                linkAddForm.getDescription(),
                linkAddForm.getUrl(),
                linkAddForm.getAuthor(),
                "",
                customUser));
        return "redirect:/";
    }

    /**
     * The page containing the form that allows the user to create a new video reading tip.
     *
     * @param model        The model to feed the information into.
     * @param videoAddForm The instance of the form.
     * @return The action to be taken by this controller.
     */
    @GetMapping("/readingTips/videos/add")
    public String viewAddVideo(Model model, @ModelAttribute VideoAddForm videoAddForm) {
        model.addAttribute("title", "Lisää video");
        model.addAttribute("view", "addvideo");
        return "layout";
    }

    /**
     * The page that interprets and handles the form used to create a new video reading tip.
     *
     * @param auth         An Authentication object representing the currently authenticated user.
     * @param videoAddForm The instance of the form.
     * @param result       The binding result of the form.
     * @param model        The model to feed the information into.
     * @return The action to be taken by this controller.
     */
    @PostMapping("/readingTips/videos/add")
    public String addVideo(Authentication auth, @Valid @ModelAttribute VideoAddForm videoAddForm,
                           BindingResult result, Model model) {
        videoAddForm.validateRest(result);
        CustomUser customUser = customUserRepository.findByUsername(auth.getName());
        if (result.hasErrors()) {
            model.addAttribute("title", "Lisää video");
            model.addAttribute("view", "addvideo");
            return "layout";
        }
        readingTipRepository.save(new ReadingTip(videoAddForm.getTitle(),
                ReadingTipCategory.VIDEO,
                videoAddForm.getDescription(),
                videoAddForm.getUrl(),
                videoAddForm.getAuthor(),
                "",
                customUser));
        return "redirect:/";
    }

}
