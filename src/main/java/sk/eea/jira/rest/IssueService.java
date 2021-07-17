package sk.eea.jira.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Service
public class IssueService {
    private List<Issue> issues;
    private Integer totals;

    public Integer getTotals() {
        return totals;
    }

    public void receiveIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public void receiveTotals(Integer totals) {
        this.totals = totals;
    }

    public Page<Issue> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Issue> list;

        if (isNull(issues)) {
            return null;
        }

        if (issues.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, issues.size());
            list = issues.subList(startItem, toIndex);
        }


        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), issues.size());
    }

    public void addToModelTotalPages(Model model, int totalPages) {
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("totalPages", totalPages);
        }
    }
}

