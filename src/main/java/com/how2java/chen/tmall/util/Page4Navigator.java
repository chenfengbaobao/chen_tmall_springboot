package com.how2java.chen.tmall.util;

import com.github.pagehelper.PageInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Page4Navigator<T> {


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    PageInfo<T> pageFromJPA;


    int navigatePages;


    int totalPages;


    int number;


    long totalElements;


    int size;


    int numberOfElements;


    List<T> content;


    boolean isHasContent;


    boolean first;


    boolean last;


    boolean isHasNext;


    boolean isHasPrevious;


    int[] navigatepageNums;


    /**
     * 空的分页是为了Redis 从 json 格式转换
     * 为Page4Navigator 对象而专门提供的
     */
    public Page4Navigator() {

    }

    public Page4Navigator(PageInfo<T> pageFromJPA, int navigatePages) {

        this.pageFromJPA = pageFromJPA;
        this.navigatePages = navigatePages;

        totalPages = pageFromJPA.getPages();


        number = pageFromJPA.getPageNum() - 1;

        totalElements = pageFromJPA.getTotal();


        size = pageFromJPA.getSize();

        numberOfElements = pageFromJPA.getSize();


        content = pageFromJPA.getList();

        isHasContent = pageFromJPA.getList() == null ? false : true;

        first = pageFromJPA.isIsFirstPage();

        last = pageFromJPA.isIsLastPage();

        isHasNext = pageFromJPA.isHasNextPage();


        isHasPrevious = pageFromJPA.isHasPreviousPage();

        calcNavigatepageNums();


    }

    private void calcNavigatepageNums() {

        int[] navigatepageNums;

        int totalPages = getTotalPages();

        int number = getNumber();


        // 当总页数小于或等于导航页码时

        if (totalPages <= navigatePages) {
            navigatepageNums = new int[totalPages];

            for (int i = 0; i < totalPages; i++) {
                navigatepageNums[i] = i + 1;
            }


        } else {


            // 总页数大于导航页码数时

            navigatepageNums = new int[navigatePages];
            int startNum = number - navigatePages / 2;

            int endNum = number + navigatePages / 2;


            if (startNum < 1) {
                startNum = 1;

                // 最前navigatePage 页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > totalPages) {

                endNum = totalPages;
                // 最后 navigatePage 页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;

                }

            } else {


                // 所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }

            }

        }


        this.navigatepageNums = navigatepageNums;
    }
}
