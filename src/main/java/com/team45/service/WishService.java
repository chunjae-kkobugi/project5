package com.team45.service;

import com.team45.entity.Wish;
import com.team45.entity.WishProduct;
import com.team45.util.Page;

import java.util.List;

public interface WishService {
    List<Wish> wishList(String uid);

    Wish wishGet(Long wno);

    List<WishProduct> wishProductList(String uid, Page page);

    int wishProductCount(String uid, Page page);

    int wishFind(Long pno, String uid);

    int wishCount(Long pno);

    int wishInsert(Wish wish);

    int wishDelete(Wish wish);

    void increaseWish(Long pno);

    void decreaseWish(Long pno);

    void wishRemove(Long wno);

    int checkWish(Wish wish);
}
