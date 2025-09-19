//package com.svm.services;
//
//import com.svm.journalapp.repository.UserRepository;
//import com.svm.journalapp.services.UserDetailsServiceImplementation;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import com.svm.journalapp.entities.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.when;
//
//public class UserDetailsServiceImplementationTest {
//
//    @InjectMocks
//    private UserDetailsServiceImplementation userDetailsServiceImplementation;
//    @Mock
//    private User user;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void testLoadUserByUsername(){
////        when(userRepository.findUserByUsername(ArgumentMatchers.anyString()))
////                .thenReturn(user.username("ram").password("uhgigjh").roles(String.valueOf(new ArrayList<>())).build());
////        UserDetails user = userDetailsServiceImplementation.loadUserByUsername("shubham");
////        Assertions.assertNotNull(user);
//    }
//}
