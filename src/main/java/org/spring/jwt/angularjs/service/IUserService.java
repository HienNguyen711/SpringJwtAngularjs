package org.spring.jwt.angularjs.service;

import javax.servlet.http.HttpServletRequest;

import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.User;
import org.spring.jwt.angularjs.request.SignupRequest;
import org.spring.jwt.angularjs.request.UpdateUserRequest;
import org.spring.jwt.angularjs.request.UserRequest;
import org.springframework.mobile.device.Device;

public interface IUserService extends IGenericService<User, Long>  {
    User findByUsername(String username) throws ServiceException;
    User findUserByEmail(String email) throws ServiceException;
    void createVerificationToken(User user, String token) throws ServiceException;
    void createPasswordResetToken(User user, String token) throws ServiceException;
    void verificationToken(String token) throws ServiceException;
    User createUser(UserRequest userRequest) throws ServiceException;
    void updateUser(UpdateUserRequest userRequest) throws ServiceException;
    void deleteUser(Long id) throws ServiceException;
    void resetPassword(String email, HttpServletRequest httpRequest, Device device) throws ServiceException;
    void resetNewPassword(String token, String newPassword) throws ServiceException;
    void signUp(SignupRequest signUpRequest, HttpServletRequest httpRequest, Device device) throws ServiceException;
}
