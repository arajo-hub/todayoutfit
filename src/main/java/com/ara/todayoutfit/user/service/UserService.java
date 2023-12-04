package com.ara.todayoutfit.user.service;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ObjectResponse;
import com.ara.todayoutfit.common.ResultCode;
import com.ara.todayoutfit.config.exception.NotFoundException;
import com.ara.todayoutfit.user.domain.User;
import com.ara.todayoutfit.user.repository.UserRepository;
import com.ara.todayoutfit.user.request.UserCreateRequest;
import com.ara.todayoutfit.user.response.UserShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ObjectResponse<UserShow> getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        return new ObjectResponse<>(user.toUserShow());
    }

    public User save(User user) {
        return userRepository.saveUser(user);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public BaseResult saveUser(UserCreateRequest request) {
        User user = request.toUser();
        User save = userRepository.saveUser(user);
        return user.equals(save) ? new BaseResult(ResultCode.SUCCESS) : new BaseResult(ResultCode.INTERNAL_SERVER_ERROR);
    }

    public BaseResult updateUser(String id, UserCreateRequest request) {
        userRepository.updateUser(id, request);
        return new BaseResult(ResultCode.SUCCESS);
    }
}
