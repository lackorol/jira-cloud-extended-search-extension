package sk.eea.jira.rest;

import com.atlassian.connect.spring.IgnoreJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class Receiver {
    @Autowired
    private IssueService issueService;

    private final int PAGE_SIZE = 10;


    @IgnoreJwt
    @RequestMapping(value = "/receiveFirstTenIssues", method = RequestMethod.POST)
    public String receiveFirstTenIssues(@RequestBody final List<Issue> firstTen,
                                        @RequestParam(name = "totals") final Integer totals,
                                                      Model model) {
        model.addAttribute("tenIssues", firstTen);

        issueService.receiveTotals(totals);
        int totalPages = totals / PAGE_SIZE;
        issueService.addToModelTotalPages(model, totalPages);
        return "extensionSearch";
    }

    @IgnoreJwt
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/receiveNextIssues", method = RequestMethod.POST)
    public void receiveNextIssues(@RequestBody List<Issue> nextIssues) {
        issueService.receiveIssues(nextIssues);
    }
}
