package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/test-context.xml")
public class HttpSessionCartServiceTest {

    @InjectMocks
    @Resource
    private CartService cartService;

    @Mock
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddPhoneWithInvalidId() {
        cartService.addPhone(-1L, 10L);
        verify(cart, times(0)).addCartItem(any(CartItem.class));
        assertEquals(cart.getProductQuantity(), Integer.valueOf(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddPhoneWithNullArgument() {
        cartService.addPhone(null, 1L);
        verify(cart, times(0)).addCartItem(any(CartItem.class));
    }

    @Test
    public void testAddPhone() {
        cartService.addPhone(1004L, 1L);
        verify(cart, times(1)).addCartItem(any(CartItem.class));
    }
}
