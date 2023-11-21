package com.team45.service;

import com.team45.entity.ProductVO;
import com.team45.entity.Wish;
import com.team45.mapper.WishMapper;
import com.team45.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishServiceImpl implements WishService {
    @Autowired
    private WishMapper wishMapper;

    @Override
    public List<Wish> wishList(String uid) {
        return wishMapper.wishList(uid);
    }

    @Override
    public List<ProductVO> wishProductList(String uid, Page page) {
        return wishMapper.wishProductList(uid, page);
    }

    @Override
    public int wishProductCount(String uid, Page page) {
        return wishMapper.wishProductCount(uid, page);
    }

    @Override
    public int wishFind(Long pno, String uid) {
        return wishMapper.wishFind(pno, uid);
    }

    @Override
    public int wishCount(Long pno) {
        return wishMapper.wishCount(pno);
    }

    @Override
    public int wishInsert(Wish wish) {
        return wishMapper.wishInsert(wish);
    }

    @Override
    public int wishDelete(Wish wish) {
        return wishMapper.wishDelete(wish);
    }

    @Override
    public void increaseWish(Long pno) {
        wishMapper.increaseWish(pno);
    }

    @Override
    public void decreaseWish(Long pno) {
        wishMapper.decreaseWish(pno);
    }

    @Override
    public int checkWish(Wish wish) {
        //System.out.println("wish : " + wish);
        Long pno = wish.getPno();
        String uid = wish.getUid();
        int found = wishFind(pno, uid);
        //System.out.println("found : " + found);
        int result = 0;
        if (found == 0) {
            result = wishInsert(wish);
            increaseWish(pno);
        } else {
            result = wishDelete(wish) * -1;
            decreaseWish(pno);
        }
        return result;
    }
}
