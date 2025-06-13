package com.jsonplaceholder.service;

import com.jsonplaceholder.dto.AddressDto;
import com.jsonplaceholder.dto.CompanyDto;
import com.jsonplaceholder.dto.GeoDto;
import com.jsonplaceholder.dto.UserDto;
import com.jsonplaceholder.model.Address;
import com.jsonplaceholder.model.Company;
import com.jsonplaceholder.model.Geo;
import com.jsonplaceholder.model.User;
import com.jsonplaceholder.repository.UserRepository;
import com.jsonplaceholder.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        // Create test user
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");

        // Set up address
        Address address = new Address();
        address.setStreet("Test Street");
        address.setSuite("Test Suite");
        address.setCity("Test City");
        address.setZipcode("12345");

        // Set up geo
        Geo geo = new Geo();
        geo.setLat("12.34");
        geo.setLng("56.78");
        address.setGeo(geo);
        testUser.setAddress(address);

        // Set up company
        Company company = new Company();
        company.setName("Test Company");
        company.setCatchPhrase("Test Catch Phrase");
        company.setBs("Test BS");
        testUser.setCompany(company);

        // Create DTO
        testUserDto = new UserDto();
        testUserDto.setId(1L);
        testUserDto.setName("Test User");
        testUserDto.setUsername("testuser");
        testUserDto.setEmail("test@example.com");

        AddressDto addressDto = new AddressDto();
        addressDto.setStreet("Test Street");
        addressDto.setSuite("Test Suite");
        addressDto.setCity("Test City");
        addressDto.setZipcode("12345");

        GeoDto geoDto = new GeoDto();
        geoDto.setLat("12.34");
        geoDto.setLng("56.78");
        addressDto.setGeo(geoDto);
        testUserDto.setAddress(addressDto);

        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("Test Company");
        companyDto.setCatchPhrase("Test Catch Phrase");
        companyDto.setBs("Test BS");
        testUserDto.setCompany(companyDto);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

        List<UserDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(testUser.getName(), users.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        UserDto found = userService.getUserById(1L);

        assertNotNull(found);
        assertEquals(testUser.getName(), found.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(999L));
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void createUser_ShouldReturnSavedUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDto saved = userService.createUser(testUserDto);

        assertNotNull(saved);
        assertEquals(testUser.getName(), saved.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_WhenUserExists_ShouldReturnUpdatedUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDto updated = userService.updateUser(1L, testUserDto);

        assertNotNull(updated);
        assertEquals(testUser.getName(), updated.getName());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(999L, testUserDto));
        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_WhenUserExists_ShouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.existsById(999L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(999L));
        verify(userRepository, times(1)).existsById(999L);
        verify(userRepository, never()).deleteById(anyLong());
    }
} 