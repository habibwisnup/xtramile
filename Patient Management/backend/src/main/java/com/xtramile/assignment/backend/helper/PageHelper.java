package com.xtramile.assignment.backend.helper;

import com.xtramile.assignment.backend.enums.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageHelper {
    private PageHelper(){}

    public static Pageable setPageable(Integer page, Integer size, SortDirection direction,
                                       String sort) {
        Sort.Direction setDirection = setDirection(direction);

        String setSort = setSortParam(sort);

        Integer setSize = maxSizePaginated(size);

        return PageRequest.of(page, setSize, Sort.by(new Sort.Order(setDirection, setSort)));
    }

    public static Integer maxSizePaginated(Integer size) {
        Integer newSize = size;
        if (size > 500) {
            newSize = 500;
        }
        return newSize;
    }

    public static Sort.Direction setDirection(SortDirection sort) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sort != null && sort.getValue().equals(SortDirection.DESC.getValue())) {
            direction = Sort.Direction.DESC;
        }

        return direction;
    }

    public static String setSortParam(String sortParam) {
        String sort = "pid";
        if (sortParam != null) {
            sort = sortParam;
        }

        return sort;
    }
}
