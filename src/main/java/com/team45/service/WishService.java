package com.team45.service;

import com.team45.entity.Wish;

import java.util.List;

public interface WishService {
    List<Wish> wishList(String uid);

    int wishFind(Long pno, String uid);

    int wishCount(Long pno);

    int wishInsert(Wish wish);

    int wishDelete(Wish wish);

    void increaseWish(Long pno);

    void decreaseWish(Long pno);

    int checkWish(Wish wish);
}
