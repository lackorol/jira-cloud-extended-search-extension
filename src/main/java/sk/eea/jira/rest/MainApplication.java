package sk.eea.jira.rest;

import com.atlassian.connect.spring.AtlassianHostUser;
import com.atlassian.connect.spring.IgnoreJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

@Controller
public class MainApplication {

    @Autowired
    private RunAfterStartup runAfterStartup;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private IssueService issueService;

    private final int PAGE_SIZE = 10;

    @IgnoreJwt
    @RequestMapping(value = "/extensionSearch", method = RequestMethod.GET)
    public String extensionSearch(@RequestParam(name="page", defaultValue="1") Optional<Integer> page,
                                  Model model) {

        //model.addAttribute("customer", new Customer());
        int currentPage = page.orElse(1);
        int pageSize = 10;

        Page<Issue> issuePage = issueService.findPaginated(PageRequest.of(currentPage, pageSize));
        if (nonNull(issuePage)) {
            model.addAttribute("tenIssues", issuePage);

            int totalPages = issuePage.getTotalPages();
            issueService.addToModelTotalPages(model, totalPages);
        }

        return "extensionSearch";
    }

    /**
     * REST Endpoints for debugging purposes.
     * These are experimental features.
     * Some are feasible only short term like
     * testJPA adding entities to adjacent db.
     */
    @IgnoreJwt
    @RequestMapping(value = "/internal/deployApplication", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void deployApplication() {
        runAfterStartup.runAfterStartup();
    }

    @IgnoreJwt
    @RequestMapping(value = "/internal/testJPA", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void testJPA() {
        customerRepository.save(new Customer("Jack", "Bauer"));
        customerRepository.save(new Customer("Chloe", "O'Brian"));
        customerRepository.save(new Customer("Kim", "Bauer"));
        customerRepository.save(new Customer("David", "Palmer"));
        customerRepository.save(new Customer("Michelle", "Dessler"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println(customer.toString());
        }
        System.out.println("");

        // fetch an individual customer by ID
        Customer customer = customerRepository.findById(1L);
        System.out.println("Customer found with findById(1L):");
        System.out.println("--------------------------------");
        System.out.println(customer.toString());
        System.out.println("");

        // fetch customers by last name
        System.out.println("Customer found with findByLastName('Bauer'):");
        System.out.println("--------------------------------------------");
        customerRepository.findByLastName("Bauer").forEach(bauer -> {
            System.out.println(bauer.toString());
        });
        System.out.println("");
    }

    /**
     * Boilerplate rest APIS
     */
    @IgnoreJwt
    @RequestMapping(value = "/connect-edit", method = RequestMethod.GET)
    public String getEditPage(@AuthenticationPrincipal AtlassianHostUser hostUser, Model model) {
        //addValueAttributeToModel(hostUser, model);
        return "extensionSearch";

    }

    @IgnoreJwt
    @RequestMapping(value = "/main-layout", method = RequestMethod.GET)
    public String extensionSearch() {
        return "main";
    }
}


