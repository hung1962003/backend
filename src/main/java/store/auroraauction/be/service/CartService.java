package store.auroraauction.be.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import store.auroraauction.be.Models.CartResponse;
import store.auroraauction.be.entity.Cart;
import store.auroraauction.be.entity.Category;
import store.auroraauction.be.entity.Jewelry;
import store.auroraauction.be.repository.CartRepository;
import store.auroraauction.be.repository.JewelryRepository;
import store.auroraauction.be.utils.AccountUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private JewelryRepository jewelryRepository;

    public String deleteCart(Long id) {
        cartRepository.deleteById(id) ;
        return "cart's jewelry del      eted";
    }
    public String deleteAllCart() {
        cartRepository.deleteAll(); ;
        return "cart deleted";
    }
    public List<Cart> getAllCart(){
        List<Cart> cart = cartRepository.findAll();
        return cart;
    }
    public Cart getCart( Long id) {
        Cart cart = cartRepository.findById(id).get();
        return cart;
    }

    public Cart addJewelryIntoCart(Long jewelry_id) {

        Cart cart = cartRepository.findCartByAccountId(accountUtils.getCurrentAccount().getId());
        Jewelry jewelry = jewelryRepository.findById(jewelry_id).get();
        if (jewelry!=null) {
            Set<Jewelry> listJewelryID = cart.getJewelries();// lấy danh sách các món trang sức (Jewelry) từ giỏ hàng (Cart).

            if (listJewelryID == null) {
                listJewelryID = new HashSet<>();
            }
            jewelry.getCarts().add(cart);
            listJewelryID.add(jewelry);
            cart.setJewelries(listJewelryID);
            cartRepository.save(cart);
        } else {
            // Handle the case where the jewelry does not exist
        }
        return cart;
    }
    public CartResponse getCart2() {
        Cart cart = cartRepository.findCartByAccountId(accountUtils.getCurrentAccount().getId());
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCart_id(cart.getId());
        cartResponse.setJewelry_id(cart.getJewelries().stream().map(item -> item.getId()).collect(Collectors.toSet()));
        cartResponse.setUser_id(accountUtils.getCurrentAccount().getId());
        return cartResponse; // Don't forget to return your list



    }

}
